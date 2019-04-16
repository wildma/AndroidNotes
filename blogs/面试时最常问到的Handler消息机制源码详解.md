# 面试时最常问到的Handler消息机制源码详解

## 前言
辞职后（非当前时间），最近又开始加入找工作的大队伍中。不得不说今年找工作确实比以前难了。从几个朋友说他们公司快倒闭的情况也验证了这一点。最近面了2家，竟然都问到了Handler消息机制，虽然以前看过源码，但是很久没看，也忘得差不多了，总体的虽然都讲的出，但是却没有彻底征服面试官，所以自己干脆再总结一遍写篇博客记录下来好了。

## 正确阅读源码的姿势
有些人阅读源码是力求每行代码都要读懂，我个人感觉这个方法是错误的。正确的方法是应该按平时你使用某个框架或者某个系统源码的执行流程，抓重点去看。例如看Handler源码，应该按照创建Handler-发送消息-处理消息的执行流程去看。并且要看最新版本的源码。例如Android2.3与Android7.0的Handler源码相差还是很大的，Android2.3中Handler的构造方法是没有进行封装的，Android7.0则进行了封装。Android2.3中Handler回收消息的时候消息池大小判断不严谨，但是高版本的就改过来了。

## Handler的用法
Handler最常用的用法，即子线程完成耗时任务然后通知主线程更新UI，步骤为：创建Handler-发送消息-处理消息。如下：
```
        //2，发送消息（子线程）
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                message.arg1 = 666;
                mHandler.sendMessage(message);
            }
        }).start();

        private static final int TAG = 1;
        //1，创建Handler（主线程）
        final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
            //3,处理消息
                if(msg.what == TAG){
                    tvText.setText(msg.arg1);
                }
            }
        };

```

## 开始阅读源码
现在我们就根据上面Handler使用的执行流程去解析源码。

## 一、创建Handler----Handler handler = new Handler();
有人问：为什么要在主线程中创建Handler，而不在子线程中创建呢？
因为如果你在子线程创建Handler（如下），程序则会崩溃，并且会报错误：Can't create handler inside thread that has not called Looper.prepare() ，翻译为：不能在没有调用Looper.prepare() 的线程中创建Handler。

```
        new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
            }
        }).start();
```

那如果在子线程中先调用一下Looper.prepare()呢，如下：

```
        new Thread(new Runnable() {
            @Override
            public void run() {
	            Looper.prepare()
                Handler handler = new Handler();
            }
        }).start();

```
果然程序就能正常运行了。玄机就藏在源码当中！

首先我们点击我们创建的Handler进去源码是这样的：  
【Handler.java】
```
    public Handler() {
        this(null, false);
    }
```
然后再跟到这个构造方法里，发现是走了有参构造  
【Handler.java】
```
    public Handler(Callback callback, boolean async) {
        if (FIND_POTENTIAL_LEAKS) {
            final Class<? extends Handler> klass = getClass();
            if ((klass.isAnonymousClass() || klass.isMemberClass() || klass.isLocalClass()) &&
                    (klass.getModifiers() & Modifier.STATIC) == 0) {
                Log.w(TAG, "The following Handler class should be static or leaks might occur: " +
                    klass.getCanonicalName());
            }
        }
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                "Can't create handler inside thread that has not called Looper.prepare()");
        }
        mQueue = mLooper.mQueue;
        mCallback = callback;
        mAsynchronous = async;
    }
```
可以看到，在第13行抛出的异常错误刚好就是我们刚刚上面报的错误！报错的原因是mLooper对象为空了，而mLooper对象则是在第10行代码中获取的，接下来我们点进去看看myLooper()这个方法，如下：  
【Looper.java】
```
    public static Looper myLooper() {
        return sThreadLocal.get();
    }
```
可以看到，mLooper对象是通过sThreadLocal的get()方法获取的。由此可以联想到应该是有sThreadLocal.set()方法设置了mLooper对象。在当前类中查找，果然找到了。如下：  
【Looper.java】

```
    private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(quitAllowed));
    }
```
果然，在第5行sThreadLocal.set()方法正是在Looper.prepare()方法里面。这段代码比较简单：首先判断sThreadLocal中有没有Looper对象，有就抛出异常，没有则new一个新的Looper进去。这样就得出结论：之前为什么会报如上错误了（不能在没有调用Looper.prepare() 的线程中创建Handler）。

但是但是！为什么在主线程中创建Handler之前就不用调用Looper.prepare() 呢？？
查找资料发现，Android程序的入口中，系统就默认帮我们调用了Looper.prepare()方法。
Android程序的入口在ActivityThread中的main()方法，ActivityThread这个类在Android studio中是看不到的，只能利用工具source insight来看。代码如下：  
【ActivityThread.java】
```
public static void main(String[] args) {
    SamplingProfilerIntegration.start();
    CloseGuard.setEnabled(false);
    Environment.initForCurrentUser();
    EventLogger.setReporter(new EventLoggingReporter());
    Process.setArgV0("<pre-initialized>");
    Looper.prepareMainLooper();
    ActivityThread thread = new ActivityThread();
    thread.attach(false);
    if (sMainThreadHandler == null) {
        sMainThreadHandler = thread.getHandler();
    }
    AsyncTask.init();
    if (false) {
        Looper.myLooper().setMessageLogging(new LogPrinter(Log.DEBUG, "ActivityThread"));
    }
    Looper.loop();
    throw new RuntimeException("Main thread loop unexpectedly exited");
}
```
可以看到，在第7行调用了Looper.prepareMainLooper()方法，跟进去，prepareMainLooper()方法中又调用了prepare()方法
【ActivityThread.java】  
```
public static final void prepareMainLooper() {
    prepare();
    setMainLooper(myLooper());
    if (Process.supportsProcesses()) {
        myLooper().mQueue.mQuitAllowed = false;
    }
}
```
这样就说明了在主线程中创建Handler同样需要调用Looper.prepare()方法，只是这个方法系统已经帮我们调用了。

接下来Handler的有参构造就只剩下面三行了。

```
	mQueue = mLooper.mQueue;
        mCallback = callback;
        mAsynchronous = async;
```
可以看到，一开始我们用的是无参构造，即传入了一个null的Callback，async的值也是false，所以这里就不考虑这2行，mQueue = mLooper.mQueue;则是获取一个消息队列MessageQueue。用于将所有收到的消息以队列的形式进行排列，并提供入队和出队的方法。MessageQueue这个类是在Looper的构造函数中创建的，因此一个Looper也就对应了一个MessageQueue。

### 小总结：主线程中可以直接创建Handler，在子线程中需要先调用Looper.prepare()才能创建Handler。Handler的构造方法中主要是获取轮询器（即Looper对象）和消息队列（即MessageQueue对象）。

## 二、发送消息----mHandler.sendMessage(message);
点击sendMessage()方法进去，如下：  
【Handler.java】
```
    public final boolean sendMessage(Message msg)
    {
        return sendMessageDelayed(msg, 0);
    }
```
可以看到，调用了sendMessageDelayed()方法，点进去，如下：  
【Handler.java】
```
    public final boolean sendMessageDelayed(Message msg, long delayMillis)
    {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
    }
```
可以看到，delayMillis < 0判断是为了防止用户传入的延迟参数为负数。之后又调用了sendMessageAtTime()方法，点进去，如下：  
【Handler.java】
```
    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessageAtTime() called with no mQueue");
            Log.w("Looper", e.getMessage(), e);
            return false;
        }
        return enqueueMessage(queue, msg, uptimeMillis);
    }
```
可以看到，我们代码中调用sendMessage()方法，最终就是走的sendMessageAtTime()方法。
而且其他发送消息的方法除了sendMessageAtFrontOfQueue(),例如sendMessageDelayed()，sendEmptyMessageDelayed()最终都会走sendMessageAtTime()方法。

sendMessageAtTime()方法接收两个参数，其中msg参数就是我们发送的Message对象，而uptimeMillis参数则表示发送消息的时间，它的值等于自系统开机到当前时间的毫秒数再加上延迟时间，如果你调用的不是sendMessageDelayed()方法，延迟时间就为0。第二行中的mQueue则是我们在创建Handler的时候获取的消息队列，然后将这三个参数都传递到enqueueMessage()方法中。  
【Handler.java】
```
    private boolean enqueueMessage(MessageQueue queue, Message msg, long uptimeMillis) {
        msg.target = this;
        if (mAsynchronous) {
            msg.setAsynchronous(true);
        }
        return queue.enqueueMessage(msg, uptimeMillis);
    }
```
可以看到，enqueueMessage()方法中，首先将当前的Handler绑定给msg.target，接着调用MessageQueue的enqueueMessage()方法


MessageQueue的enqueueMessage()方法则是消息入队的方法，点击进去，如下：  
【MessageQueue.java】
```
    boolean enqueueMessage(Message msg, long when) {
        if (msg.target == null) {
            throw new IllegalArgumentException("Message must have a target.");
        }
        if (msg.isInUse()) {
            throw new IllegalStateException(msg + " This message is already in use.");
        }

        synchronized (this) {
            if (mQuitting) {
                IllegalStateException e = new IllegalStateException(
                        msg.target + " sending message to a Handler on a dead thread");
                Log.w(TAG, e.getMessage(), e);
                msg.recycle();
                return false;
            }

            msg.markInUse();
            msg.when = when;
            Message p = mMessages;
            boolean needWake;
            if (p == null || when == 0 || when < p.when) {
                // New head, wake up the event queue if blocked.
                msg.next = p;
                mMessages = msg;
                needWake = mBlocked;
            } else {
                // Inserted within the middle of the queue.  Usually we don't have to wake
                // up the event queue unless there is a barrier at the head of the queue
                // and the message is the earliest asynchronous message in the queue.
                needWake = mBlocked && p.target == null && msg.isAsynchronous();
                Message prev;
                for (;;) {
                    prev = p;
                    p = p.next;
                    if (p == null || when < p.when) {
                        break;
                    }
                    if (needWake && p.isAsynchronous()) {
                        needWake = false;
                    }
                }
                msg.next = p; // invariant: p == prev.next
                prev.next = msg;
            }

            // We can assume mPtr != 0 because mQuitting is false.
            if (needWake) {
                nativeWake(mPtr);
            }
        }
        return true;
    }
```
可以看到，代码的第2行，当msg.target为null时是直接抛异常的。代码的第22-45行，首先判断如果当前的消息队列为空，或者新添加的消息的执行时间when是0，或者新添加的消息的执行时间比消息队列头的消息的执行时间还早，就把消息添加到消息队列头（消息队列按时间排序），否则就要找到合适的位置将当前消息添加到消息队列。

### 小总结：发送消息的方法除了sendMessageAtFrontOfQueue(),例如sendMessage()，sendMessageDelayed()最终都会走sendMessageAtTime()方法。在sendMessageAtTime()方法中又调用MessageQueue的enqueueMessage()方法将所有的消息按时间来进行排序放在消息队列中。

### 三、处理消息----Looper.loop()
消息发送完成并且也已经入队列了，接下来我们就是处理消息队列中的消息了。首先要从队列中取出消息，取消息主要靠轮询器，看Looper.loop()方法  
【Looper.java】
```
    public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        final MessageQueue queue = me.mQueue;

        // Make sure the identity of this thread is that of the local process,
        // and keep track of what that identity token actually is.
        Binder.clearCallingIdentity();
        final long ident = Binder.clearCallingIdentity();

        for (;;) {
            Message msg = queue.next(); // might block
            if (msg == null) {
                // No message indicates that the message queue is quitting.
                return;
            }

            // This must be in a local variable, in case a UI event sets the logger
            final Printer logging = me.mLogging;
            if (logging != null) {
                logging.println(">>>>> Dispatching to " + msg.target + " " +
                        msg.callback + ": " + msg.what);
            }

            final long traceTag = me.mTraceTag;
            if (traceTag != 0 && Trace.isTagEnabled(traceTag)) {
                Trace.traceBegin(traceTag, msg.target.getTraceName(msg));
            }
            try {
                msg.target.dispatchMessage(msg);
            } finally {
                if (traceTag != 0) {
                    Trace.traceEnd(traceTag);
                }
            }

            if (logging != null) {
                logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
            }

            // Make sure that during the course of dispatching the
            // identity of the thread wasn't corrupted.
            final long newIdent = Binder.clearCallingIdentity();
            if (ident != newIdent) {
                Log.wtf(TAG, "Thread identity changed from 0x"
                        + Long.toHexString(ident) + " to 0x"
                        + Long.toHexString(newIdent) + " while dispatching to "
                        + msg.target.getClass().getName() + " "
                        + msg.callback + " what=" + msg.what);
            }

            msg.recycleUnchecked();
        }
    }
```
可以看到，代码第6行，从轮询器中获取消息队列。接着通过一个死循环来把消息队列中的消息逐个取出来。代码第14行，通过MessageQueue的next()方法取出消息，当queue.next返回null时会退出消息循环。有消息则调用msg.target.dispatchMessage(msg)，target就是发送message时跟message关联的handler，Message被处理后会被调用recycleUnchecked()进行回收。

接下来看看MessageQueue的next()方法  
【MessageQueue.java】
```
    Message next() {
        // Return here if the message loop has already quit and been disposed.
        // This can happen if the application tries to restart a looper after quit
        // which is not supported.
        final long ptr = mPtr;
        if (ptr == 0) {
            return null;
        }

        int pendingIdleHandlerCount = -1; // -1 only during first iteration
        int nextPollTimeoutMillis = 0;
        for (;;) {
            if (nextPollTimeoutMillis != 0) {
                Binder.flushPendingCommands();
            }

            nativePollOnce(ptr, nextPollTimeoutMillis);

            synchronized (this) {
                // Try to retrieve the next message.  Return if found.
                final long now = SystemClock.uptimeMillis();
                Message prevMsg = null;
                Message msg = mMessages;
                if (msg != null && msg.target == null) {
                    // Stalled by a barrier.  Find the next asynchronous message in the queue.
                    do {
                        prevMsg = msg;
                        msg = msg.next;
                    } while (msg != null && !msg.isAsynchronous());
                }
                if (msg != null) {
                    if (now < msg.when) {
                        // Next message is not ready.  Set a timeout to wake up when it is ready.
                        nextPollTimeoutMillis = (int) Math.min(msg.when - now, Integer.MAX_VALUE);
                    } else {
                        // Got a message.
                        mBlocked = false;
                        if (prevMsg != null) {
                            prevMsg.next = msg.next;
                        } else {
                            mMessages = msg.next;
                        }
                        msg.next = null;
                        if (DEBUG) Log.v(TAG, "Returning message: " + msg);
                        msg.markInUse();
                        return msg;
                    }
                } else {
                    // No more messages.
                    nextPollTimeoutMillis = -1;
                }

                // Process the quit message now that all pending messages have been handled.
                if (mQuitting) {
                    dispose();
                    return null;
                }

                // If first time idle, then get the number of idlers to run.
                // Idle handles only run if the queue is empty or if the first message
                // in the queue (possibly a barrier) is due to be handled in the future.
                if (pendingIdleHandlerCount < 0
                        && (mMessages == null || now < mMessages.when)) {
                    pendingIdleHandlerCount = mIdleHandlers.size();
                }
                if (pendingIdleHandlerCount <= 0) {
                    // No idle handlers to run.  Loop and wait some more.
                    mBlocked = true;
                    continue;
                }

                if (mPendingIdleHandlers == null) {
                    mPendingIdleHandlers = new IdleHandler[Math.max(pendingIdleHandlerCount, 4)];
                }
                mPendingIdleHandlers = mIdleHandlers.toArray(mPendingIdleHandlers);
            }

            // Run the idle handlers.
            // We only ever reach this code block during the first iteration.
            for (int i = 0; i < pendingIdleHandlerCount; i++) {
                final IdleHandler idler = mPendingIdleHandlers[i];
                mPendingIdleHandlers[i] = null; // release the reference to the handler

                boolean keep = false;
                try {
                    keep = idler.queueIdle();
                } catch (Throwable t) {
                    Log.wtf(TAG, "IdleHandler threw exception", t);
                }

                if (!keep) {
                    synchronized (this) {
                        mIdleHandlers.remove(idler);
                    }
                }
            }

            // Reset the idle handler count to 0 so we do not run them again.
            pendingIdleHandlerCount = 0;

            // While calling an idle handler, a new message could have been delivered
            // so go back and look again for a pending message without waiting.
            nextPollTimeoutMillis = 0;
        }
    }
```

大概意思是如果当前MessageQueue中存在mMessages就将这个消息取出来，标记为已用并从消息队列中移除该消息，然后让下一条消息成为mMessages，否则就进入一个阻塞状态，一直等到有新的消息入队。

接下来看看Handler的dispatchMessage()方法  
【Handler.java】

```
    public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            handleMessage(msg);
        }
    }
```
可以看到，第2行进行判断，如果msg.callback不为空，则调用handleCallback(msg);方法，否则直接调用Handler的handleMessage()方法。这里的handleMessage()方法是不是很熟悉？没错！就是我们在主线程中处理消息的handleMessage()方法。

接下来看看Handler的handleCallback()方法  
【Handler.java】
```
	private static void handleCallback(Message message) {
        	message.callback.run();
    	}
```
可以看到，处理消息是在run方法中，即Runnable对象的run方法，也就是我们不用最常用的方法使用handle，而是以callback的方式使用。如下：

```
	//1，创建Handler（主线程）
	Handler mHandler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
            //3,处理消息
                mMessage = Message.obtain(mHandler, new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("--------------callback的形式处理消息--------------" + mMessage.arg1);
                    }
                });
                mMessage.arg1 = 666;
                //2,发送消息
                mHandler.sendMessage(mMessage);

            }
        }).start();
```
其实Handler的post()方法源码也是走了handleCallback()方法。自己点击post()方法进去看看就知道了。

### 小总结：通过轮询器的Looper.loop()方法中执行一个死循环把消息队列中的消息逐个取出来。死循环中主要通过MessageQueue的next()方法取出消息，取出消息后通过调用Handler的dispatchMessage()方法最终在handleCallback(msg)或者handleMessage(msg)方法处理消息。

### 好了，Handler消息机制源码终于分析完了。希望这篇文章可以在面试当中帮到你们。

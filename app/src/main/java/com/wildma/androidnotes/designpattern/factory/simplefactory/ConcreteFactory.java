package com.wildma.androidnotes.designpattern.factory.simplefactory;

import com.wildma.androidnotes.designpattern.factory.Product;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体工厂}
 */
public class ConcreteFactory {

    public static <T extends Product> T createProduct(Class<T> clz) {
        Product product = null;
        String classname = clz.getName();
        try {
            product = (Product) Class.forName(classname).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) product;
    }
}

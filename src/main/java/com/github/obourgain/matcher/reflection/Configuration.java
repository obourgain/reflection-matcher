package com.github.obourgain.matcher.reflection;

import java.lang.reflect.Constructor;

/**
 * @author olivier bourgain
 */
public class Configuration {

    private boolean ignoreTransient;

    private Class<? extends Throwable> exceptionClass;
    protected Constructor<? extends Throwable> exceptionConstructorWithObject;
    protected Constructor<? extends Throwable> exceptionConstructorWithoutParams;


    private Configuration() {
    }

    public static Configuration builder() {
        Configuration configuration = new Configuration();
        configuration.ignoreTransient = false;
        configuration.exceptionClass(AssertionError.class);
        return configuration;
    }

    public boolean ignoreTransient() {
        return ignoreTransient;
    }

    public Configuration ignoreTransient(boolean ignoreTransient) {
        this.ignoreTransient = ignoreTransient;
        return this;
    }

    public Configuration exceptionClass(Class<? extends Throwable> exceptionClass) {
        this.exceptionClass = exceptionClass;
        initException();
        return this;
    }

    private void initException() {
        try {
            // constructor with String param is private whereas constructor with Object param is public and does the toString()
            exceptionConstructorWithObject = exceptionClass.getConstructor(Object.class);
            exceptionConstructorWithoutParams = exceptionClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}

package com.github.obourgain.matcher.reflection;

import com.github.obourgain.matcher.reflection.custom.CustomComparison;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TODO have a better specific field comparison mechanism, maps are too limited
 * @author olivier bourgain
 */
public class Configuration {

    private boolean ignoreTransient;

    private Class<? extends Throwable> exceptionClass;
    protected Constructor<? extends Throwable> exceptionConstructorWithObject;
    protected Constructor<? extends Throwable> exceptionConstructorWithoutParams;
    protected Set<Field> ignoredFields = new HashSet<>();

    protected Map<Class, Class<? extends CustomComparison>> customClassComparisons = new HashMap<>();
    protected Map<Field, Class<? extends CustomComparison>> customFieldComparisons = new HashMap<>();

    private Configuration() {
    }

    public boolean isIgnored(Field field) {
        return ignoredFields.contains(field);
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

    public Configuration addIgnoredFields(Field field) {
        ignoredFields.add(field);
        return this;
    }

    public Configuration addCustomClassComparison(Class clazz, Class<? extends CustomComparison> customComparison) {
        customClassComparisons.put(clazz, customComparison);
        return this;
    }

    public Configuration addCustomFieldComparison(Field field, Class<? extends CustomComparison> customComparison) {
        customFieldComparisons.put(field, customComparison);
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

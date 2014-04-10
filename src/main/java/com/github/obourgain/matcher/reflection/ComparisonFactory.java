package com.github.obourgain.matcher.reflection;

import com.github.obourgain.matcher.reflection.custom.AbstractCustomComparison;
import com.github.obourgain.matcher.reflection.custom.CustomComparison;
import com.github.obourgain.matcher.reflection.custom.StringComparison;
import com.github.obourgain.matcher.reflection.path.PathStack;

import java.lang.reflect.Field;

/**
 * @author olivier bourgain
 */
public class ComparisonFactory {

    private final Configuration configuration;
    private final Assertions assertions;
    private final PathStack pathStack;

    public ComparisonFactory(Configuration configuration, Assertions assertions, PathStack pathStack) {
        this.configuration = configuration;
        this.assertions = assertions;
        this.pathStack = pathStack;
    }

    public CustomComparison getObjectComparison(Class<?> clazz) {
        Class<? extends CustomComparison> customComparisonClass = configuration.customClassComparisons.get(clazz);

        if (customComparisonClass == null) {
            return null;
        }

        CustomComparison customComparison;
        try {
            customComparison = customComparisonClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assert customComparison instanceof AbstractCustomComparison;
        ((AbstractCustomComparison) customComparison).setAssertions(assertions);
        ((AbstractCustomComparison) customComparison).setConfiguration(configuration);
        ((AbstractCustomComparison) customComparison).setPathStack(pathStack);
        return customComparison;
    }

    public CustomComparison getFieldComparison(Field field) {
        Class<? extends CustomComparison> customComparisonClass = configuration.customFieldComparisons.get(field);

        if (customComparisonClass == null) {
            return null;
        }

        CustomComparison customComparison;
        try {
            customComparison = customComparisonClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assert customComparison instanceof AbstractCustomComparison;
        ((AbstractCustomComparison) customComparison).setAssertions(assertions);
        ((AbstractCustomComparison) customComparison).setConfiguration(configuration);
        ((AbstractCustomComparison) customComparison).setPathStack(pathStack);
        return customComparison;
    }

}

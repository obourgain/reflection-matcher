package com.github.obourgain.matcher.reflection;

import com.github.obourgain.matcher.reflection.custom.CustomComparison;
import com.github.obourgain.matcher.reflection.graph.ObjectGraph;
import com.github.obourgain.matcher.reflection.path.PathStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author olivier bourgain
 */
public class ReflectionMatcher {

    public static final float DELTA_FLOAT = 0.01f;
    public static final double DELTA_DOUBLE = 0.01d;

    private static final Logger logger = LoggerFactory.getLogger(ReflectionMatcher.class);

    private Assertions assertions;

    private PathStack pathStack = new PathStack();
    private ObjectGraph objectGraph = new ObjectGraph();
    private Configuration configuration;
    private ComparisonFactory comparisonFactory;

    public ReflectionMatcher() {
        this(Configuration.builder());
    }

    public ReflectionMatcher(Configuration configuration) {
        this.configuration = configuration;
        assertions = new Assertions(configuration);
        comparisonFactory = new ComparisonFactory(configuration, assertions, pathStack, this);
    }

    public void compareObjects(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return;
        }
        if (obj1 == null) {
            assertions.fail("comparing non null with null");
        }
        if (obj2 == null) {
            assertions.fail("comparing non null with null");
        }

        if(obj1 == obj2) {
            return;
        }

        assert obj1 != null;
        assert obj2 != null;

        try {
            CustomComparison customComparator = comparisonFactory.getObjectComparison(obj1.getClass());
            if(customComparator != null) {
                customComparator.compare(obj1, obj2);
            } else {
                if (obj1.getClass() != obj2.getClass()) {
                    assertions.fail("comparing objects with different classes " + obj1.getClass() + " " + obj2.getClass());
                }

                compareFields(obj1, obj2, obj1.getClass().getDeclaredFields());

                Class<?> clazz = obj1.getClass();
                while(clazz.getSuperclass() != null) {
                    clazz = clazz.getSuperclass();
                    compareFields(obj1, obj2, clazz.getDeclaredFields());
                }
            }
        } catch (Throwable t) {
            logger.error(path());
            Assertions.internalThrow(t);
        }
    }

    private void compareFields(Object obj1, Object obj2, Field[] declaredFields) throws IllegalAccessException {
        for (Field field : declaredFields) {
            if(configuration.ignoreTransient() && Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            if(configuration.isIgnored(field)) {
                continue;
            }
            pathStack.push(field.getName());
            CustomComparison fieldComparison = comparisonFactory.getFieldComparison(field);
            if(fieldComparison != null) {
                field.setAccessible(true);
                fieldComparison.compare(field.get(obj1), field.get(obj2));
            } else {
                compareField(obj1, obj2, field);
            }
            pathStack.pop();
        }
    }

    private void compareField(Object obj1, Object obj2, Field field) throws IllegalAccessException {
        if (objectGraph.isVisited(obj1, field)) {
            logger.trace("skipping already visited {}#{}", obj1.getClass().getName(), field);
            return;
        }
        objectGraph.add(obj1, field);
        field.setAccessible(true);
        if (field.getType().isPrimitive()) {
            comparePrimitives(obj1, obj2, field);
        } else if (field.getType().isArray()) {
            compareArrays(obj1, obj2, field);
        } else {
            compareObjects(field.get(obj1), field.get(obj2));
        }
    }

    private void compareArrays(Object obj1, Object obj2, Field field) throws IllegalAccessException {
        assert field.getType().isArray();

        Object arrayObj1 = field.get(obj1);
        Object arrayObj2 = field.get(obj2);
        doCompareArrays(arrayObj1, arrayObj2);
    }

    private void doCompareArrays(Object arrayObj1, Object arrayObj2) throws IllegalAccessException {

        if(arrayObj1 == null && arrayObj2 == null) {
            return;
        } else if(arrayObj1 == null) {
            assertions.fail("comparing null array with non null array");
        } else if(arrayObj2 == null) {
            assertions.fail("comparing null array with non null array");
        }

        if(arrayObj1 == arrayObj2) {
            return;
        }

        assert arrayObj1 != null;
        assert arrayObj2 != null;

        int length = Array.getLength(arrayObj1);
        assertions.assertEquals(length, Array.getLength(arrayObj2));
        Class<?> componentType = arrayObj1.getClass().getComponentType();
        if (componentType != arrayObj2.getClass().getComponentType()) {
            assertions.fail("comparing arrays with different types " + componentType.getName() + " / " + arrayObj2.getClass().getComponentType());
        }
        if (componentType.isArray()) {
            compareArrayOfArrays(arrayObj1, arrayObj2, length);
        } else if (componentType.isPrimitive()) {
            comparePrimitiveArrays(arrayObj1, arrayObj2, componentType);
        } else {
            compareObjectArrays(arrayObj1, arrayObj2, length);
        }
    }

    private void compareArrayOfArrays(Object obj1, Object obj2, int length) throws IllegalAccessException {
        for (int i = 0; i < length; i++) {
            pathStack.push(i);
            doCompareArrays(Array.get(obj1, i), Array.get(obj2, i));
            pathStack.pop();
        }
    }

    private void compareObjectArrays(Object arrayObj1, Object arrayObj2, int length) throws IllegalAccessException {
        for (int i = 0; i < length; i++) {
            pathStack.push(i);
            compareObjects(Array.get(arrayObj1, i), Array.get(arrayObj2, i));
            pathStack.pop();
        }
    }

    private void comparePrimitives(Object obj1, Object obj2, Field field) throws IllegalAccessException {
        if (field.getType() == int.class) {
            assertions.assertEquals(field.getInt(obj1), field.getInt(obj2));
        } else if (field.getType() == long.class) {
            assertions.assertEquals(field.getLong(obj1), field.getLong(obj2));
        } else if (field.getType() == short.class) {
            assertions.assertEquals(field.getShort(obj1), field.getShort(obj2));
        } else if (field.getType() == char.class) {
            assertions.assertEquals(field.getChar(obj1), field.getChar(obj2));
        } else if (field.getType() == byte.class) {
            assertions.assertEquals(field.getByte(obj1), field.getByte(obj2));
        } else if (field.getType() == boolean.class) {
            assertions.assertEquals(field.getBoolean(obj1), field.getBoolean(obj2));
        } else if (field.getType() == float.class) {
            assertions.assertEquals(field.getFloat(obj1), field.getFloat(obj2), DELTA_FLOAT);
        } else if (field.getType() == double.class) {
            assertions.assertEquals(field.getDouble(obj1), field.getDouble(obj2), DELTA_DOUBLE);
        }
    }

    private void comparePrimitiveArrays(Object arrayObj1, Object arrayObj2, Class<?> componentType) {
        // hoist the loop counter out to avoid flooding the pathStack
        // we have a primitive type, which can not have references to other stuff
        // so it's safe to catch the AssertionError outside of the loop
        int i = 0;
        try {
            if (componentType == int.class) {
                for (i = 0; i < Array.getLength(arrayObj1); i++) {
                    assertions.assertEquals(Array.getInt(arrayObj1, i), Array.getInt(arrayObj2, i));
                }
            } else if (componentType == long.class) {
                for (i = 0; i < Array.getLength(arrayObj1); i++) {
                    assertions.assertEquals(Array.getLong(arrayObj1, i), Array.getLong(arrayObj2, i));
                }
            } else if (componentType == short.class) {
                for (i = 0; i < Array.getLength(arrayObj1); i++) {
                    assertions.assertEquals(Array.getShort(arrayObj1, i), Array.getShort(arrayObj2, i));
                }
            } else if (componentType == char.class) {
                for (i = 0; i < Array.getLength(arrayObj1); i++) {
                    assertions.assertEquals(Array.getChar(arrayObj1, i), Array.getChar(arrayObj2, i));
                }
            } else if (componentType == byte.class) {
                for (i = 0; i < Array.getLength(arrayObj1); i++) {
                    assertions.assertEquals(Array.getByte(arrayObj1, i), Array.getByte(arrayObj2, i));
                }
            } else if (componentType == boolean.class) {
                for (i = 0; i < Array.getLength(arrayObj1); i++) {
                    assertions.assertEquals(Array.getBoolean(arrayObj1, i), Array.getBoolean(arrayObj2, i));
                }
            } else if (componentType == float.class) {
                for (i = 0; i < Array.getLength(arrayObj1); i++) {
                    assertions.assertEquals(Array.getFloat(arrayObj1, i), Array.getFloat(arrayObj2, i), DELTA_FLOAT);
                }
            } else if (componentType == double.class) {
                for (i = 0; i < Array.getLength(arrayObj1); i++) {
                    assertions.assertEquals(Array.getDouble(arrayObj1, i), Array.getDouble(arrayObj2, i), DELTA_DOUBLE);
                }
            }
        } catch (Throwable e) {
            pathStack.push(i);
            Assertions.internalThrow(e);
        }
    }

    public String path() {
        return pathStack.pathAsString();
    }

}

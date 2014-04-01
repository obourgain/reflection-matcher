package com.github.obourgain.matcher.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author olivier bourgain
 */
public abstract class AbstractTest {

    protected Configuration configuration = Configuration.builder()
            .ignoreTransient(false)
            .exceptionClass(MatchException.class);

    protected Assertions assertions = new Assertions(configuration);

    protected ReflectionMatcher matcher = new ReflectionMatcher(configuration);

    protected static void setValue(Object theObject, String fieldName, Object value) {
        try {
            Field field = findField(theObject.getClass(), fieldName);
            field.setAccessible(true);
            field.set(theObject, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected static <T> void setPrimitiveValue(Object theObject, String fieldName, Class<?> clazz, String value) {
        try {
            T parsedValue;
            if (clazz == Character.class) {
                // no valueOf(String) on Character, we must hack
                Method charAt = String.class.getDeclaredMethod("charAt", int.class);
                parsedValue = (T) charAt.invoke(value, 0);
            } else {
                Method valueOf = clazz.getDeclaredMethod("valueOf", String.class);
                valueOf.setAccessible(true);
                parsedValue = (T) valueOf.invoke(null, value);
            }

            Field field = findField(theObject.getClass(), fieldName);
            field.setAccessible(true);
            field.set(theObject, parsedValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected static void setArrayValue(Object theObject, String fieldName, Class<?> clazz, String value) throws Exception {
        try {
            String[] values = value.split(",");
            Object[] parsedValues = new Object[values.length];
            if (clazz == Character.class) {
                // no valueOf(String) on Character, we must hack
                Method charAt = String.class.getDeclaredMethod("charAt", int.class);
                for (int i = 0; i < values.length; i++) {
                    parsedValues[i] = charAt.invoke(values[i], 0);
                }
            } else {
                Method valueOf = clazz.getDeclaredMethod("valueOf", String.class);
                valueOf.setAccessible(true);
                for (int i = 0; i < values.length; i++) {
                    parsedValues[i] = valueOf.invoke(null, values[i]);
                }
            }

            Field field = findField(theObject.getClass(), fieldName);
            field.setAccessible(true);

            assert field.getType().isArray();
            Object valuesAsT = Array.newInstance(field.getType().getComponentType(), parsedValues.length);

            // do not use arrayCopy as it does not unwrap the values
            for (int i = 0; i < parsedValues.length; i++) {
                Array.set(valuesAsT, i, parsedValues[i]);
            }

            field.set(theObject, valuesAsT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds the field with the given name in the given object's class hierarchy
     */
    private static Field findField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superclass = clazz.getSuperclass();
            if(superclass != null) {
                return findField(superclass, fieldName);
            }
            throw new RuntimeException(e);
        }
    }

}

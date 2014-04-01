package com.github.obourgain.matcher.reflection;

import java.lang.reflect.InvocationTargetException;

/**
 * Some code taken or heavily inspired from JUnit's org.junit.Assert
 * @author olivier bourgain
 */
public class Assertions {

    private final Configuration configuration;

    public Assertions(Configuration configuration) {
        this.configuration = configuration;
    }

    public void fail(String message) {
        doThrow(message);
    }

    public void fail() {
        doThrow();
    }

    public void assertEquals(long expected, long actual) {
        assertEquals((Long) expected, (Long) actual);
    }

    public void assertEquals(boolean expected, boolean actual) {
        assertEquals((Boolean) expected, (Boolean) actual);
    }

    public void assertEquals(float expected, float actual, float delta) {
        if (Float.compare(expected, actual) == 0) {
            return;
        }
        if (!(Math.abs(expected - actual) <= delta)) {
            fail(String.format("%f / %f", expected, actual));
        }
    }

    public void assertEquals(double expected, double actual, double delta) {
        if (doubleIsDifferent(expected, actual, delta)) {
            fail(String.format("%f / %f", expected, actual));
        }
    }

    static private boolean doubleIsDifferent(double d1, double d2, double delta) {
        if (Double.compare(d1, d2) == 0) {
            return false;
        }
        if ((Math.abs(d1 - d2) <= delta)) {
            return false;
        }

        return true;
    }

    public void assertEquals(Object expected, Object actual) {
        if(nullSafeEquals(expected, actual)) {
            return;
        }
        fail(expected + " / " + actual);
    }

    public void assertTrue(String message, boolean condition) {
        if(!condition) {
            fail(message);
        }
    }

    public void assertTrue(boolean condition) {
        assertTrue("", condition);
    }

    private static boolean nullSafeEquals(Object expected, Object actual) {
        if (expected == null) {
            return actual == null;
        }
        return expected.equals(actual);
    }

    public void doThrow() {
        try {
            Throwable throwable = configuration.exceptionConstructorWithoutParams.newInstance();
            internalThrow(throwable);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void doThrow(String message) {
        try {
            Throwable throwable = configuration.exceptionConstructorWithObject.newInstance(message);
            internalThrow(throwable);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void internalThrow(final Throwable t) {
        // dirty trick to be able to throw any kind of stuff without declaring throws Throwable everywhere
        class Thrower<T extends Throwable> {
            @SuppressWarnings("unchecked")
            private void sneakyThrow(Throwable exception) throws T {
                throw (T) exception;
            }
        }
        new Thrower<RuntimeException>().sneakyThrow(t);
    }
}

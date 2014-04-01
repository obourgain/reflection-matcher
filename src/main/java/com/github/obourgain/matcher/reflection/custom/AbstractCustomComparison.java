package com.github.obourgain.matcher.reflection.custom;

import com.github.obourgain.matcher.reflection.Assertions;
import com.github.obourgain.matcher.reflection.Configuration;
import com.github.obourgain.matcher.reflection.path.PathStack;

/**
 * @author olivier bourgain
 */
public abstract class AbstractCustomComparison<T> implements CustomComparison<T> {

    protected Configuration configuration;
    protected Assertions assertions;
    protected PathStack pathStack;

    protected abstract void doCompare(T t1, T t2);

    public void compare(T t1, T t2) {
        if (t1 == null && t2 == null) {
            return;
        }
        if (t1 == null) {
            assertions.fail("comparing non null with null");
        }
        if (t2 == null) {
            assertions.fail("comparing non null with null");
        }

        if(t1 == t2) {
            return;
        }

        assert t1 != null;
        assert t2 != null;

        if (t1.getClass() != t2.getClass()) {
            assertions.fail("comparing objects with different classes " + t1.getClass() + " " + t2.getClass());
        }
        doCompare(t1, t2);
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setAssertions(Assertions assertions) {
        this.assertions = assertions;
    }

    public void setPathStack(PathStack pathStack) {
        this.pathStack = pathStack;
    }
}

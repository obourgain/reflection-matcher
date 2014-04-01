package com.github.obourgain.matcher.reflection.custom;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author olivier bourgain
 */
public class CollectionComparison<T extends Collection<E>, E> extends AbstractCustomComparison<T> {

    @Override
    protected void doCompare(T col1, T col2) {
        int size1 = col1.size();
        int size2 = col2.size();

        assertions.assertEquals(size1, size2);

        for (E e : col1) {
            assertions.assertTrue(e + " is not in the other collection", col2.contains(e));
        }

    }
}

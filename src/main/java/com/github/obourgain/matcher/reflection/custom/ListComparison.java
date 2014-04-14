package com.github.obourgain.matcher.reflection.custom;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author olivier bourgain
 */
public class ListComparison<T extends Collection<E>, E> extends AbstractCustomComparison<T> {

    @Override
    protected void doCompare(T col1, T col2) {
        int size1 = col1.size();
        int size2 = col2.size();

        assertions.assertEquals(size1, size2);

        Iterator<E> iterator1 = col1.iterator();
        Iterator<E> iterator2 = col2.iterator();

        for (int i = 0; i < col1.size(); i++) {
            pathStack.push(i);
            reflectionMatcher.compareObjects(iterator1.next(), iterator2.next());
            pathStack.pop();
        }
    }
}

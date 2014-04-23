package com.github.obourgain.matcher.reflection.custom;

import java.util.Map;

/**
 * Compares the content of two {@link Map} without checking class equality.
 *
 * @author olivier bourgain
 */
public class MapContentComparison<T extends Map<K, V>, K, V> extends AbstractCustomComparison<T> {

    @Override
    public void compare(T map1, T map2) {
        if (map1 == null && map2 == null) {
            return;
        }
        if (map1 == null) {
            assertions.fail("comparing null with non null");
        }
        if (map2 == null) {
            assertions.fail("comparing non null with null");
        }

        if(map1 == map2) {
            return;
        }

        assert map1 != null;
        assert map2 != null;

        int size1 = map1.size();
        int size2 = map2.size();

        assertions.assertEquals(size1, size2);

        for (Map.Entry<K, V> entry : map1.entrySet()) {
            V v = map2.get(entry.getKey());
            reflectionMatcher.compareObjects(entry.getValue(), v);
        }

    }

    @Override
    protected void doCompare(T t1, T t2) {
        // no nop
    }
}

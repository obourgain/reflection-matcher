package com.github.obourgain.matcher.reflection.custom;

import java.util.Map;

/**
 * @author olivier bourgain
 */
public class MapComparison<T extends Map<K, V>, K, V> extends AbstractCustomComparison<T> {

    @Override
    protected void doCompare(T map1, T map2) {
        int size1 = map1.size();
        int size2 = map2.size();

        assertions.assertEquals(size1, size2);

        for (Map.Entry<K, V> e : map1.entrySet()) {
            V v = map2.get(e.getKey());
            assertions.assertEquals(e.getValue(), v);
        }

    }
}

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

        for (Map.Entry<K, V> entry : map1.entrySet()) {
            V v = map2.get(entry.getKey());
            reflectionMatcher.compareObjects(entry.getValue(), v);
        }

    }
}

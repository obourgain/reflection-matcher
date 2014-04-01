package com.github.obourgain.matcher.reflection.graph;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author olivier bourgain
 */
public class ObjectGraph {

    private Set<FieldReference> visitedReferences = new HashSet<>();

    public void add(Object object, Field field) {
        visitedReferences.add(new FieldReference(object, field));
    }

    public boolean isVisited(Object object, Field field) {
        return visitedReferences.contains(new FieldReference(object, field));
    }

}

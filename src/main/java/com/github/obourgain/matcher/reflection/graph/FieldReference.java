package com.github.obourgain.matcher.reflection.graph;

import java.lang.reflect.Field;

/**
 * We should not visit two times the same field to handle cycles in graphs
 * A field is identified by its containing object and the {@link Field} instance.
 *
 * This relies on the {@link Field#equals} behavior
 *
 * @author olivier bourgain
 */
public class FieldReference {

    final Object object;
    final Field field;

    public FieldReference(Object object, Field field) {
        this.object = object;
        this.field = field;
    }

    public Object getObject() {
        return object;
    }

    public Field getField() {
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldReference that = (FieldReference) o;

        return field.equals(that.field) && object.equals(that.object);
    }

    @Override
    public int hashCode() {
        int result = object.hashCode();
        result = 31 * result + field.hashCode();
        return result;
    }
}

package com.github.obourgain.matcher.reflection.theory;

public class Assignment {
    final String fieldName;
    final Class<?> clazz;

    Assignment(String fieldName, Class<?> clazz) {
        this.fieldName = fieldName;
        this.clazz = clazz;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

}

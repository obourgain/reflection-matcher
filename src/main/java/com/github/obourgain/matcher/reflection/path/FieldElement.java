package com.github.obourgain.matcher.reflection.path;

/**
 * @author olivier bourgain
 */
public class FieldElement implements PathElement {

    private String name;

    public FieldElement(String name) {
        this.name = name;
    }

    @Override

    public String asString() {
        return name;
    }
}

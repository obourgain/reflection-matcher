package com.github.obourgain.matcher.reflection.path;

/**
 * @author olivier bourgain
 */
public class ArrayElement implements PathElement {

    private int index;

    public ArrayElement(int index) {
        this.index = index;
    }

    @Override
    public String asString() {
        return "#" + index;
    }
}

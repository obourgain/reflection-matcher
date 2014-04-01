package com.github.obourgain.matcher.reflection.path;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * @author olivier bourgain
 */
public class PathStack {

    private Deque<PathElement> path = new ArrayDeque<>();

    public void push(String string) {
        path.push(new FieldElement(string));
    }

    public void push(int index) {
        path.push(new ArrayElement(index));
    }

    public void pop() {
        PathElement pop = path.pop();
    }

    public String pathAsString() {
        StringBuilder builder = new StringBuilder();
        for (Iterator<PathElement> iterator = path.descendingIterator(); iterator.hasNext(); ) {
            PathElement element = iterator.next();
            builder.append(element.asString());
            if (iterator.hasNext()) {
                builder.append(".");
            }
        }
        return builder.toString();
    }

}

package com.github.obourgain.matcher.reflection.custom;

/**
 * @author olivier bourgain
 */
public class StringComparison extends AbstractCustomComparison<String> {

    @Override
    protected void doCompare(String s1, String s2) {
        // do not compare on internal representation, only according to the equals method
        assertions.assertEquals(s1, s2);
    }
}

package com.github.obourgain.matcher.reflection;

import com.github.obourgain.matcher.reflection.data.AnObjectWithSomeFields;
import com.github.obourgain.matcher.reflection.data.AnOtherObjectWithTheSameFields;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;

/**
 * @author olivier bourgain
 */
public class SimilarClassesTest extends AbstractTest {

    @Test
    public void classesWithSameStructureDoesNotMatch() throws Exception {
        AnObjectWithSomeFields theObject = new AnObjectWithSomeFields();
        AnOtherObjectWithTheSameFields theOtherObject = new AnOtherObjectWithTheSameFields();

        setPrimitiveValue(theObject, "anInt", Integer.class, "1");
        setPrimitiveValue(theOtherObject, "anInt", Integer.class, "1");

        setValue(theObject, "aString", "string");
        setValue(theOtherObject, "aString", "string");

        try {
            matcher.compareObjects(theObject, theOtherObject);
            Assert.fail();
        } catch (MatchException e) {
            Assert.assertThat(e.getMessage(), containsString("comparing objects with different classes"));
        }
    }

}

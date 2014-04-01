package com.github.obourgain.matcher.reflection;

import com.github.obourgain.matcher.reflection.data.TheObject;
import com.github.obourgain.matcher.reflection.data.TheObjectOfASubclass;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

/**
 * @author olivier bourgain
 */
public class ReflectionMatcherTransientTest {

    @Test
    public void testAssertTransientIsIgnored() throws Exception {
        TheObjectOfASubclass theObject1 = new TheObjectOfASubclass();
        TheObjectOfASubclass theObject2 = new TheObjectOfASubclass();
        TheObject theNestedObject1 = new TheObject();

        theObject1.setTheTransientField(theNestedObject1);
        // no object referenced in transient field

        ReflectionMatcher matcher = new ReflectionMatcher(Configuration.builder().ignoreTransient(true));
        matcher.compareObjects(theObject1, theObject2);
    }

    @Test
    public void testAssertTransientIsNotIgnored() throws Exception {
        TheObjectOfASubclass theObject1 = new TheObjectOfASubclass();
        TheObjectOfASubclass theObject2 = new TheObjectOfASubclass();
        TheObject theNestedObject1 = new TheObject();

        theObject1.setTheTransientField(theNestedObject1);
        // no object referenced in transient field

        ReflectionMatcher matcher = new ReflectionMatcher(Configuration.builder().ignoreTransient(false).exceptionClass(MatchException.class));
        boolean failed = false;
        try {
            matcher.compareObjects(theObject1, theObject2);
            failed = true;
        } catch (MatchException e) {
            Assert.assertThat(matcher.path(), is("theTransientField"));
        }

        if (failed) {
            Assert.fail("transient field was compared");
        }
    }

}

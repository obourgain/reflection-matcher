package com.github.obourgain.matcher.reflection;

import com.github.obourgain.matcher.reflection.data.TheObject;
import com.github.obourgain.matcher.reflection.data.TheObjectOfASubclass;
import com.github.obourgain.matcher.reflection.theory.Assignment;
import com.github.obourgain.matcher.reflection.theory.PrimitiveFieldsNames;
import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

/**
 * @author olivier bourgain
 */
@RunWith(Theories.class)
public class ReflectionMatcherInheritanceTest extends AbstractTest {

    Map<Class<?>, String> equalsValues = new HashMap<>();

    Map<Class<?>, String> failingValues = new HashMap<>();

    @Before
    public void setUpValues() {
        equalsValues.put(Integer.class, "1");
        equalsValues.put(Long.class, "1");
        equalsValues.put(Short.class, "1");
        equalsValues.put(Character.class, "A");
        equalsValues.put(Byte.class, "1");
        equalsValues.put(Boolean.class, "true");
        equalsValues.put(Float.class, "1.0");
        equalsValues.put(Double.class, "1.0");

        failingValues.put(Integer.class, "2");
        failingValues.put(Long.class, "2");
        failingValues.put(Short.class, "2");
        failingValues.put(Character.class, "B");
        failingValues.put(Byte.class, "2");
        failingValues.put(Boolean.class, "false");
        failingValues.put(Float.class, "2.0");
        failingValues.put(Double.class, "2.0");
    }

    @Theory
    public void testAssertPrimitiveEquals(@PrimitiveFieldsNames(fieldNames = {"anInt", "aLong", "aShort", "aChar", "aByte", "aBoolean", "aFloat", "aDouble"},
            classes = {Integer.class, Long.class, Short.class, Character.class, Byte.class, Boolean.class, Float.class, Double.class})
                                          Assignment assignment) throws Exception {
        TheObjectOfASubclass theObject1 = new TheObjectOfASubclass();
        TheObjectOfASubclass theObject2 = new TheObjectOfASubclass();

        setPrimitiveValue(theObject1, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));
        setPrimitiveValue(theObject2, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));

        matcher.compareObjects(theObject1, theObject2);
    }

    @Theory
    public void testAssertPrimitiveNotEquals(@PrimitiveFieldsNames(fieldNames = {"anInt", "aLong", "aShort", "aChar", "aByte", "aBoolean", "aFloat", "aDouble"},
            classes = {Integer.class, Long.class, Short.class, Character.class, Byte.class, Boolean.class, Float.class, Double.class})
                                          Assignment assignment) throws Exception {
        TheObject theObject1 = new TheObject();
        TheObject theObject2 = new TheObject();

        setPrimitiveValue(theObject1, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));
        setPrimitiveValue(theObject2, assignment.getFieldName(), assignment.getClazz(), failingValues.get(assignment.getClazz()));

        boolean failed = false;
        try {
            matcher.compareObjects(theObject1, theObject2);
            // can not use Assert.fail() because we catch the AssertionError ...
            failed = true;
        } catch (MatchException e) {
            Assert.assertThat(matcher.path(), is(assignment.getFieldName()));
        }
        if(failed) {
            Assert.fail();
        }
    }

    @Theory
    public void testAssertPrimitiveInReferencedObjectEquals(@PrimitiveFieldsNames(fieldNames = {"anInt", "aLong", "aShort", "aChar", "aByte", "aBoolean", "aFloat", "aDouble"},
            classes = {Integer.class, Long.class, Short.class, Character.class, Byte.class, Boolean.class, Float.class, Double.class})
                                          Assignment assignment) throws Exception {
        TheObjectOfASubclass theObject1 = new TheObjectOfASubclass();
        TheObjectOfASubclass theObject2 = new TheObjectOfASubclass();
        TheObject theNestedObject1 = new TheObject();
        TheObject theNestedObject2 = new TheObject();

        setPrimitiveValue(theNestedObject1, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));
        setPrimitiveValue(theNestedObject2, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));

        theObject1.setAnObject(theNestedObject1);
        theObject2.setAnObject(theNestedObject2);

        matcher.compareObjects(theObject1, theObject2);
    }

    @Theory
    public void testAssertPrimitiveInReferencedObjectNotEquals(@PrimitiveFieldsNames(fieldNames = {"anInt", "aLong", "aShort", "aChar", "aByte", "aBoolean", "aFloat", "aDouble"},
            classes = {Integer.class, Long.class, Short.class, Character.class, Byte.class, Boolean.class, Float.class, Double.class})
                                          Assignment assignment) throws Exception {
        TheObjectOfASubclass theObject1 = new TheObjectOfASubclass();
        TheObjectOfASubclass theObject2 = new TheObjectOfASubclass();
        TheObject theNestedObject1 = new TheObject();
        TheObject theNestedObject2 = new TheObject();

        setPrimitiveValue(theNestedObject1, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));
        setPrimitiveValue(theNestedObject2, assignment.getFieldName(), assignment.getClazz(), failingValues.get(assignment.getClazz()));

        theObject1.setAnObject(theNestedObject1);
        theObject2.setAnObject(theNestedObject2);

        boolean failed = false;
        try {
            matcher.compareObjects(theObject1, theObject2);
            failed = true;
        } catch (MatchException e) {
            Assert.assertThat(matcher.path(), is("anObject." + assignment.getFieldName()));
        }
        if(failed) {
            Assert.fail();
        }
    }

}

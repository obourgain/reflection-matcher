package com.github.obourgain.matcher.reflection;

import com.github.obourgain.matcher.reflection.data.TheObject;
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
public class ReflectionMatcherPrimitiveArraysTest extends AbstractTest {

    private Map<Class<?>, String> equalsValues = new HashMap<>();
    private Map<Class<?>, String> failingValuesAtIndex0 = new HashMap<>();
    private Map<Class<?>, String> failingValuesAtIndex1 = new HashMap<>();

    @Before
    public void setUpValues() {
        equalsValues.put(Integer.class, "1,2");
        equalsValues.put(Long.class, "1,2");
        equalsValues.put(Short.class, "1,2");
        equalsValues.put(Character.class, "A,B");
        equalsValues.put(Byte.class, "1,2");
        equalsValues.put(Boolean.class, "true,true");
        equalsValues.put(Float.class, "1.0,2.0");
        equalsValues.put(Double.class, "1.0,2.0");

        failingValuesAtIndex0.put(Integer.class, "2,3");
        failingValuesAtIndex0.put(Long.class, "2,3");
        failingValuesAtIndex0.put(Short.class, "2,3");
        failingValuesAtIndex0.put(Character.class, "B,C");
        failingValuesAtIndex0.put(Byte.class, "2,3");
        failingValuesAtIndex0.put(Boolean.class, "false,false");
        failingValuesAtIndex0.put(Float.class, "2.0,2.0");
        failingValuesAtIndex0.put(Double.class, "2.0,2.0");

        failingValuesAtIndex1.put(Integer.class, "1,3");
        failingValuesAtIndex1.put(Long.class, "1,3");
        failingValuesAtIndex1.put(Short.class, "1,3");
        failingValuesAtIndex1.put(Character.class, "A,C");
        failingValuesAtIndex1.put(Byte.class, "1,3");
        failingValuesAtIndex1.put(Boolean.class, "true,false");
        failingValuesAtIndex1.put(Float.class, "1.0,3.0");
        failingValuesAtIndex1.put(Double.class, "1.0,3.0");
    }

    @Theory
    public void testAssertPrimitiveEquals(@PrimitiveFieldsNames(fieldNames = {"anIntArray", "aLongArray", "aShortArray", "aCharArray", "aByteArray", "aBooleanArray", "aFloatArray", "aDoubleArray"},
            classes = {Integer.class, Long.class, Short.class, Character.class, Byte.class, Boolean.class, Float.class, Double.class})
                                          Assignment assignment) throws Exception {
        TheObject theObject1 = new TheObject();
        TheObject theObject2 = new TheObject();

        setArrayValue(theObject1, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));
        setArrayValue(theObject2, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));

        matcher.compareObjects(theObject1, theObject2);
    }

    @Theory
    public void testAssertPrimitiveNotEquals_atIndex0(@PrimitiveFieldsNames(fieldNames = {"anIntArray", "aLongArray", "aShortArray", "aCharArray", "aByteArray", "aBooleanArray", "aFloatArray", "aDoubleArray"},
            classes = {Integer.class, Long.class, Short.class, Character.class, Byte.class, Boolean.class, Float.class, Double.class})
                                          Assignment assignment) throws Exception {
        TheObject theObject1 = new TheObject();
        TheObject theObject2 = new TheObject();

        setArrayValue(theObject1, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));
        setArrayValue(theObject2, assignment.getFieldName(), assignment.getClazz(), failingValuesAtIndex0.get(assignment.getClazz()));

        try {
            matcher.compareObjects(theObject1, theObject2);
            Assert.fail();
        } catch (MatchException e) {
            Assert.assertThat(matcher.path(), is(assignment.getFieldName() + ".#0"));
        }
    }

    @Theory
    public void testAssertPrimitiveNotEquals_atIndex1(@PrimitiveFieldsNames(fieldNames = {"anIntArray", "aLongArray", "aShortArray", "aCharArray", "aByteArray", "aBooleanArray", "aFloatArray", "aDoubleArray"},
            classes = {Integer.class, Long.class, Short.class, Character.class, Byte.class, Boolean.class, Float.class, Double.class})
                                          Assignment assignment) throws Exception {
        TheObject theObject1 = new TheObject();
        TheObject theObject2 = new TheObject();

        setArrayValue(theObject1, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));
        setArrayValue(theObject2, assignment.getFieldName(), assignment.getClazz(), failingValuesAtIndex1.get(assignment.getClazz()));

        try {
            matcher.compareObjects(theObject1, theObject2);
            // can not use Assert.fail() because we catch the AssertionError ...
            Assert.fail();
        } catch (MatchException e) {
            Assert.assertThat(matcher.path(), is(assignment.getFieldName() + ".#1"));
        }
    }

    @Theory
    public void testAssertPrimitiveInReferencedObjectEquals(@PrimitiveFieldsNames(fieldNames = {"anIntArray", "aLongArray", "aShortArray", "aCharArray", "aByteArray", "aBooleanArray", "aFloatArray", "aDoubleArray"},
            classes = {Integer.class, Long.class, Short.class, Character.class, Byte.class, Boolean.class, Float.class, Double.class})
                                          Assignment assignment) throws Exception {
        TheObject theObject1 = new TheObject();
        TheObject theObject2 = new TheObject();
        TheObject theNestedObject1 = new TheObject();
        TheObject theNestedObject2 = new TheObject();

        setArrayValue(theNestedObject1, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));
        setArrayValue(theNestedObject2, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));

        theObject1.setAnObject(theNestedObject1);
        theObject2.setAnObject(theNestedObject2);

        matcher.compareObjects(theObject1, theObject2);
    }

    @Theory
    public void testAssertPrimitiveInReferencedObjectNotEquals_atIndex0(@PrimitiveFieldsNames(fieldNames = {"anIntArray", "aLongArray", "aShortArray", "aCharArray", "aByteArray", "aBooleanArray", "aFloatArray", "aDoubleArray"},
            classes = {Integer.class, Long.class, Short.class, Character.class, Byte.class, Boolean.class, Float.class, Double.class})
                                          Assignment assignment) throws Exception {
        TheObject theObject1 = new TheObject();
        TheObject theObject2 = new TheObject();
        TheObject theNestedObject1 = new TheObject();
        TheObject theNestedObject2 = new TheObject();

        setArrayValue(theNestedObject1, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));
        setArrayValue(theNestedObject2, assignment.getFieldName(), assignment.getClazz(), failingValuesAtIndex0.get(assignment.getClazz()));

        theObject1.setAnObject(theNestedObject1);
        theObject2.setAnObject(theNestedObject2);

        try {
            matcher.compareObjects(theObject1, theObject2);
            Assert.fail();
        } catch (MatchException e) {
            Assert.assertThat(matcher.path(), is("anObject." + assignment.getFieldName() + ".#0"));
        }
    }

    @Theory
    public void testAssertPrimitiveInReferencedObjectNotEquals_atIndex1(@PrimitiveFieldsNames(fieldNames = {"anIntArray", "aLongArray", "aShortArray", "aCharArray", "aByteArray", "aBooleanArray", "aFloatArray", "aDoubleArray"},
            classes = {Integer.class, Long.class, Short.class, Character.class, Byte.class, Boolean.class, Float.class, Double.class})
                                          Assignment assignment) throws Exception {
        TheObject theObject1 = new TheObject();
        TheObject theObject2 = new TheObject();
        TheObject theNestedObject1 = new TheObject();
        TheObject theNestedObject2 = new TheObject();

        setArrayValue(theNestedObject1, assignment.getFieldName(), assignment.getClazz(), equalsValues.get(assignment.getClazz()));
        setArrayValue(theNestedObject2, assignment.getFieldName(), assignment.getClazz(), failingValuesAtIndex1.get(assignment.getClazz()));

        theObject1.setAnObject(theNestedObject1);
        theObject2.setAnObject(theNestedObject2);

        try {
            matcher.compareObjects(theObject1, theObject2);
            Assert.fail();
        } catch (MatchException e) {
            Assert.assertThat(matcher.path(), is("anObject." + assignment.getFieldName() + ".#1"));
        }
    }

}

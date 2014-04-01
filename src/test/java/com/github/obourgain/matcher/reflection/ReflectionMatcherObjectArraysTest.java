package com.github.obourgain.matcher.reflection;

import com.github.obourgain.matcher.reflection.data.TheObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;

/**
 * @author olivier bourgain
 */
public class ReflectionMatcherObjectArraysTest extends AbstractTest {

    @Test
    public void testAssertObjectArrayEquals() throws Exception {
        TheObject theObject1 = new TheObject();
        TheObject theObject2 = new TheObject();

        TheObject[] array = new TheObject[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = new TheObject();
            array[i].setAnInt(i);
        }

        theObject1.setAnObjectArray(array);
        theObject2.setAnObjectArray(Arrays.copyOf(array, array.length)); // have a different reference

        matcher.compareObjects(theObject1, theObject2);
    }

    @Test
    public void testAssertObjectArrayNotEquals() throws Exception {
        TheObject theObject1 = new TheObject();
        TheObject theObject2 = new TheObject();

        TheObject[] theArray = new TheObject[10];
        for (int i = 0; i < theArray.length; i++) {
            theArray[i] = new TheObject();
            theArray[i].setAnInt(i);
        }

        theObject1.setAnObjectArray(theArray);

        TheObject[] theOtherArray = new TheObject[10];
        for (int i = 0; i < theArray.length; i++) {
            theOtherArray[i] = new TheObject();
            theOtherArray[i].setAnInt(i);
        }
        // modify one element
        theOtherArray[1].setALong(2);

        theObject2.setAnObjectArray(theOtherArray);

        boolean failed = false;
        try {
            matcher.compareObjects(theObject1, theObject2);
            failed = true;
        } catch (MatchException e) {
            Assert.assertThat(matcher.path(), is("anObjectArray.#1"));
        }
        if (failed) {
            Assert.fail();
        }
    }

    @Test
    public void testAssertArrayOfObjectArraysEquals() throws Exception {
        TheObject theObject1 = new TheObject();
        TheObject theObject2 = new TheObject();

        TheObject[][] array = new TheObject[10][];
        for (int i = 0; i < array.length; i++) {
            array[i] = new TheObject[i];
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = new TheObject();
                array[i][j].setAnInt(j);
            }
        }

        TheObject[][] theOtherArray = new TheObject[10][];
        for (int i = 0; i < theOtherArray.length; i++) {
            theOtherArray[i] = new TheObject[i];
            for (int j = 0; j < theOtherArray[i].length; j++) {
                theOtherArray[i][j] = new TheObject();
                theOtherArray[i][j].setAnInt(j);
            }
        }

        theObject1.setAnArrayOfObjectArrays(array);
        theObject2.setAnArrayOfObjectArrays(theOtherArray);

        matcher.compareObjects(theObject1, theObject2);
    }

    @Test
    public void testAssertArrayOfObjectArraysNotEquals() throws Exception {
        TheObject theObject1 = new TheObject();
        TheObject theObject2 = new TheObject();

        TheObject[][] array = new TheObject[10][];
        for (int i = 0; i < array.length; i++) {
            array[i] = new TheObject[i];
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = new TheObject();
                array[i][j].setAnInt(j);
            }
        }

        TheObject[][] theOtherArray = new TheObject[10][];
        for (int i = 0; i < theOtherArray.length; i++) {
            theOtherArray[i] = new TheObject[i];
            for (int j = 0; j < theOtherArray[i].length; j++) {
                theOtherArray[i][j] = new TheObject();
                theOtherArray[i][j].setAnInt(j);
            }
        }

        // modify one element, remember to take care of the index used, because we have arrays of increasing sizes
        theOtherArray[2][1].setALong(2);

        theObject1.setAnArrayOfObjectArrays(array);
        theObject2.setAnArrayOfObjectArrays(theOtherArray);

        boolean failed = false;
        try {
            matcher.compareObjects(theObject1, theObject2);
            failed = true;
        } catch (MatchException e) {
            Assert.assertThat(matcher.path(), is("anArrayOfObjectArrays.#2.#1"));
        }
        if (failed) {
            Assert.fail();
        }
    }

}

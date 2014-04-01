package com.github.obourgain.matcher.reflection.custom;

import com.github.obourgain.matcher.reflection.AbstractTest;
import com.github.obourgain.matcher.reflection.data.TheObject;
import com.github.obourgain.matcher.reflection.path.PathStack;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author olivier bourgain
 */
public class CollectionComparisonTest extends AbstractTest {

    private CollectionComparison<List<TheObject>,TheObject> comparison;

    @Before
    public void setUp() {
        comparison = new CollectionComparison<>();
    }

    @Test
    public void doCompareEmptyCollection() throws Exception {
        List<TheObject> list1 = new ArrayList<>();
        List<TheObject> list2 = new ArrayList<>();

        comparison.setAssertions(assertions);
        comparison.compare(list1, list2);
    }

    @Test
    public void doCompareSameCollection() throws Exception {
        List<TheObject> list1 = new ArrayList<>();

        comparison.setAssertions(assertions);
        comparison.compare(list1, list1);
    }

    @Test
    public void doCompareCollection() throws Exception {
        List<TheObject> list1 = new ArrayList<>();
        list1.add(new TheObject());

        List<TheObject> list2 = new ArrayList<>();
        list2.add(new TheObject());

        comparison.setAssertions(assertions);
        comparison.setPathStack(new PathStack());
        comparison.compare(list1, list2);
    }
}

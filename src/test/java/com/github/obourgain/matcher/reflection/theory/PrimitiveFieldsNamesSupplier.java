package com.github.obourgain.matcher.reflection.theory;

import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

import java.util.ArrayList;
import java.util.List;

public class PrimitiveFieldsNamesSupplier extends ParameterSupplier {

    @Override
    public List<PotentialAssignment> getValueSources(ParameterSignature sig) {
        PrimitiveFieldsNames annotation = sig.getAnnotation(PrimitiveFieldsNames.class);

        int fieldNameLength = annotation.fieldNames().length;
        int classesLength = annotation.classes().length;
        if (fieldNameLength != classesLength) {
            throw new IllegalArgumentException("fieldNames and classes parameters must have the same length");
        }

        ArrayList<PotentialAssignment> list = new ArrayList<>();
        for (int i = 0; i < annotation.fieldNames().length; i++) {
            String fieldName = annotation.fieldNames()[i];
            Class<?> clazz = annotation.classes()[i];
            list.add(PotentialAssignment.forValue(fieldName + " / " + clazz, new Assignment(fieldName, clazz)));
        }
        return list;
    }

}

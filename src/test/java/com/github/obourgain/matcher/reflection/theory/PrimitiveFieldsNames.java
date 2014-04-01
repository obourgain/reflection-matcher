package com.github.obourgain.matcher.reflection.theory;

import org.junit.experimental.theories.ParametersSuppliedBy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author olivier bourgain
 */
@Retention(RetentionPolicy.RUNTIME)
@ParametersSuppliedBy(PrimitiveFieldsNamesSupplier.class)
public @interface PrimitiveFieldsNames {

    String[] fieldNames();
    Class<?>[] classes();
}


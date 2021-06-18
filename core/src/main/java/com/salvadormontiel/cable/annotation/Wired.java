package com.salvadormontiel.cable.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Target({ FIELD, METHOD })
@Retention(CLASS)
public @interface Wired {

	//Object defaultValue() default "new NullObject()";

	boolean readOnly() default false;

	//Object type();
}

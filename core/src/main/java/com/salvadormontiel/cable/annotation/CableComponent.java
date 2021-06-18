package com.salvadormontiel.cable.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * An annotation that indicates a class is a component. Requires the component name to be specified.
 */
@Target(TYPE)
@Retention(CLASS)
public @interface CableComponent {

	/**
	 * Returns the component name.
	 *
	 * @return The component name.
	 */
	String value() default "";
}

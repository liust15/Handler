package com.liust.handler.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author liust
 */
@Retention(value = RUNTIME)
@Target(value = {TYPE})
@Documented
public @interface Order {

  int value() default Integer.MAX_VALUE;
}

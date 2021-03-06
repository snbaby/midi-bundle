package com.seadun.midi.equipment.annotation;

import com.github.drinkjava2.jbeanbox.annotation.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@AOP
public @interface TX {
	public Class<?> value() default TxBox.class;
}

package com.sapient.tdi.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface CodeReview {
	String[] authors();
	String changeDate();
	String reviewDate() default "Jan 01, 1970";
	Status status() default Status.NOT_REVIEWED;
}

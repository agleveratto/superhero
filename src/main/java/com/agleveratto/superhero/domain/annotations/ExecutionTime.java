package com.agleveratto.superhero.domain.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Target annotations tells us where our annotations will be applicable.
 * Here we are using ElementType.Method, which means it will only work on methods.
 * If we tried to use the annotations anywhere else, then our code would fail to compile.
 * This behavior makes sense, as our annotations will be used for logging method execution time.
 * <p>
 * And @Retention just states whether the annotations will be available to the JVM at runtime or not.
 * By default, it is not, so Spring AOP would not be able to see the annotations.
 * This is why it's been reconfigured.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecutionTime {
}

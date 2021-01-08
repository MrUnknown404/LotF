package main.java.lotf.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Use the getter provided instead
 */
@Target(ElementType.FIELD)
public @interface UseGetter {
	String[] value();
}

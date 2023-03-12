package sd.ufpi.core.rest.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParam {
    String name() default "";

    boolean isRequired() default false;

    String erroMessage() default "Value is required";
}

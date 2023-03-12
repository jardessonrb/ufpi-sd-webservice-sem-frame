package sd.ufpi.core.rest.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RequestMapping(methods = RequestMethod.PUT)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PutMapping {
    String message() default "Path already exists";

    String path() default "";
}

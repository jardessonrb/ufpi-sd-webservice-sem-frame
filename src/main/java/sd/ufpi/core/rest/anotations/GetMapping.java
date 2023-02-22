package sd.ufpi.core.rest.anotations;

@RequestMapping(methods = RequestMethod.GET)
public @interface GetMapping {
    String message() default "Path already exists";

    String path() default "";
}

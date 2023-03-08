package sd.ufpi.core.rest.tipos;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ResponseEntity<T> {
    private int status;
    private T body;

    public void created(T body){
        this.status = 201;
        this.body = body;
    }


    private String convert(T body) throws IllegalArgumentException, IllegalAccessException{
        Class<?> classe = body.getClass();
        Map<String, String> json = new HashMap<>();
        for(Field field : classe.getFields()){
            field.setAccessible(true);
            json.put(field.getName(), (String)field.get(body));
        }

        // String s = json.entrySet().stream().map((Function<? super Entry<String, String>, ? extends T>) (valor) -> {
        //     " \\" valor.getKey() \":\"valor.getValue()\""

        // }).collect(Collectors.joining(",\\n"));


        return null;
    }

    
}

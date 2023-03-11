package sd.ufpi.core.rest;

import java.lang.reflect.Parameter;

import sd.ufpi.core.rest.anotations.PathParam;
import sd.ufpi.core.rest.types.Request;

public class MapperParametersManager {

    public Object[] parse(Request request, Parameter parameters[]){
        Object parametersMapped[] = new Object[parameters.length];
        System.out.println("Quantos elementos na fila de paths: "+request.getPathParams().size());
        for (int i = 0; i < parameters.length; i++) {
            PathParam pathParam = parameters[i].getAnnotation(PathParam.class);
            if(pathParam != null){
               parametersMapped[i] = request.getPathParams().poll();
               continue;
            }
        }

        return parametersMapped;
    }
}

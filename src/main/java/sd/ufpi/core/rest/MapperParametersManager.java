package sd.ufpi.core.rest;

import java.lang.reflect.Parameter;

import com.google.gson.Gson;

import sd.ufpi.core.rest.anotations.PathParam;
import sd.ufpi.core.rest.anotations.QueryParam;
import sd.ufpi.core.rest.exceptions.UnparsedValueForTargetType;
import sd.ufpi.core.rest.exceptions.ValueIsRequiredInAnotation;
import sd.ufpi.core.rest.types.Request;

public class MapperParametersManager {
    private Gson jsonParseManager;

    public MapperParametersManager(){
        this.jsonParseManager = new Gson();
    }

    public Object[] parse(Request request, Parameter parameters[]) throws UnparsedValueForTargetType, ClassNotFoundException, ValueIsRequiredInAnotation{
        Object parametersMapped[] = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            PathParam pathParam = parameters[i].getAnnotation(PathParam.class);
            if(pathParam != null){
                String value = request.getPathParams().get(pathParam.name());
                if(value == null){
                    throw new ValueIsRequiredInAnotation(pathParam.erroMessage());
                }
                parametersMapped[i] = parseTypeObject(request.getPathParams().get(pathParam.name()), parameters[i]);
                continue;
            }

            QueryParam queryParam = parameters[i].getAnnotation(QueryParam.class);
            if(queryParam != null){
                String key = queryParam.name();
                String value = request.getQueryParams().get(key);

                if(value == null){
                    if(queryParam.isRequired()){
                        throw new ValueIsRequiredInAnotation(queryParam.erroMessage());
                    }else {
                        parametersMapped[i] = value;
                        continue;
                    }
                }else {
                    parametersMapped[i] = parseTypeObject(request.getQueryParams().get(queryParam.name()), parameters[i]);
                    continue;
                }
            }
        }

        return parametersMapped;
    }

    private Object parseTypeObject(String value, Parameter parameter) throws UnparsedValueForTargetType, ClassNotFoundException{
        try {
            Class t = Class.forName(parameter.getAnnotatedType().toString());
            Object valueParameter = jsonParseManager.fromJson(value, t);
            return valueParameter;
        } catch (Exception e) {
            throw new UnparsedValueForTargetType("Value "+value+" not valid for type "+parameter.getType().getName());
        }
    }
}

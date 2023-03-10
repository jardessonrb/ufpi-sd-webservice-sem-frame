package sd.ufpi.core.rest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sd.ufpi.core.exceptions.AttributeNotFoundException;
import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.RequestMethod;
import sd.ufpi.core.rest.exceptions.ClassControllerAlreadyExists;
import sd.ufpi.core.rest.types.Request;

public class RequestResolver {
    private Map<String, Object> controllers;
    private HttpParse parse;

    public RequestResolver(){
        this.controllers = new HashMap<>();
        this.parse       = new HttpParse();
    }

    public void addController(String pathMapping, Object controller) throws ClassControllerAlreadyExists{
        if(this.controllers.containsKey(pathMapping)){
            throw new ClassControllerAlreadyExists("Já existe um controller com o path "+pathMapping);
        }

        this.controllers.put(pathMapping, controller);
    }

    public Object resolver(Request request) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        List<String> paths = request.getParamsPath();

        Object controller = getController(paths.get(0));

        if(request.getRequestMethod().equals(RequestMethod.GET)){
            Object result = executeGET(request, controller);
            return result;
        }
        
        return "Deu bom";
    }

    private Object getController(String path){
        if(!this.controllers.containsKey(path)){
            throw new AttributeNotFoundException("Controller is not contain path");
        }

        return this.controllers.get(path);

        // Class<?> classe = controller.getClass();
        // for(Method method : classe.getDeclaredMethods()){
        //     GetMapping getMapping = method.getAnnotation(GetMapping.class);
        //     System.out.println("GetMapping Value:" +getMapping.path());
        // }
        

        // return null;
    }

    private Object executeGET(Request request, Object controller) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Class<?> controllerClass = controller.getClass();
        for(Method method : controllerClass.getMethods()){
            GetMapping get = method.getAnnotation(GetMapping.class);
            if(get != null){
                List<PathParams> pathParams = this.parse.tranformUriInPathParams(get.path());
                if(isCorrespondingRequest(request.getParamsPath(), pathParams)){
                    Object resultado = (Object)method.invoke(controller , null);

                    return resultado;

                }
            }
        }

        return null;
    }

    private boolean isCorrespondingRequest(List<String> paths, List<PathParams> pathParams){
        if((pathParams.size() + 1) != paths.size()){
            return false;
        }
        for (int i = 0; i < pathParams.size(); i++) {
            if(i < paths.size()){
                if(!pathParams.get(i).isParams()){
                    System.out.println(pathParams.get(i).getValue()+" = "+paths.get(i + 1 ));
                    if(!pathParams.get(i).getValue().equals(paths.get(i + 1))){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private List<String> getPathsMethod(String path){
        List<String> paths = new ArrayList<>();
        String pathsMethod[] = path.split("/");
        for (int i = 0; i < pathsMethod.length; i++) {
            if(!pathsMethod[i].equals("")){
                paths.add(pathsMethod[i]);
            }
        }

        return paths;
    }
}

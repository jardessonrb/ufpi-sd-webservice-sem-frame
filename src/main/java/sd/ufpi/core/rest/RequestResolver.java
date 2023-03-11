package sd.ufpi.core.rest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sd.ufpi.core.exceptions.AttributeNotFoundException;
import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.PathParam;
import sd.ufpi.core.rest.anotations.RequestMethod;
import sd.ufpi.core.rest.exceptions.ClassControllerAlreadyExists;
import sd.ufpi.core.rest.exceptions.UnparsedValueForTargetType;
import sd.ufpi.core.rest.exceptions.ValueIsRequiredInAnotation;
import sd.ufpi.core.rest.types.Request;

public class RequestResolver {
    private Map<String, Object> controllers;
    private HttpParse parse;
    private MapperParametersManager mapperParametersManager;

    public RequestResolver(){
        this.controllers = new HashMap<>();
        this.parse       = new HttpParse();
        this.mapperParametersManager = new MapperParametersManager();
    }

    public void addController(String pathMapping, Object controller) throws ClassControllerAlreadyExists{
        if(this.controllers.containsKey(pathMapping)){
            throw new ClassControllerAlreadyExists("Já existe um controller com o path "+pathMapping);
        }

        this.controllers.put(pathMapping, controller);
    }

    public Object resolver(Request request) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnparsedValueForTargetType, ClassNotFoundException, ValueIsRequiredInAnotation{
        List<String> paths = request.getPaths();

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

    private Object executeGET(Request request, Object controller) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnparsedValueForTargetType, ClassNotFoundException, ValueIsRequiredInAnotation{
        Class<?> controllerClass = controller.getClass();

        for(Method method : controllerClass.getMethods()){
            GetMapping get = method.getAnnotation(GetMapping.class);
            if(get != null){
                List<PathParams> pathParams = this.parse.tranformUriInPathParams(get.path());
                if(isCorrespondingRequest(request, pathParams)){
                    method.setAccessible(true);
                    Parameter parameters[] = method.getParameters();
                    
                    Object parametrosMapeado[] = this.mapperParametersManager.parse(request, parameters);

                    /*
                     * method.getParameters() retorna todos os parametros, tem que caçar uma forma de mapear para cada um dele e passar em ordem
                     */
                    
                    Object resultado = (Object)method.invoke(controller , parametrosMapeado);

                    return resultado;

                }
            }
        }

        return null;
    }

    private boolean isCorrespondingRequest(Request request, List<PathParams> pathParams){
        request.getPathParams().clear();
        if((pathParams.size() + 1) != request.getPaths().size()){
            return false;
        }
        for (int i = 0; i < pathParams.size(); i++) {
            if(i < request.getPaths().size()){
                if(!pathParams.get(i).isParams()){
                    if(!pathParams.get(i).getValue().equals(request.getPaths().get(i + 1))){
                        return false;
                    }
                }else {
                    request.getPathParams().put(pathParams.get(i).getKey(), request.getPaths().get(i + 1 ));
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

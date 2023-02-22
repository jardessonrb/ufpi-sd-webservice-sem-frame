package sd.ufpi.core.rest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import sd.ufpi.core.exceptions.AttributeNotFoundException;
import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.RequestMapping;

public class Root implements HttpHandler {
    private Map<String, RootController> controllers;

    public Root(){
        this.controllers = new HashMap<String,RootController>();
    }

    public void handle(HttpExchange exchange) throws IOException {
        
        String path = splitPath(exchange.getRequestURI());
        
        Object result = getResultExecute(path);
    
        exchange.sendResponseHeaders(200, result.toString().length());
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseBody().write(result.toString().getBytes("UTF-8"));
    }

    public void addController(RootController controller){

        Class<?> classe = controller.getClass();

        RequestMapping request = classe.getAnnotation(RequestMapping.class);
        String path = null;
        if(request != null){
            path = request.path();
            path = path.split("/")[1];
        }

        if(path == null){
            throw new AttributeNotFoundException("Controller is not contain path");
        }

        if(this.controllers.containsKey(path)){
            throw new AttributeNotFoundException("Path alread exists");
        }


        this.controllers.put(path, controller);
    }
    
    private String splitPath(URI uriRequest){
        String path[] = uriRequest.toString().split("/");

        for(int i = 0; i < path.length; i++){
            System.out.println(i + " = "+path[i]);
        }
        System.out.println("Path: "+path[1]+" si "+path.length);
        return path[1];
    }

    private Object getResultExecute(String path){
        if(!this.controllers.containsKey(path)){
            throw new AttributeNotFoundException("Controller is not contain path");
        }

        RootController controller = this.controllers.get(path);
        return controller.execute();
    }

    private RootController getController(String path){
        RootController controller = this.controllers.get(path);

        Class<?> classe = controller.getClass();
        for(Method method : classe.getDeclaredMethods()){
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            System.out.println("GetMapping Value:" +getMapping.path());
        }
        

        return null;
    }
}

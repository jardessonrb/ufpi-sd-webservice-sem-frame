package sd.ufpi.core.rest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
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
        System.out.println("Chegou");
        HttpParse parse = new HttpParse();
        Request request = parse.parse(exchange);
        Gson gson = new Gson();
        System.out.println(gson.toJson(request));

        System.out.println(exchange.getRequestURI());
        System.out.println(exchange.getRequestMethod());

        
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

        Class<?> controllerClass = this.controllers.get(path).getClass();

        String message = "message";
        for(Method method : controllerClass.getMethods()){
            GetMapping get = method.getAnnotation(GetMapping.class);
            if(get != null){
                if(message.equals(get.path().split("/")[1])){
                    try {
                        String valor = (String)method.invoke(this.controllers.get(path) , null);

                        return "{\"valor\":\""+valor+"\"}";
                        
                    } catch (Exception e) {
                        System.out.println("Deu erro");
                    }
                }
            }
        }

        return null;
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

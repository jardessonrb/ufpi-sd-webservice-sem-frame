package sd.ufpi.core.rest;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import sd.ufpi.core.exceptions.AtributteNotFoundException;

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
        if(controller.getPath() == null){
            throw new AtributteNotFoundException("Controller is not contain path");
        }

        if(this.controllers.containsKey(controller.getPath())){
            throw new AtributteNotFoundException("Path alread exists");
        }

        this.controllers.put(controller.getPath(), controller);
    }
    
    private String splitPath(URI uriRequest){
        String path[] = uriRequest.toString().split("/");

        System.out.println("Path: "+path[1]+" si "+path.length);
        return path[1];
    }

    private Object getResultExecute(String path){
        if(!this.controllers.containsKey(path)){
            throw new AtributteNotFoundException("Controller is not contain path");
        }

        RootController controller = this.controllers.get(path);
        return controller.execute();
    }
}

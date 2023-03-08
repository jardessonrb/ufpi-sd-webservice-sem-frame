package sd.ufpi.core.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sd.ufpi.core.rest.anotations.RequestMethod;

class Request {
    private List<String> paths;
    private Map<String, String> paramsQuery;
    private List<String> paramsPath;
    private Object body;
    private RequestMethod method;

    public Request(){
        this.paths = new ArrayList<>();
        this.paramsQuery = new HashMap<>();
        this.paramsPath = new ArrayList<>();
    }

    public void setMethod(String method){
        this.method = RequestMethod.valueOf(method);
    }

    public void setBody(Object body){
        this.body = body;
    }

    public void setPaths(List<String> paths){
        this.paramsPath = paths;
    }

    public void setQueryParams(List<String> paths){
        Map<String, String> query = new HashMap<>();
        paths.forEach(path -> { 
            String params[] = getQueryParams(path);
            for (int i = 0; i < params.length; i++) {
                String chaveValor[] = params[i].split("=");
                if(chaveValor.length > 1){
                    query.put(chaveValor[0], chaveValor[1]);

                }
            }
        });

        this.paramsQuery = query;
    }

    public List<String> getPaths(){
        return this.paramsPath;
    }

    private String[] getQueryParams(String path){
        String query[] = path.split("&");

        return query;
    }
}
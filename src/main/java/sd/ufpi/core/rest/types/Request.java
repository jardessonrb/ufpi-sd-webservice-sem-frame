package sd.ufpi.core.rest.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sd.ufpi.core.rest.anotations.RequestMethod;

public class Request {
    private Map<String, String> paramsQuery;
    private List<String> paramsPath;
    private Object body;
    private RequestMethod method;

    public Request(){
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

    public void setQueryParams(Map<String, String> queries){
        this.paramsQuery = queries;
    }

    public List<String> getParamsPath(){
        return this.paramsPath;
    }

    public RequestMethod getRequestMethod(){
        return this.method;
    }
}
package sd.ufpi.core.rest.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import sd.ufpi.core.rest.anotations.RequestMethod;

public class Request {
    private Map<String, String> paramsQuery;
    private List<String> paths;
    private Object body;
    private RequestMethod method;
    private Map<String, String> paramsPath;

    public Request(){
        this.paramsQuery = new HashMap<>();
        this.paths = new ArrayList<String>();
        this.paramsPath = new HashMap<>();
    }

    public void setMethod(String method){
        this.method = RequestMethod.valueOf(method);
    }

    public void setBody(Object body){
        this.body = body;
    }

    public void setPaths(List<String> paths){
        this.paths = paths;
    }

    public void setQueryParams(Map<String, String> queries){
        this.paramsQuery = queries;
    }

    public List<String> getPaths(){
        return this.paths;
    }

    public RequestMethod getRequestMethod(){
        return this.method;
    } 

    public void setPathParams(Map<String, String> params){
        this.paramsPath = params;
    }

    public Map<String, String> getPathParams(){
        return this.paramsPath;
    }

    public Map<String, String> getQueryParams(){
        return this.paramsQuery;
    }
}
package sd.ufpi.core.rest;

public class PathParams {
    private boolean isParam;
    private String value;
    private String key;

    public PathParams(boolean isParam, String value, String key){
        this.isParam = isParam;
        this.value   = value;
        this.key = key;
    }


    public boolean isParams(){
        return this.isParam;
    }

    public String getValue(){
        return this.value;
    }

    public String getKey(){
        return this.key;
    }
}

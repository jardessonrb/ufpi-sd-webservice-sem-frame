package sd.ufpi.core.rest;

public class PathParams {
    private boolean isParam;
    private String value;

    public PathParams(boolean isParam, String value){
        this.isParam = isParam;
        this.value   = value;
    }


    public boolean isParams(){
        return this.isParam;
    }

    public String getValue(){
        return this.value;
    }
}

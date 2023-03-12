package sd.ufpi.core.rest.types;

public class ResponseEntity<T> {
    private int status;
    private T body;

    public ResponseEntity(){}

    public ResponseEntity<T> created(T body){
        this.body = body;
        this.status = 201;

        return this;
    }

    public ResponseEntity<T> erro(T body, int status){
        this.body = body;
        this.status = status;

        return this;
    }

    public int getStatus(){
        return this.status;
    }
}

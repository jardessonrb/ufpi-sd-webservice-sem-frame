package sd.ufpi.application.controllers;

import sd.ufpi.core.rest.RootController;
import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.RequestMapping;

@RequestMapping(path = "/message")
public class MessageController implements RootController{

    public Object execute() {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        builder.append("\"message\":\"Mensagem de usuario para usuario\"");
        builder.append("}");

        return builder.toString();
    }

    @GetMapping(path = "/message")
    public String getMessage(){
        return "Deu bom o mapeamento";
    }
}

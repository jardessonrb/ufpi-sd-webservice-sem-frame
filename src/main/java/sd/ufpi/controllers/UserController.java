package sd.ufpi.controllers;

import sd.ufpi.core.rest.RootController;
import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.RequestMapping;

@RequestMapping(path = "/user")
public class UserController implements RootController {

    public Object execute() {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        builder.append("\"user\":\"Jardesson Ribeiro\"");
        builder.append("}");

        return builder.toString();
    }

    @GetMapping(path = "/name")
    public String buscarNome(){
        return "Járdesson Ribeiro";
    }
    
}

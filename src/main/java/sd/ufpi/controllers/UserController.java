package sd.ufpi.controllers;

import sd.ufpi.core.rest.RootController;

public class UserController implements RootController {
    private String path = "user";

    public String getPath() {
        return this.path;
    }

    public Object execute() {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        builder.append("\"user\":\"Jardesson Ribeiro\"");
        builder.append("}");

        return builder.toString();
    }
    
}

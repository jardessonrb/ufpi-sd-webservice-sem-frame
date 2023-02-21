package sd.ufpi.controllers;

import sd.ufpi.core.rest.RootController;

public class MessageController implements RootController{
    private String path = "message";

    public String getPath() {
        return this.path;
    }

    public Object execute() {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        builder.append("\"message\":\"Mensagem de usuario para usuario\"");
        builder.append("}");

        return builder.toString();
    }
}

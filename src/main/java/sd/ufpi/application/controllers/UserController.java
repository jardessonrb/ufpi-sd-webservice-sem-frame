package sd.ufpi.application.controllers;

import sd.ufpi.core.rest.RootController;
import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.RequestMapping;

@RequestMapping(path = "/user")
public class UserController implements RootController {

    @GetMapping(path = "/name")
    public Object execute() {
        return "Járdesson Ribeiro";
    }

    @GetMapping(path = "/name/{ehumpathparam}/sobrenome")
    public Object testeDeQuery() {
        return "Járdesson Ribeiro Santos";
    }
    
}

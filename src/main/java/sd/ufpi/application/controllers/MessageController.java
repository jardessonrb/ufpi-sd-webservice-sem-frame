package sd.ufpi.application.controllers;

import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.RequestMapping;

@RequestMapping(path = "/message")
public class MessageController {

    @GetMapping(path = "/uuid/new")
    public Object testeDeMensagem() {
        return new Teste();
    }

    @GetMapping(path = "/message")
    public String getMessage(){
        return "Deu bom o mapeamento";
    }
}

class Teste {
    public String name;
    
    public Teste(){
        this.name = "Járdesson Ribeiro";
    }
}

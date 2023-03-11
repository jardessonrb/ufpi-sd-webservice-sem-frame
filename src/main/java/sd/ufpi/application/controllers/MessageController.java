package sd.ufpi.application.controllers;

import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.PathParam;
import sd.ufpi.core.rest.anotations.RequestMapping;

@RequestMapping(path = "/message")
public class MessageController {

    @GetMapping(path = "/nome/{nome}/idade/{idade}")
    public Object testeDeMensagem(@PathParam(name = "nome") String valor, @PathParam(name = "idade") String idade) {
        return new Teste(valor, idade);
    }

    @GetMapping(path = "/message/{nome}")
    public String getMessage(@PathParam String nome){
        return "Eu quero ver o nome passado "+nome;
    }
}

class Teste {
    public String name;
    
    public Teste(String nome, String idade){
        this.name = "Meu nome é "+nome+" e tenho "+idade+" anos";
    }
}

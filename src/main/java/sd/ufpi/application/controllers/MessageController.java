package sd.ufpi.application.controllers;

import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.PathParam;
import sd.ufpi.core.rest.anotations.RequestMapping;

@RequestMapping(path = "/message")
public class MessageController {

    @GetMapping(path = "/nome/{nome}/idade/{idade}")
    public Object testeDeMensagem(@PathParam(name = "idade") Integer idade, @PathParam(name = "nome") String nome) {
        return new Teste(nome, idade);
    }

    @GetMapping(path = "/message/{nome}")
    public String getMessage(@PathParam(name = "nome") String nome){
        return "Eu quero ver o nome passado "+nome;
    }
}

class Teste {
    public String name;
    
    public Teste(String nome, Integer idade){
        this.name = "Meu nome é "+nome+" e tenho "+idade+" anos";
    }
}

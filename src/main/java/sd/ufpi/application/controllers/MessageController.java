package sd.ufpi.application.controllers;

import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.PathParam;
import sd.ufpi.core.rest.anotations.QueryParam;
import sd.ufpi.core.rest.anotations.RequestMapping;

@RequestMapping(path = "/message")
public class MessageController {

    @GetMapping(path = "/nome/{nome}/idade/{idade}")
    public Object testeDeMensagem(@PathParam(name = "idade") Integer idade, @PathParam(name = "nome") String nome) {
        return new Teste(nome, idade);
    }

    @GetMapping(path = "/{nome}/teste")
    public String getMessage(
            @PathParam(name = "nome") String nome, 
            @QueryParam(name = "page") Integer page,  
            @QueryParam(name = "limit") Integer limit){

        return "Eu quero ver o nome passado "+nome+" com uma pagina de "+page+" limit "+limit;
    }
}

class Teste {
    public String name;
    
    public Teste(String nome, Integer idade){
        this.name = "Meu nome é "+nome+" e tenho "+idade+" anos";
    }
}

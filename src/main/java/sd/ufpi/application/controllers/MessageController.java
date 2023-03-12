package sd.ufpi.application.controllers;

import sd.ufpi.application.domain.dto.MensagemDTO;
import sd.ufpi.application.domain.form.MensagemForm;
import sd.ufpi.application.domain.form.MensagemReencaminhadaForm;
import sd.ufpi.core.rest.anotations.DeleteMapping;
import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.PatchMapping;
import sd.ufpi.core.rest.anotations.PathParam;
import sd.ufpi.core.rest.anotations.PostMapping;
import sd.ufpi.core.rest.anotations.PutMapping;
import sd.ufpi.core.rest.anotations.QueryParam;
import sd.ufpi.core.rest.anotations.RequestBody;
import sd.ufpi.core.rest.anotations.RequestMapping;
import sd.ufpi.core.rest.anotations.RequestMethod;
import sd.ufpi.core.rest.types.ResponseEntity;

@RequestMapping(path = "/message")
public class MessageController {

    @GetMapping(path = "/nome/{nome}/idade/{idade}")
    public Object testeDeMensagem(@PathParam(name = "idade") Integer idade, @PathParam(name = "nome") String nome) {
        return new Teste(nome, idade);
    }

    @GetMapping(path = "/{nome}/teste")
    public ResponseEntity<MensagemDTO> getMessage(
            @PathParam(name = "nome") String nome, 
            @QueryParam(name = "page") Integer page,  
            @QueryParam(name = "limit") Integer limit,
            @QueryParam(name = "method") RequestMethod method,
            @RequestBody MensagemForm form){
        
        System.out.println(form.toString());
        System.out.println(method);
        String mensagem = "Eu quero ver o nome passado "+nome+" com uma pagina de "+page+" limit "+limit;

        MensagemDTO dto = new MensagemDTO();
        dto.setMensagem(mensagem);
        ResponseEntity<MensagemDTO> rs = new ResponseEntity<>();
        
        return rs.created(dto);
    }


    @PostMapping
    public ResponseEntity<MensagemDTO> createMensagem(@RequestBody MensagemForm form){
        MensagemDTO mensagemDTO = new MensagemDTO();
        mensagemDTO.setMensagem("Salvou a mensagem");
        ResponseEntity<MensagemDTO> rs = new ResponseEntity<MensagemDTO>().created(mensagemDTO);
        return rs;
    }

    @PutMapping(path = "/emissor/{idEmissor}/mensagem/{idMensagem}")
    public ResponseEntity<MensagemDTO> reecaminharMensagem(
        @RequestBody MensagemReencaminhadaForm form,
        @PathParam(name = "idEmissor") Long idEmissor,
        @PathParam(name = "idMensagem") Long idMensagem){

        MensagemDTO mensagemDTO = new MensagemDTO();
        String msg = "Mensagem "+idMensagem+" foi enviada de "+idEmissor+" para ";
        for (int i = 0; i < form.getReceptores().size() ; i++) {
            msg += form.getReceptores().get(i)+", ";
        }

        mensagemDTO.setMensagem(msg);
        ResponseEntity<MensagemDTO> rs = new ResponseEntity<MensagemDTO>().created(mensagemDTO);
        return rs;
    }

    @DeleteMapping(path = "/{idMensagem}")
    public ResponseEntity<MensagemDTO> reecaminharMensagem(
        @PathParam(name = "idMensagem") Long idMensagem){

        MensagemDTO mensagemDTO = new MensagemDTO();
        String msg = "A mensagem "+idMensagem+" foi deletada.";
        
        mensagemDTO.setMensagem(msg);
        ResponseEntity<MensagemDTO> rs = new ResponseEntity<MensagemDTO>().created(mensagemDTO);
        return rs;
    }

    @PatchMapping(path = "/{idMensagem}")
    public ResponseEntity<MensagemDTO> atualizarPatch(
        @PathParam(name = "idMensagem") Long idMensagem){

        MensagemDTO mensagemDTO = new MensagemDTO();
        String msg = "A mensagem "+idMensagem+" foi atualizada patch.";
        
        mensagemDTO.setMensagem(msg);
        ResponseEntity<MensagemDTO> rs = new ResponseEntity<MensagemDTO>().created(mensagemDTO);
        return rs;
    }
}

class Teste {
    public String name;
    
    public Teste(String nome, Integer idade){
        this.name = "Meu nome é "+nome+" e tenho "+idade+" anos";
    }
}

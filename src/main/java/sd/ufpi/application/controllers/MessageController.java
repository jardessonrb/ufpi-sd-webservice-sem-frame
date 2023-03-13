package sd.ufpi.application.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import sd.ufpi.application.domain.dto.MensagemCompletaDTO;
import sd.ufpi.application.domain.dto.MensagemDTO;
import sd.ufpi.application.domain.dto.Sucesso;
import sd.ufpi.application.domain.dto.UsuarioDTO;
import sd.ufpi.application.domain.enums.SituacaoMensagem;
import sd.ufpi.application.domain.form.EncaminharMensagemForm;
import sd.ufpi.application.domain.form.MensagemForm;
import sd.ufpi.application.domain.form.MensagemReencaminhadaForm;
import sd.ufpi.application.service.MensagemService;
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

    private MensagemService mensagemService;

    public MessageController() throws SQLException, IOException {
        this.mensagemService = new MensagemService();
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> enviarMensagem(@RequestBody MensagemForm form) throws SQLException, IOException{
        try {
            MensagemDTO dto = this.mensagemService.enviarMensagem(form);
            System.out.println("Salvou: "+form.getAssunto());
            return new ResponseEntity<MensagemDTO>().created(dto);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping(path = "/encaminhar")
    public ResponseEntity<MensagemDTO> encaminharMensagem(@RequestBody EncaminharMensagemForm form) throws SQLException, IOException{
        try {
            MensagemDTO dto = this.mensagemService.encaminhar(form);
            System.out.println("Encaminhou: "+form.getIdMensagem());
            return new ResponseEntity<MensagemDTO>().created(dto);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/{idMensagem}")
    public ResponseEntity<MensagemCompletaDTO> verMensagem(@PathParam(name = "idMensagem") Long idMensagem) throws SQLException, IOException{
        try {
            MensagemCompletaDTO dto = this.mensagemService.verMensagem(idMensagem);
            System.out.println("Encaminhou: "+idMensagem);
            return new ResponseEntity<MensagemCompletaDTO>().created(dto);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(path = "/{idMensagem}/responder")
    public ResponseEntity<MensagemDTO> responderMensagem(@PathParam(name = "idMensagem") Long idMensagem, @RequestBody MensagemForm form) throws SQLException, IOException{
        try {
            System.out.println(idMensagem);
            MensagemDTO dto = this.mensagemService.responderMensagem(form, idMensagem);
            System.out.println("Salvou: "+form.getAssunto());
            return new ResponseEntity<MensagemDTO>().created(dto);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/{idUsuario}")
    public ResponseEntity<List<MensagemDTO>> listar(@PathParam(name = "idUsuario") Long idUsuario, 
                                                        @QueryParam(name = "situacao", isRequired = true) SituacaoMensagem situacao) throws SQLException, IOException{
        try {
            List<MensagemDTO> dtos = this.mensagemService.listarMensagem(idUsuario, situacao);
            System.out.println("Encaminhou: "+idUsuario);
            return new ResponseEntity<List<MensagemDTO>>().created(dtos);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping(path = "/{idMensagem}")
    public ResponseEntity<Sucesso> excluir(@PathParam(name = "idMensagem") Long idMensagem) throws SQLException, IOException{
        try {
            Boolean res = this.mensagemService.excluir(idMensagem);
            Sucesso s = new Sucesso();
            s.setResposta("Mensagem Apagada");
            return new ResponseEntity<Sucesso>().created(s);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}

package sd.ufpi.application.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import sd.ufpi.application.domain.dto.UsuarioDTO;
import sd.ufpi.application.domain.form.UsuarioForm;
import sd.ufpi.application.service.UsuarioService;
import sd.ufpi.core.rest.anotations.GetMapping;
import sd.ufpi.core.rest.anotations.PostMapping;
import sd.ufpi.core.rest.anotations.QueryParam;
import sd.ufpi.core.rest.anotations.RequestBody;
import sd.ufpi.core.rest.anotations.RequestMapping;
import sd.ufpi.core.rest.types.ResponseEntity;

@RequestMapping(path = "/user")
public class UserController{

    private UsuarioService usuarioService;

    public UserController() throws SQLException, IOException{
        this.usuarioService = new UsuarioService();
    }
    
    @GetMapping()
    public ResponseEntity<UsuarioDTO> getUsuario(@QueryParam(name = "nome") String nome, @QueryParam(name = "senha") String senha) throws SQLException, IOException{
        UsuarioDTO dto = this.usuarioService.getUsuario(nome, senha);
        return new ResponseEntity<UsuarioDTO>().created(dto);
    }

    @GetMapping(path = "/listagem")
    public ResponseEntity<List<UsuarioDTO>> getListagemUsuario(@QueryParam(name = "nome") String nome) throws SQLException, IOException{
        List<UsuarioDTO> dtos = this.usuarioService.listagemDeUsuario(nome);
        return new ResponseEntity<List<UsuarioDTO>>().created(dtos);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioForm form) throws SQLException, IOException{
        try {
            UsuarioDTO dto = this.usuarioService.criarUsuario(form);
            System.out.println("Salvou: "+form.getNome());
            return new ResponseEntity<UsuarioDTO>().created(dto);
            
        } catch (Exception e) {
            return null;
        }

    }
    
}

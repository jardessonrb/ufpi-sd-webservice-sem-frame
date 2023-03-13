package sd.ufpi.application.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sd.ufpi.application.domain.dto.UsuarioDTO;
import sd.ufpi.application.domain.form.UsuarioForm;
import sd.ufpi.application.domain.model.UsuarioModel;
import sd.ufpi.application.repository.UsuarioRepository;

public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService() throws SQLException, IOException{
        this.usuarioRepository = new UsuarioRepository();
    }

    public UsuarioDTO getUsuario(String nome, String senha) throws SQLException, IOException{
        UsuarioModel usuarioModel = this.usuarioRepository.findByNameAndSenha(nome, senha);

        if(usuarioModel != null){
            UsuarioDTO dto = new UsuarioDTO();
            dto.setNome(usuarioModel.getNome());
            dto.setId(usuarioModel.getId());
    
            return dto;
        }

        return null;
    }

    public UsuarioDTO criarUsuario(UsuarioForm form) throws SQLException, IOException{
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setSenha(form.getSenha());
        usuarioModel.setNome(form.getNome());

        usuarioModel = this.usuarioRepository.save(usuarioModel);

        if(usuarioModel != null){
            UsuarioDTO dto = new UsuarioDTO();
            dto.setNome(usuarioModel.getNome());
            dto.setId(usuarioModel.getId());
    
            return dto;
        }

        return null;

    }


    public List<UsuarioDTO> listagemDeUsuario(String nome) throws SQLException, IOException{
        List<UsuarioModel> usuarios = this.usuarioRepository.findByNameContaingIgnoreCase(nome);
        List<UsuarioDTO> dtos = new ArrayList<>();

        usuarios.forEach(usuario -> {
            UsuarioDTO dto = UsuarioDTO.fromUsuario(usuario);
            dtos.add(dto);
        });

        return dtos;
    }
}

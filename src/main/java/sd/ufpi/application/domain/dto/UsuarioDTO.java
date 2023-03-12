package sd.ufpi.application.domain.dto;

import sd.ufpi.application.domain.model.UsuarioModel;

public class UsuarioDTO {

    public UsuarioDTO(){}
    
    private String nome;
    private Long id;

    public String getNome(){
        return this.nome;
    }

    public Long getId(){
        return this.id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setId(Long id){
        this.id = id;
    }

    public static UsuarioDTO fromUsuario(UsuarioModel usuarioModel){

        if(usuarioModel != null){
            UsuarioDTO dto = new UsuarioDTO();
            dto.setNome(usuarioModel.getNome());
            dto.setId(usuarioModel.getId());
    
            return dto;
        }else {
            return null;
        }
    }
}

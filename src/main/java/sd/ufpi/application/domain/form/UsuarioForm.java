package sd.ufpi.application.domain.form;

public class UsuarioForm {
    public UsuarioForm(){}
    
    private String nome;
    private String senha;

    public String getNome(){
        return this.nome;
    }

    public String getSenha(){
        return this.senha;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }
}

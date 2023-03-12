package sd.ufpi.application.domain.form;

public class MensagemForm {
    private Long idEmissor;
    private Long idReceptor;
    private String assunto;
    private String mensagem;

    public MensagemForm(Long emissor, Long receptor, String assunto, String mensagem){
        this.idEmissor  = emissor;
        this.idReceptor = receptor;
        this.assunto    = assunto;
        this.mensagem   = mensagem;
    }

    public String toString(){
        return idEmissor+" = "+idReceptor+" = "+assunto+"  = "+mensagem;
    }

    public MensagemForm(){}

    public void setEmissor(Long id){
        this.idEmissor = id;
    }
    public void setReceptor(Long id){
        this.idReceptor = id;
    }
    public void setAssunto(String assunto ){
        this.assunto = assunto;
    }
    public void setMensagem(String mensagem){
        this.mensagem = mensagem;
    }

    public Long getEmissor(){
        return this.idEmissor;
    }
    public Long getReceptor(){
        return this.idReceptor;
    }
    public String getAssunto(){
        return this.assunto;
    }
    public String getMensagem(){
        return this.mensagem;
    }
}

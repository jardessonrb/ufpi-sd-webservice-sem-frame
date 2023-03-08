package sd.ufpi.core.forms;

public class MensagemForm {
    private Long idRemetente;
    private Long idReceptor;
    private String assunto;
    private String mensagem;

    public String toString(){
        return idRemetente+" "+idReceptor+" "+assunto+" "+mensagem;
    }
}

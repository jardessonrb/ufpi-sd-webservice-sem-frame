package sd.ufpi.application.domain.form;

import java.util.List;

public class EncaminharMensagemForm {
    private Long idMensagem;
    private Long idUsuario;
    private List<Long> destinatarios;
    
	public Long getIdMensagem() {
		return idMensagem;
	}
	public void setIdMensagem(Long idMensagem) {
		this.idMensagem = idMensagem;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public List<Long> getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(List<Long> destinatarios) {
		this.destinatarios = destinatarios;
	}
    
    
}

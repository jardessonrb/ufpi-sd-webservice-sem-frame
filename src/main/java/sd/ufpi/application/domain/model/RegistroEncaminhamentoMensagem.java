package sd.ufpi.application.domain.model;

public class RegistroEncaminhamentoMensagem {
	private Long id;
	private UsuarioModel remetente;
	private UsuarioModel receptor;
	private MensagemModel mensagemOriginal;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UsuarioModel getRemetente() {
		return remetente;
	}
	public void setRemetente(UsuarioModel remetente) {
		this.remetente = remetente;
	}
	public UsuarioModel getReceptor() {
		return receptor;
	}
	public void setReceptor(UsuarioModel receptor) {
		this.receptor = receptor;
	}
	public MensagemModel getMensagemOriginal() {
		return mensagemOriginal;
	}
	public void setMensagemOriginal(MensagemModel mensagemOriginal) {
		this.mensagemOriginal = mensagemOriginal;
	}
	
}

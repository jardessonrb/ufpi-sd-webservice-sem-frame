package sd.ufpi.application.model;

import java.time.LocalDateTime;
import java.util.List;

import sd.ufpi.application.enums.SituacaoMensagem;
import sd.ufpi.application.enums.TipoMensagem;

public class MensagemModel {
	
	private Long id;
	private String assunto;
	private String corpoMensagem;
	private SituacaoMensagem situacao;
	private LocalDateTime horarioEnvio;
	private TipoMensagem tipo;
	private UsuarioModel rementente;
	private UsuarioModel destinatario;
	private MensagemModel mensagemOriginal;
	private List<MensagemModel> respostas;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getCorpoMensagem() {
		return corpoMensagem;
	}
	public void setCorpoMensagem(String corpoMensagem) {
		this.corpoMensagem = corpoMensagem;
	}
	public SituacaoMensagem getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoMensagem situacao) {
		this.situacao = situacao;
	}
	public LocalDateTime getHorarioEnvio() {
		return horarioEnvio;
	}
	public void setHorarioEnvio(LocalDateTime horarioEnvio) {
		this.horarioEnvio = horarioEnvio;
	}
	public TipoMensagem getTipo() {
		return tipo;
	}
	public void setTipo(TipoMensagem tipo) {
		this.tipo = tipo;
	}
	public UsuarioModel getRementente() {
		return rementente;
	}
	public void setRementente(UsuarioModel rementente) {
		this.rementente = rementente;
	}
	public UsuarioModel getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(UsuarioModel destinatario) {
		this.destinatario = destinatario;
	}
	public MensagemModel getMensagemOriginal() {
		return mensagemOriginal;
	}
	public void setMensagemOriginal(MensagemModel mensagemOriginal) {
		this.mensagemOriginal = mensagemOriginal;
	}
	public List<MensagemModel> getRespostas() {
		return respostas;
	}
	public void setRespostas(List<MensagemModel> respostas) {
		this.respostas = respostas;
	}
	
	
}

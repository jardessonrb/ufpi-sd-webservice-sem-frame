package sd.ufpi.application.domain.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sd.ufpi.application.domain.enums.SituacaoMensagem;
import sd.ufpi.application.domain.enums.TipoMensagem;
import sd.ufpi.application.domain.model.MensagemModel;

public class MensagemDTO {
    private String mensagem;
    private String assunto;
    private Long id;
    private TipoMensagem tipo;
    private String dataEnvio;
    private SituacaoMensagem situacaoMensagem;

    public MensagemDTO(){}

    public void setMensagem(String mensagem){
        this.mensagem = mensagem;
    }
    
    
    public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoMensagem getTipo() {
		return tipo;
	}

	public void setTipo(TipoMensagem tipo) {
		this.tipo = tipo;
	}

	public LocalDate getDataEnvio() {
		return null;
	}

	public void setDataEnvio(LocalDate dataEnvio) {
		this.dataEnvio = dataEnvio != null ? dataEnvio.toString() : null;
	}

	public SituacaoMensagem getSituacaoMensagem() {
		return situacaoMensagem;
	}

	public void setSituacaoMensagem(SituacaoMensagem situacaoMensagem) {
		this.situacaoMensagem = situacaoMensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public static MensagemDTO fromModel(MensagemModel model){
        MensagemDTO dto = new MensagemDTO();
        dto.setAssunto(model.getAssunto());
        dto.setMensagem(model.getCorpoMensagem());
        dto.setDataEnvio(model.getHorarioEnvio().toLocalDate());
        dto.setTipo(model.getTipo());
        dto.setSituacaoMensagem(model.getSituacao());
        dto.setId(model.getId());
        return dto;
    }

	public static List<MensagemDTO> fromModel(List<MensagemModel> models){
		List<MensagemDTO> dtos = new ArrayList<>();

		models.forEach(mensagem -> {
			dtos.add(fromModel(mensagem));
		});

       return dtos;
    }
}

package sd.ufpi.application.domain.dto;

import java.util.ArrayList;
import java.util.List;

import sd.ufpi.application.domain.model.MensagemModel;

public class MensagemCompletaDTO extends MensagemDTO {
    private List<MensagemDTO> respostas;

    public void setRespostas(List<MensagemDTO> respostas){
        this.respostas = respostas;
    }

    public List<MensagemDTO> getRespotas(){
        return this.respostas;
    }

    public static List<MensagemDTO> fromModels(List<MensagemModel> respostas){
        List<MensagemDTO> res = new ArrayList<>();
        respostas.forEach(resposta -> {
            res.add(MensagemDTO.fromModel(resposta));
        });

        return res;
    }

    public static MensagemCompletaDTO fromModel(MensagemModel model, List<MensagemModel> respostas){
        MensagemCompletaDTO dto = new MensagemCompletaDTO();
        dto.setAssunto(model.getAssunto());
        dto.setMensagem(model.getCorpoMensagem());
        dto.setDataEnvio(model.getHorarioEnvio().toLocalDate());
        dto.setTipo(model.getTipo());
        dto.setSituacaoMensagem(model.getSituacao());
        dto.setId(model.getId());
        if(respostas != null){
            dto.setRespostas(fromModels(respostas));
        }

        return dto;
    }
}

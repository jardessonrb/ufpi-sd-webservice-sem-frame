package sd.ufpi.application.service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import sd.ufpi.application.domain.dto.MensagemCompletaDTO;
import sd.ufpi.application.domain.dto.MensagemDTO;
import sd.ufpi.application.domain.enums.SituacaoMensagem;
import sd.ufpi.application.domain.enums.TipoMensagem;
import sd.ufpi.application.domain.form.EncaminharMensagemForm;
import sd.ufpi.application.domain.form.MensagemForm;
import sd.ufpi.application.domain.model.MensagemModel;
import sd.ufpi.application.domain.model.UsuarioModel;
import sd.ufpi.application.repository.MensagemRepository;
import sd.ufpi.application.repository.UsuarioRepository;

public class MensagemService {
    private MensagemRepository mensagemRepository;
    private UsuarioRepository usuarioRepository;

    public MensagemService() throws SQLException, IOException{
        this.mensagemRepository = new MensagemRepository();
        this.usuarioRepository  = new UsuarioRepository();
    }

    public MensagemDTO enviarMensagem(MensagemForm form) throws SQLException, IOException{
        UsuarioModel remetente = this.usuarioRepository.findById(form.getEmissor());
        UsuarioModel destinatario = this.usuarioRepository.findById(form.getReceptor());

        if(remetente == null || destinatario == null){
            return null;
        }
    
        MensagemModel model = new MensagemModel();

        model.setAssunto(form.getAssunto());
        model.setCorpoMensagem(form.getMensagem());
        model.setHorarioEnvio(LocalDateTime.now());
        model.setTipo(TipoMensagem.ORIGINAL);
        model.setSituacao(SituacaoMensagem.ENVIADA);
        model.setDestinatario(destinatario);
        model.setRementente(remetente);

        model = this.mensagemRepository.save(model);

        MensagemDTO dto = MensagemDTO.fromModel(model);
        
        return dto;
    }

    public MensagemDTO encaminhar(EncaminharMensagemForm form) throws SQLException, IOException{
        UsuarioModel remetente = this.usuarioRepository.findById(form.getIdUsuario());
        MensagemModel mensagem = this.mensagemRepository.findById(form.getIdMensagem());

        if(remetente == null || mensagem == null || form.getDestinatarios().size() < 1){
            return null;
        }

        this.mensagemRepository.encaminharMensagem(form.getIdMensagem(), form.getIdUsuario(), form.getDestinatarios());
        MensagemDTO dto = MensagemDTO.fromModel(this.mensagemRepository.findById(form.getIdMensagem()));
        return dto;
    }

    public MensagemCompletaDTO verMensagem(Long idMensagem) throws SQLException, IOException{
        MensagemModel model = this.mensagemRepository.findById(idMensagem);
        if(model == null){
            return null;
        }

        
        List<MensagemModel> respostas = this.mensagemRepository.findRespostasByIdMensagemOriginal(idMensagem);
        MensagemCompletaDTO dto = MensagemCompletaDTO.fromModel(model, respostas);

        return dto;
    }

    public MensagemDTO responderMensagem(MensagemForm form, Long idMensagem) throws SQLException, IOException{
        UsuarioModel remetente = this.usuarioRepository.findById(form.getEmissor());
        UsuarioModel destinatario = this.usuarioRepository.findById(form.getReceptor());
        MensagemModel mensagemModel = this.mensagemRepository.findById(idMensagem);

        if(remetente == null || mensagemModel == null || destinatario == null){
            return null;
        }
    
        MensagemModel model = new MensagemModel();

        model.setAssunto(form.getAssunto());
        model.setCorpoMensagem(form.getMensagem());
        model.setHorarioEnvio(LocalDateTime.now());
        model.setTipo(TipoMensagem.RESPOSTA);
        model.setSituacao(SituacaoMensagem.ENVIADA);
        model.setRementente(remetente);
        model.setDestinatario(destinatario);
        model.setMensagemOriginal(mensagemModel);

        model = this.mensagemRepository.save(model);

        MensagemDTO dto = MensagemDTO.fromModel(model);
        
        return dto;
    }

    public List<MensagemDTO> listarMensagem(Long idUsuario, SituacaoMensagem situacao) throws SQLException, IOException{
        UsuarioModel usuarioModel = this.usuarioRepository.findById(idUsuario);
        if(usuarioModel == null){
            return null;
        }
        System.out.println(idUsuario+" = "+situacao);

        List<MensagemModel> mensagens = this.mensagemRepository.findByDestinatarioAndSituacao(idUsuario, situacao);
        List<MensagemModel> mensagensEncaminhadas = this.mensagemRepository.findByMensagemEncaminhadasRecebidas(idUsuario);
        mensagens.addAll(mensagensEncaminhadas);

        return MensagemDTO.fromModel(mensagens);

    }

    public Boolean excluir(Long idMensagem) throws SQLException, IOException{
        MensagemModel model = this.mensagemRepository.findById(idMensagem);
        if(model == null){
            return null;
        }

        Boolean respostas = this.mensagemRepository.excluir(idMensagem);
        return respostas;
    }


}

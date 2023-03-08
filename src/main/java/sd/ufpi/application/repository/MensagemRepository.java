package sd.ufpi.application.repository;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import sd.ufpi.application.enums.SituacaoMensagem;
import sd.ufpi.application.enums.TipoMensagem;
import sd.ufpi.application.model.MensagemModel;
import sd.ufpi.core.database.ConnectionFactory;
import sd.ufpi.core.utils.DatesUtils;

public class MensagemRepository {
    private Connection connection;
    private ConnectionFactory connectionFactory;

    public MensagemRepository() throws SQLException, IOException{
        this.connectionFactory = new ConnectionFactory();
        this.connection = this.connectionFactory.getConnection();
    }

    public MensagemModel save(MensagemModel mensagem) throws SQLException, IOException{
        if(this.connection.isClosed()){
            this.connection = this.connectionFactory.getConnection();
        }

        String query;
        if(mensagem.getTipo().equals(TipoMensagem.RESPOSTA)){
            query = "insert into tb_mensagem(assunto_mensagem, corpo_mensagem, situacao_mensagem, horario_envio, tipo_mensagem, id_remetente, id_destinatario, id_mensagem_original) "+
            "values (?,?,?,?,?,?,?,?)";
        }else {
            query = "insert into tb_mensagem(assunto_mensagem, corpo_mensagem, situacao_mensagem, horario_envio, tipo_mensagem, id_remetente, id_destinatario) "+
            "values (?,?,?,?,?,?,?)";
        }

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.setString(1, mensagem.getAssunto());
        preparedStatement.setString(2, mensagem.getCorpoMensagem());
        preparedStatement.setString(3, mensagem.getSituacao().name());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setString(5, mensagem.getTipo().name());
        preparedStatement.setLong(6, mensagem.getRementente().getId());
        preparedStatement.setLong(7, mensagem.getDestinatario().getId());
        if(mensagem.getTipo().equals(TipoMensagem.RESPOSTA)){
            preparedStatement.setLong(8, mensagem.getMensagemOriginal() != null ? mensagem.getMensagemOriginal().getId() : 1L);
        }
        preparedStatement.execute();

        preparedStatement = this.connection.prepareStatement("select * from tb_mensagem order by id desc limit 1");
        ResultSet resultado = preparedStatement.executeQuery();
        if(resultado.next()){
            mensagem.setId(new BigInteger(resultado.getString("id")).longValue());
            mensagem.setAssunto(resultado.getString("assunto_mensagem"));
            mensagem.setCorpoMensagem(resultado.getString("corpo_mensagem"));
            mensagem.setSituacao(SituacaoMensagem.valueOf(resultado.getString("situacao_mensagem")));
            mensagem.setTipo(TipoMensagem.valueOf(resultado.getString("tipo_mensagem")));
            mensagem.setHorarioEnvio(DatesUtils.stringToLocalDatetime(resultado.getString("horario_envio")));
            mensagem.setDestinatario(null);
            mensagem.setRementente(null);
            mensagem.setMensagemOriginal(null);
        }

        this.connection.close();
        return mensagem;

    }

    public MensagemModel updateSituacaoMensagem(Long idMensagem, SituacaoMensagem situacao) throws SQLException, IOException{
        if(this.connection.isClosed()){
            this.connection = this.connectionFactory.getConnection();
        }

        String query = "select * from tb_mensagem where id = ?";
        PreparedStatement preparedStatementValidarSeExiste = this.connection.prepareStatement(query);
        preparedStatementValidarSeExiste.setLong(1, idMensagem);
        ResultSet resultado = preparedStatementValidarSeExiste.executeQuery();
        this.connection.close();
        if(resultado.next()){
            this.connection = this.connectionFactory.getConnection();
            String queryUpdate = "update tb_mensagem set situacao_mensagem = ? where id = ?";
            PreparedStatement preparedStatementExecutaUpdate = this.connection.prepareStatement(queryUpdate);

            preparedStatementExecutaUpdate.setString(1, situacao.name());
            preparedStatementExecutaUpdate.setLong(2, idMensagem);
            int resultadoUpdate = preparedStatementExecutaUpdate.executeUpdate();
            this.connection.close();

            if(resultadoUpdate == 1){
                this.connection = this.connectionFactory.getConnection();
                PreparedStatement preparedStatementBuscarMensagemAtualizada = this.connection.prepareStatement("select * from tb_mensagem order by id desc limit 1");
                ResultSet mensagemAtualizaResult = preparedStatementBuscarMensagemAtualizada.executeQuery();
                this.connection.close();

                if(mensagemAtualizaResult.next()){
                    MensagemModel mensagem = new MensagemModel();

                    mensagem.setId(new BigInteger(mensagemAtualizaResult.getString("id")).longValue());
                    mensagem.setAssunto(mensagemAtualizaResult.getString("assunto_mensagem"));
                    mensagem.setCorpoMensagem(mensagemAtualizaResult.getString("corpo_mensagem"));
                    mensagem.setSituacao(SituacaoMensagem.valueOf(mensagemAtualizaResult.getString("situacao_mensagem")));
                    mensagem.setTipo(TipoMensagem.valueOf(mensagemAtualizaResult.getString("tipo_mensagem")));
                    mensagem.setHorarioEnvio(DatesUtils.stringToLocalDatetime(mensagemAtualizaResult.getString("horario_envio")));
                    mensagem.setDestinatario(null);
                    mensagem.setRementente(null);
                    mensagem.setMensagemOriginal(null);

                    return mensagem;
                }
            }
        }
        return null;
    }
   
    public void encaminharMensagem(Long idMensagemOriginal, Long idRemetente, List<Long> destinatarios) throws SQLException, IOException{
        if(this.connection.isClosed()){
            this.connection = this.connectionFactory.getConnection();
        }

        String query = "insert into tb_registro_mensagem_encaminhada (id_mensagem_original, id_remetente, id_receptor) values (?,?,?)";
        destinatarios.forEach(destinatario -> {
            try {
                PreparedStatement preparedStatement = this.connection.prepareStatement(query);
                preparedStatement.setLong(1, idMensagemOriginal);
                preparedStatement.setLong(2, idRemetente);
                preparedStatement.setLong(3, destinatario);
                preparedStatement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        updateSituacaoMensagem(idMensagemOriginal, SituacaoMensagem.ENCAMINHADA);
        if(!this.connection.isClosed()){
            this.connection.close();
        }
    }

    public List<MensagemModel> findByRemetenteAndSituacao(Long idRemetente, SituacaoMensagem situacao) throws SQLException, IOException{
        if(this.connection.isClosed()){
            this.connection = this.connectionFactory.getConnection();
        }

        PreparedStatement preparedStatement = this.connection.prepareStatement("select * from tb_mensagem where situacao_mensagem = ? and id_remetente = ? and tipo_mensagem != ?");
        preparedStatement.setString(1, situacao.name());
        preparedStatement.setLong(2, idRemetente);
        preparedStatement.setString(3, TipoMensagem.RESPOSTA.name());
        ResultSet resultado = preparedStatement.executeQuery();
        List<MensagemModel> mensagens = new ArrayList<>();
        while(resultado.next()){
            MensagemModel mensagem = new MensagemModel();
            mensagem.setId(new BigInteger(resultado.getString("id")).longValue());
            mensagem.setAssunto(resultado.getString("assunto_mensagem"));
            mensagem.setCorpoMensagem(resultado.getString("corpo_mensagem"));
            mensagem.setSituacao(SituacaoMensagem.valueOf(resultado.getString("situacao_mensagem")));
            mensagem.setTipo(TipoMensagem.valueOf(resultado.getString("tipo_mensagem")));
            mensagem.setHorarioEnvio(DatesUtils.stringToLocalDatetime(resultado.getString("horario_envio")));
            mensagem.setDestinatario(null);
            mensagem.setRementente(null);
            mensagem.setRespostas(new ArrayList<>());

            mensagens.add(mensagem);
        }
        this.connection.close();
        return mensagens;
    }

    public MensagemModel findById(Long idMessage) throws SQLException, IOException{
        PreparedStatement prepared = this.connection.prepareStatement("select * from tb_mensagem where id = ?");
        prepared.setLong(1, idMessage);
        ResultSet mensagemAtualizaResult = prepared.executeQuery();
        this.connection.close();

        if(mensagemAtualizaResult.next()){
            MensagemModel mensagem = new MensagemModel();

            mensagem.setId(new BigInteger(mensagemAtualizaResult.getString("id")).longValue());
            mensagem.setAssunto(mensagemAtualizaResult.getString("assunto_mensagem"));
            mensagem.setCorpoMensagem(mensagemAtualizaResult.getString("corpo_mensagem"));
            mensagem.setSituacao(SituacaoMensagem.valueOf(mensagemAtualizaResult.getString("situacao_mensagem")));
            mensagem.setTipo(TipoMensagem.valueOf(mensagemAtualizaResult.getString("tipo_mensagem")));
            mensagem.setHorarioEnvio(DatesUtils.stringToLocalDatetime(mensagemAtualizaResult.getString("horario_envio")));
            mensagem.setDestinatario(null);
            mensagem.setRementente(null);
            mensagem.setMensagemOriginal(null);
            mensagem.setRespostas(findRespostasByIdMensagemOriginal(idMessage));

            return mensagem;
        }   

        return null;
    }

    public List<MensagemModel> findRespostasByIdMensagemOriginal(Long idMessage) throws SQLException, IOException{
        if(this.connection.isClosed()){
            this.connection = this.connectionFactory.getConnection();
        }

        String query = "select msgr.* from tb_mensagem msgo"+
        " join tb_mensagem msgr on msgo.id = msgr.id_mensagem_original"+
        " where msgo.id = ? and msgr.tipo_mensagem = ?";

        PreparedStatement preparedStatement =  this.connection.prepareStatement(query);
        preparedStatement.setLong(1, idMessage);
        preparedStatement.setString(2, TipoMensagem.RESPOSTA.name());

        ResultSet resultado = preparedStatement.executeQuery();
        this.connection.close();

        List<MensagemModel> mensagens = new ArrayList<>();
        while(resultado.next()){
            MensagemModel mensagem = new MensagemModel();

            mensagem.setId(new BigInteger(resultado.getString("id")).longValue());
            mensagem.setAssunto(resultado.getString("assunto_mensagem"));
            mensagem.setCorpoMensagem(resultado.getString("corpo_mensagem"));
            mensagem.setSituacao(SituacaoMensagem.valueOf(resultado.getString("situacao_mensagem")));
            mensagem.setTipo(TipoMensagem.valueOf(resultado.getString("tipo_mensagem")));
            mensagem.setHorarioEnvio(DatesUtils.stringToLocalDatetime(resultado.getString("horario_envio")));
            mensagem.setDestinatario(null);
            mensagem.setRementente(null);
            mensagem.setMensagemOriginal(null);

            mensagens.add(mensagem);
        }   

        return mensagens;
    }

    public static void main(String[] args) throws SQLException, IOException {
        MensagemRepository repository = new MensagemRepository();
        // UsuarioModel remetente = new UsuarioModel();
        // UsuarioModel destinatario = new UsuarioModel();

        // remetente.setId(1L);
        // destinatario.setId(3L);

        // MensagemModel mensagemModel = new MensagemModel();
        // mensagemModel.setAssunto("Teste de Mensagem");
        // mensagemModel.setCorpoMensagem("Essa é uma mensagem enviada");
        // mensagemModel.setSituacao(SituacaoMensagem.ENVIADA);
        // mensagemModel.setTipo(TipoMensagem.ORIGINAL);
        // mensagemModel.setDestinatario(destinatario);
        // mensagemModel.setRementente(remetente);
        // mensagemModel.setMensagemOriginal(null);

        // mensagemModel = repository.save(mensagemModel);
        // MensagemModel mensagemModel = repository.updateSituacaoMensagem(1L, SituacaoMensagem.ENCAMINHADA);
        // System.out.println("id da mensagem: "+mensagemModel.getId());

        // repository.encaminharMensagem(1L, 1L, Arrays.asList(4L, 7L, 9L));

        // MensagemModel mensagemModel = repository.findById(1L);
        // System.out.println("Mensagem original "+mensagemModel.getAssunto());
        // if(mensagemModel.getRespostas().size() > 0){
        //     System.out.println("Respostas:");
        //     mensagemModel.getRespostas().forEach(resposta -> {
        //         System.out.println(resposta.getAssunto());
        //     });
        // }
    }
}

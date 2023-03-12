package sd.ufpi.application.routine;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import sd.ufpi.application.domain.enums.SituacaoMensagem;
import sd.ufpi.application.domain.enums.TipoMensagem;
import sd.ufpi.core.database.ConnectionFactory;

public class DatabaseInit {
    
    public DatabaseInit() {}

    public static void initDatabase() throws SQLException, IOException{
        System.out.println("Starting database migrations");
        Connection connection = new ConnectionFactory().getConnection();
        DatabaseInit init = new DatabaseInit();
        init.createTableUser(connection);
        init.createTableMessage(connection);
        init.createTableForwardingRecord(connection);
        // init.createConstraints(connection);
        init.insertDatas(connection);

        System.out.println("Database started ...");
    }

    private boolean createTableUser(Connection connection) throws SQLException{
        String createTable = "create table if not exists tb_usuario ( "+
            "id serial primary key, "+
            "nome_usuario varchar unique not null, "+
            "senha_usuario varchar(20) not null, "+
            "ativo boolean default true) ";

        Statement statement = connection.createStatement();

        return statement.execute(createTable);
    }

    private boolean createTableMessage(Connection connection) throws SQLException{
        String createTable = "create table if not exists tb_mensagem ( "+
            "id serial primary key, "+
            "assunto_mensagem varchar not null, "+
            "corpo_mensagem varchar not null, "+
            "situacao_mensagem varchar not null, "+
            "horario_envio timestamp default current_timestamp, "+
            "tipo_mensagem varchar not null, "+
            "id_remetente bigint not null, "+
            "id_destinatario bigint not null, "+
            "id_mensagem_original bigint default null, "+
            "ativo boolean default true)";

        Statement statement = connection.createStatement();

        return statement.execute(createTable);
    }

    private boolean createTableForwardingRecord(Connection connection) throws SQLException{
        String createTable = "create table if not exists tb_registro_mensagem_encaminhada ( "+
            "id serial primary key, "+
            "id_mensagem_original bigint not null, "+
            "id_remetente bigint not null, "+
            "id_receptor bigint not null, "+
            "ativo boolean default true)";

        Statement statement = connection.createStatement();

        return statement.execute(createTable);
    }

    private void createConstraints(Connection connection) throws SQLException{
        String foreingKeyRementente = "alter table tb_mensagem add foreign key (id_remetente) references tb_usuario(id)";
        String foreingKeyDesinatario = "alter table tb_mensagem add foreign key (id_destinatario) references tb_usuario(id)";
        String foreingKeyMensagemOriginal = "alter table tb_mensagem add foreign key (id_mensagem_original) references tb_mensagem(id)";
        String foreingKeyRegistroRemetente = "alter table tb_registro_mensagem_encaminhada add foreign key (id_remetente) references tb_usuario(id)";
        String foreingKeyRegistroReceptor = "alter table tb_registro_mensagem_encaminhada add foreign key (id_receptor) references tb_usuario(id)";

        
        PreparedStatement rementente = connection.prepareStatement(foreingKeyRementente);
        rementente.executeQuery();

        PreparedStatement desinatario = connection.prepareStatement(foreingKeyDesinatario);
        desinatario.executeQuery();

        PreparedStatement mensagemOriginal = connection.prepareStatement(foreingKeyMensagemOriginal);
        mensagemOriginal.executeQuery();

        PreparedStatement registroRemetente = connection.prepareStatement(foreingKeyRegistroRemetente);
        registroRemetente.executeQuery();

        PreparedStatement registroReceptor = connection.prepareStatement(foreingKeyRegistroReceptor);
        registroReceptor.executeQuery();
    }

    private boolean insertDatas(Connection connection) throws SQLException{
        String insertUser1 = "insert into tb_usuario (nome_usuario, senha_usuario) values ('sistema@sd', '123456')";
        String insertUser2 = "insert into tb_usuario (nome_usuario, senha_usuario) values ('usuario#1', '123456')";
        String insertUser3 = "insert into tb_usuario (nome_usuario, senha_usuario) values ('usuario#2', '123456')";

        PreparedStatement user01 = connection.prepareStatement(insertUser1);
        user01.execute();
        PreparedStatement user02 = connection.prepareStatement(insertUser2);
        user02.execute();
        PreparedStatement user03 = connection.prepareStatement(insertUser3);
        user03.execute();


        String insertMessage = "insert into tb_mensagem(assunto_mensagem, corpo_mensagem, situacao_mensagem, horario_envio, tipo_mensagem, id_remetente, id_destinatario) "+
        "values (?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(insertMessage);
        preparedStatement.setString(1, "Primeira Mensagem");
        preparedStatement.setString(2, "Essa mensagem é uma mensagem padrão no inicio");
        preparedStatement.setString(3, SituacaoMensagem.ENVIADA.name());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setString(5, TipoMensagem.ORIGINAL.name());
        preparedStatement.setLong(6, 1L);
        preparedStatement.setLong(7, 2L);

        preparedStatement.execute();

        return true;
    }

    
}

package sd.ufpi.application.repository;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sd.ufpi.application.domain.model.UsuarioModel;
import sd.ufpi.core.database.ConnectionFactory;

public class UsuarioRepository {
    private Connection connection;
    private ConnectionFactory connectionFactory;

    public UsuarioRepository() throws SQLException, IOException{
        this.connectionFactory = new ConnectionFactory();
        this.connection = this.connectionFactory.getConnection();
    }

    public UsuarioModel findById(Long id) throws SQLException, IOException{
        if(this.connection.isClosed()){
            this.connection = this.connectionFactory.getConnection();
        }

        PreparedStatement statement = this.connection.prepareStatement("select * from tb_usuario where id = ?");
        statement.setLong(1, id);

        ResultSet resultado = statement.executeQuery();
        this.connection.close();

        if(resultado.next()){
            UsuarioModel usuarioModel = new UsuarioModel();

            usuarioModel.setId(new BigInteger(resultado.getString("id")).longValue());
            usuarioModel.setNome(resultado.getString("nome_usuario"));
            usuarioModel.setSenha(resultado.getString("senha_usuario"));

            return usuarioModel;
        }

        return null;
    }

    public UsuarioModel findByNameAndSenha(String name, String senha) throws SQLException, IOException{
        if(this.connection.isClosed()){
            this.connection = this.connectionFactory.getConnection();
        }

        PreparedStatement statement = this.connection.prepareStatement("select * from tb_usuario where nome_usuario = ? and senha_usuario = ?");
        statement.setString(1, name);
        statement.setString(2, senha);
        
        ResultSet resultado = statement.executeQuery();
        this.connection.close();
        
        if(resultado.next()){
            UsuarioModel usuarioModel = new UsuarioModel();
            usuarioModel.setId(new BigInteger(resultado.getString("id")).longValue());
            usuarioModel.setNome(resultado.getString("nome_usuario"));
            usuarioModel.setSenha(resultado.getString("senha_usuario"));

            return usuarioModel;
        }

        return null;
    }

    public List<UsuarioModel> findByNameContaingIgnoreCase(String name) throws SQLException, IOException{
        if(this.connection.isClosed()){
            this.connection = this.connectionFactory.getConnection();
        }

        PreparedStatement statement = this.connection.prepareStatement("select * from tb_usuario where nome_usuario ilike '%"+name+"%'");

        ResultSet resultado = statement.executeQuery();
        this.connection.close();

        List<UsuarioModel> usuarios = new ArrayList<>();
        while(resultado.next()){
            UsuarioModel usuarioModel = new UsuarioModel();

            usuarioModel.setId(new BigInteger(resultado.getString("id")).longValue());
            usuarioModel.setNome(resultado.getString("nome_usuario"));
            usuarioModel.setSenha(resultado.getString("senha_usuario"));
            usuarios.add(usuarioModel);
        }

        return usuarios;
    }

    public UsuarioModel save(UsuarioModel usuarioModel) throws SQLException, IOException{
        if(this.connection.isClosed()){
            this.connection = this.connectionFactory.getConnection();
        }

        PreparedStatement statementInsert = this.connection.prepareStatement("insert into tb_usuario(nome_usuario, senha_usuario) values (?, ?)");
        statementInsert.setString(1, usuarioModel.getNome());
        statementInsert.setString(2, usuarioModel.getSenha());
        statementInsert.execute();

        PreparedStatement statementFind = this.connection.prepareStatement("select * from tb_usuario order by id desc limit 1");
        ResultSet resultado = statementFind.executeQuery();
        if(resultado.next()){
            usuarioModel.setId(new BigInteger(resultado.getString("id")).longValue());
            usuarioModel.setNome(resultado.getString("nome_usuario"));
            usuarioModel.setSenha(resultado.getString("senha_usuario"));
    
            System.out.println("Id: " + resultado.getString("id"));
        }
        
        this.connection.close();
        return usuarioModel;

    }

    public UsuarioModel findByName(String name) throws SQLException, IOException{
        if(this.connection.isClosed()){
            this.connection = this.connectionFactory.getConnection();
        }

        PreparedStatement statement = this.connection.prepareStatement("select * from tb_usuario where nome_usuario = ?");
        statement.setString(1, name);

        ResultSet resultado = statement.executeQuery();
        this.connection.close();

        if(resultado.next()){
            UsuarioModel usuarioModel = new UsuarioModel();

            usuarioModel.setId(new BigInteger(resultado.getString("id")).longValue());
            usuarioModel.setNome(resultado.getString("nome_usuario"));
            usuarioModel.setSenha(resultado.getString("senha_usuario"));

            return usuarioModel;
        }

        return null;
    }
    
}

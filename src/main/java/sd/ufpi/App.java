package sd.ufpi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import sd.ufpi.application.controllers.MessageController;
import sd.ufpi.application.controllers.UserController;
import sd.ufpi.application.domain.model.UsuarioModel;
import sd.ufpi.application.repository.UsuarioRepository;
import sd.ufpi.core.database.ConnectionFactory;
import sd.ufpi.core.rest.Root;
import sd.ufpi.core.rest.Server;
import sd.ufpi.core.rest.exceptions.ClassControllerAlreadyExists;
import sd.ufpi.core.rest.exceptions.ClassNotController;

public class App
{
    public static void main( String[] args ) throws IOException, SQLException, ClassNotController, ClassControllerAlreadyExists
    {
        Root root = new Root();
        root.addController(new UserController());
        root.addController(new MessageController());
        
        Server servidor = new Server(8080, root);
        servidor.start();

        // Thread thread1 = new Thread(new TesteRunner(servidor), "thread1");
        // Thread thread2 = new Thread(new TesteRunner(servidor), "thread2");
        // Thread thread3 = new Thread(new TesteRunner(servidor), "thread3");

        // thread1.start();
        // thread2.start();
        // thread3.start();

        // try {
            
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }
        
        // // try {
        //     ConnectionFactory connection =  new ConnectionFactory();
        //     Connection conn = connection.getConnection();
        //     ResultSet resultSet;
        //     // PreparedStatement prs = conn.prepareStatement("insert into tb_usuario(nome_usuario, senha_usuario) values (?, ?)");

        //     // prs.setString(1, "usuario02");
        //     // prs.setString(2, "senha02");

        //     // prs.execute();

        //     UsuarioRepository repository = new UsuarioRepository();
        //     UsuarioModel usuarioModel = repository.findByNameAndSenha("usuario01", "senha01");
        //     if(usuarioModel == null) {
        //         System.out.println("Não achou nenhum usuario");
        //     }else {
        //         System.out.println(usuarioModel.getId()+" "+usuarioModel.getNome()+" "+usuarioModel.getSenha());
        //     }

        //     List<UsuarioModel> usuarioModels = repository.findByNameContaingIgnoreCase("02");
        //     usuarioModels.forEach(usuario -> {
        //         System.out.println(usuario.getId()+" "+usuario.getNome()+" "+usuario.getSenha());
        //     });

            // System.out.println(resultSet.getString("id"));
            // Statement statement = conn.createStatement();
            // resultSet = statement.executeQuery("select * from tb_usuario");
            // while(resultSet.next()){
            //     System.out.println(resultSet.getString("id")+" = "+resultSet.getString("nome_usuario")+" = "+resultSet.getString("senha_usuario"));
            // }
            // conn.close();
        // } catch (Exception e) {
        //     System.out.println("erro: "+e.getMessage());
        // }




    }
}

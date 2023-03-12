package sd.ufpi;

import java.io.IOException;
import java.sql.SQLException;

import sd.ufpi.application.controllers.MessageController;
import sd.ufpi.application.controllers.UserController;
import sd.ufpi.application.routine.DatabaseInit;
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
        DatabaseInit.initDatabase();
        servidor.start();
    }
}

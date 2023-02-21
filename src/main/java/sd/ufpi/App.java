package sd.ufpi;
import java.io.IOException;

import sd.ufpi.controllers.MessageController;
import sd.ufpi.controllers.UserController;
import sd.ufpi.core.rest.Root;
import sd.ufpi.core.rest.Server;

public class App
{
    public static void main( String[] args ) throws IOException
    {
        Root root = new Root();
        root.addController(new UserController());
        root.addController(new MessageController());

        Server servidor = new Server(8080, root);

        servidor.start();
    }
}

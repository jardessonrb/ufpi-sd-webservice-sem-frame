package sd.ufpi.core.rest;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

public class Server {
    private static int PORT_DEFAULT = 8080;
    private static String CONTEXTO_DEFAULT = "/";
    private HttpServer servidor = null;

    public Server(int porta, HttpHandler handler) throws IOException {
        this.servidor = HttpServer.create(new InetSocketAddress(porta), 5);

        this.servidor.createContext(CONTEXTO_DEFAULT, handler);
    }

    public Server(HttpHandler handler)  throws IOException{
        this(PORT_DEFAULT, handler);
    }

    public void start(){
        this.servidor.start();
    }

    public void stop(){
        this.servidor.stop(1);
    }
}

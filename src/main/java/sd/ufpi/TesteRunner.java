package sd.ufpi;

import sd.ufpi.core.rest.Server;

public class TesteRunner implements Runnable {

    private Server server;
    public TesteRunner(Server server){
        this.server = server;
    }
    @Override
    public void run() {
        synchronized(this){
            this.server.start();
        }
    }
    
}

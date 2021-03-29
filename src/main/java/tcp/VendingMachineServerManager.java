package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class VendingMachineServerManager {
    BlockingQueue<String> serverErrors = new LinkedBlockingQueue<>();
    BlockingQueue<TCPServerCommand> commands = new LinkedBlockingQueue<>();
    private Integer port = null;
    private Thread thread = null;
    private Runnable task = null;

    public VendingMachineServerManager(Integer port) {
        this.port = port;
        this.task = () -> {
            Boolean shouldRun = true;
            VendingMachineServer server = new VendingMachineServer(port);
            try {
                server.listen();
            } catch (IOException e) {
                server.stop(e);
                try {
                    serverErrors.put(e.toString());
                } catch (InterruptedException interruptedException) {
                    System.err.println("The thread managed by " + this.getClass().getCanonicalName() + " died while handling exceptions.");
                    interruptedException.printStackTrace();
                    return;
                }
            }
            TCPServerCommand lastCommand = TCPServerCommand.HANDLE_CONNECTION;
            while (shouldRun) {
                try {
                    TCPServerCommand cmd = commands.poll(100, TimeUnit.MILLISECONDS);
                    if (cmd != null) {
                        lastCommand = cmd;
                    }
                } catch (InterruptedException e) {
                    System.err.println("The thread managed by " + this.getClass().getCanonicalName() + " died while handling exceptions.");
                    e.printStackTrace();
                    return;
                }

                try {

                    switch (lastCommand) {
                        case STOP:
                            shouldRun = false;
                            server.stop(null);
                            break;
                        case HANDLE_CONNECTION:
                            server.handleNewConnection();
                            break;
                    }
                } catch (IOException e) {
                    try {
                        serverErrors.put(e.toString());
                    } catch (InterruptedException ie) {
                        System.err.println("The thread managed by " + this.getClass().getCanonicalName() + " was interrupted while handling the upstream exception: " + e.toString());
                        ie.printStackTrace();
                        return;
                    }
                }
            }
        };
    }

    public VendingMachineServerManager() throws IOException {
        this(getFreeTCPPort());
    }

    private static Integer getFreeTCPPort() throws IOException {
        // https://www.baeldung.com/java-free-port#finding-a-free-port
        ServerSocket serverSocket = new ServerSocket(0);
        Integer localPort = serverSocket.getLocalPort();
        serverSocket.close();
        return localPort;
    }

    public void start() {
        if (this.thread != null) {
            throw new RuntimeException("VendingMachineServer is already running (on port: " + this.port.toString() + ")");
        }


        this.thread = new Thread(task);
        thread.start();
    }


    public Integer getPort() {
        return this.port;
    }

    public void stop() {
        commands.add(TCPServerCommand.STOP);
    }
}

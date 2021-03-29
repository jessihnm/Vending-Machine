package tcp;

import java.io.IOException;

public interface TCPServer {
    void run() throws IOException, InterruptedException;

    Boolean listen() throws IOException;

    void handleNewConnection() throws IOException, InterruptedException;

    void stop(Exception e);
}

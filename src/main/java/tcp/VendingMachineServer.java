package tcp;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import serialization.EnglishSerializer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class VendingMachineServer implements TCPServer {
    private static final Integer DEFAULT_PORT = 7777;
    private final Map<String, Integer> availableProducts = new HashMap<String, Integer>() {{
        put("Coke Zero", 3);
        put("Fanta", 4);
        put("Sprite", 6);
    }};
    private final EnglishSerializer serializer = new EnglishSerializer();
    private final Integer port;
    private boolean shouldRun = true;
    private ServerSocket socket = null;

    VendingMachineServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        VendingMachineServer server = new VendingMachineServer(DEFAULT_PORT);
        server.run();
    }

    public void run() throws IOException, InterruptedException {
        this.listen();
        while (this.shouldRun) {
            this.handleNewConnection();
        }
    }

    /**
     * @return Boolean - true when the socket *just* started listening or false if the socket was already listening before this method was called.
     * @throws IOException
     */
    public Boolean listen() throws IOException {
        if (socket == null) {
            socket = new ServerSocket(port);
            socket.setReuseAddress(true);
            System.out.println(this.getClass().getName() + " is running and ready to accept connections on port " + this.port.toString() + " ✅");
            return true;
        }
        return false;
    }

    public Integer getPort() {
        return port;
    }

    /**
     * @param input  a DataInputStream managed by the server
     * @param output a DataOutputStream managed by the server
     * @return Boolean - when `true`  the server stays listening for connections and responding to requests - otherwise the server stops running after processing the current request.
     * @throws IOException
     */
    public Boolean processConnection(DataInputStream input, DataOutputStream output) throws IOException {
        String request = input.readUTF();
        System.out.println("client sent request: ⬆️ " + request);

        if (request.equals("show products;")) {
            output.writeUTF(serializer.serialize(availableProducts));
        } else if (request.equals("stop server;")) {
            output.writeBoolean(true);
            this.shouldRun = false;
        } else {
            HashFunction hf = Hashing.sha256();
            HashCode hc = hf.newHasher()
                    .putString(serializer.serialize(availableProducts), Charsets.UTF_8)
                    .hash();
            output.writeUTF(hc.toString());
        }
        System.out.println("Server sent response: ⬇️ ");
        return this.shouldRun;
    }

    public void handleNewConnection() throws IOException {
        if (socket == null) {
            throw new TCPServerError("handleNewConnection() cannot be called before listen()");
        }
        Socket connection = socket.accept();

        System.out.println("client connected: " + connection.getInetAddress().getCanonicalHostName() + ":" + connection.getPort());

        InputStream is = connection.getInputStream();
        OutputStream os = connection.getOutputStream();
        DataInputStream input = new DataInputStream(is);
        DataOutputStream output = new DataOutputStream(os);

        while (processConnection(input, output)) {
            Thread.yield();
        }
        output.close();
        input.close();
        connection.close();
    }

    public void stop(Exception e) {
        this.shouldRun = false;
        if (!socket.isClosed()) {
            if (e != null) {
                System.err.println("Closing server socket under exception " + e.toString());
            }
            try {
                socket.close();
            } catch (IOException ioErr) {
                System.err.println(this.getClass().getCanonicalName() + " Failed to close its server socket " + ioErr.toString());
            }
        }
    }


}

package tcp;

import parser.EnglishSerializer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class VendingMachineServer {
    public static final int PORTNUMBER = 7777;
    private final int port;
    private boolean shouldRun = true;
    private EnglishSerializer serializer = new EnglishSerializer();
    private final Map<String, Integer> availableProducts = new HashMap<String, Integer>() {{
        put("Coke Zero", 3);
        put("Fanta", 4);
        put("Sprite", 6);
    }};

    VendingMachineServer(int port) { //Konstruktor
        this.port = port;  //mit Port an dem er lauschen kann
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        VendingMachineServer vendingMachineServer = new VendingMachineServer(PORTNUMBER);
        vendingMachineServer.run();
    }

    public void run() throws IOException {
        while (this.shouldRun) {
            this.handleRequest();
        }
    }

    public Socket acceptSocket() throws IOException {
        ServerSocket srvSocket = new ServerSocket(this.port); //Serverport anlegen
        System.out.println("ready to accept connections");
        return srvSocket.accept();
    }

    public void handleRequest() throws IOException {

        Socket socket = this.acceptSocket();

        System.out.println("client connected");

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        DataInputStream input = new DataInputStream(is);
        DataOutputStream output = new DataOutputStream(os);

        String request = input.readUTF();
        System.out.println("client sent request: " + request);

        if (request.equals("show products;")) {
            output.writeUTF(serializer.serialize(availableProducts));
        }
        output.close();
        input.close();
    }


}

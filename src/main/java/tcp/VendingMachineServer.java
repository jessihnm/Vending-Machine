package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class VendingMachineServer {
    private final int port;
    public static final int PORTNUMBER = 7777;

    public static void main (String [] args) throws IOException, InterruptedException {
        VendingMachineServer vendingMachineServer = new VendingMachineServer(PORTNUMBER);
        vendingMachineServer.handleRequest();
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
            output.writeUTF("3 Coke Zero, 4 Fanta, 6 Sprite.");
        }
        output.close();
        input.close();
    }

    VendingMachineServer(int port) { //Konstruktor
        this.port = port;  //mit Port an dem er lauschen kann
    }


}

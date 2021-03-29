package tcp;


import serialization.EnglishDeserializationError;
import serialization.EnglishDeserializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class VendingMachineClient {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 7777;
    private final String hostname;
    private final int port;
    private Socket socket = null;

    private DataOutputStream output = null;
    private DataInputStream input = null;

    VendingMachineClient(String hostname, int port) throws IOException {
        this.hostname = hostname;
        this.port = port;
        this.socket = new Socket(this.hostname, this.port);

        output = new DataOutputStream(socket.getOutputStream());
        input = new DataInputStream(socket.getInputStream());
    }

    public static void main(String[] args) throws IOException, EnglishDeserializationError, VendingMachineClientDisconnected {

        VendingMachineClient client = new VendingMachineClient(DEFAULT_HOST, DEFAULT_PORT);
        Map<String, Integer> products = client.askForProducts();

        Set<Map.Entry<String, Integer>> entries = products.entrySet();
        System.out.println("\033[1;33mThe vending machine has the following products: \033[0m");

        for (Map.Entry<String, Integer> e : entries) {
            System.out.println("\033[1;32m" + e.getKey() + " \033[0m");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println("...");
        }
        Boolean serverStopped = client.stopServer();
        if (serverStopped) {
            System.out.println("server politely stopped itself.");

        }
    }

    public Boolean stopServer() throws VendingMachineClientDisconnected {
        System.out.println("client is requesting server to stop");
        try {
            output.writeUTF("stop server;");
            Boolean response = input.readBoolean();
            System.out.println("server sent response " + response.toString());
            return response;
        } catch (IOException e) {
            System.err.println("server did not send response");
            throw new VendingMachineClientDisconnected(this);
        }
    }

    public Map<String, Integer> askForProducts() throws EnglishDeserializationError, VendingMachineClientDisconnected {
        EnglishDeserializer parser = new EnglishDeserializer();
        System.out.println("client is requesting to see products");
        String response;

        try {
            output.writeUTF("show products;");
            response = input.readUTF();
        } catch (IOException e) {
            throw new VendingMachineClientDisconnected(this);
        }
        System.out.println("server sent response");
        return parser.deserialize(response);
    }


    public String getServerAddress() {
        return this.hostname + ":" + this.port;
    }
}

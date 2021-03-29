package tcp;


import parser.EnglishDeserializationError;
import parser.EnglishDeserializer;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class VendingMachineClient {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 7777;
    private final String hostname;
    private final int port;
    private Socket socket = null;


    VendingMachineClient(String hostname, int port) throws IOException {//repräsentiert Verbinung zum Server
        this.hostname = hostname;
        this.port = port;
        this.socket = new Socket(this.hostname, this.port);
    }

    public static void main(String[] args) throws IOException, EnglishDeserializationError {

        VendingMachineClient client = new VendingMachineClient(DEFAULT_HOST, DEFAULT_PORT);
        Map<String, Integer> products = client.askForProducts();

        Set<Map.Entry<String, Integer>> entries = products.entrySet();
        System.out.println("\033[1;33mThe vending machine has the following products: \033[0m");

        for (Map.Entry<String, Integer> e : entries) {
            System.out.println("\033[1;32m" + e.getKey() + " \033[0m");
        }

    }

    public Map<String, Integer> askForProducts() throws IOException, EnglishDeserializationError {
        EnglishDeserializer parser = new EnglishDeserializer();

        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        DataOutputStream output = new DataOutputStream(os);
        DataInputStream input = new DataInputStream(is);
        System.out.println("client is requesting to see products");

        output.writeUTF("show products;");

        String response = input.readUTF();
        System.out.println(".server sent response");
        return parser.deserialize(response);
    }


}

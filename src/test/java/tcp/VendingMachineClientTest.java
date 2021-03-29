package tcp;

import org.junit.Test;
import parser.EnglishDeserializationError;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VendingMachineClientTest {

    private static int getFreeTCPPort() {
        // https://www.baeldung.com/java-free-port#finding-a-free-port
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            int localPort = serverSocket.getLocalPort();
            serverSocket.close();
            return localPort;
        } catch (IOException e) {
            throw new AssertionError("Failed to get free TCP port");
        }
    }

    public void startServer(Integer port) {
        // Background: I have a Thread that
        Runnable task = () -> {
            VendingMachineServer server = new VendingMachineServer(port);

            try {
                server.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    @Test
    public void testClientConnectsToServer() throws IOException, EnglishDeserializationError {
        // Given that I am using a free TCP port
        Integer port = getFreeTCPPort();
        // Given that I have the TCP server running in background that port
        startServer(port);
        // And that I have a TCP client connected to the server on that port
        VendingMachineClient client = new VendingMachineClient("localhost", port);

        // When I call askForProducts
        Map<String, Integer> products = client.askForProducts();

        // Then it should return the expected list of products
        assertEquals(products, new HashMap<String, Integer>()
        {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Coke Zero", 3);
            put("Fanta", 4);
            put("Sprite", 6);
        }});
    }

    @Test
    public void testServerStaysUp() throws IOException, EnglishDeserializationError {
        // Given that I am using a free TCP port
        Integer port = getFreeTCPPort();
        // Given that I have the TCP server running in background that port
        startServer(port);
        // And that I have a TCP client connected to the server on that port
        VendingMachineClient client = new VendingMachineClient("localhost", port);

        // When I call askForProducts
        Map<String, Integer> products1 = client.askForProducts();
        Map<String, Integer> products2 = client.askForProducts();

        // Then it should return the expected list of products
        assertEquals(products1, new HashMap<String, Integer>()
        {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Coke Zero", 3);
            put("Fanta", 4);
            put("Sprite", 6);
        }});
        assertEquals(products1, products2);

    }
}
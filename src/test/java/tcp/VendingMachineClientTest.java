package tcp;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VendingMachineClientTest {


    public void startServer(Integer port) throws IOException {
        // Background: I have a Thread that
        Runnable task = () -> {
            VendingMachineServer server = new VendingMachineServer(port);

            try {
                server.handleRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(task);
        thread.start();

    }

    @Test
    public void testClientConnectsToServer() throws IOException {
        // Given that I have the TCP server running in background on port 8889
        startServer(8889);
        // And that I have a TCP client connected to the server on the port 8888
        VendingMachineClient client = new VendingMachineClient("localhost", 8889);

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
}
package tcp;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import parser.EnglishDeserializationError;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class VendingMachineClientTest {
    VendingMachineServerManager server = null;
    VendingMachineClient client = null;

    @Before
    public void setUp() throws IOException {
        // Given I have the TCP server running
        server = new VendingMachineServerManager();
        server.start();
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void testClientConnectsToServer() throws IOException, EnglishDeserializationError {
        // Given that I have a TCP client connected to the server
        VendingMachineClient client = new VendingMachineClient("localhost", server.getPort());

        // When I call askForProducts
        Map<String, Integer> products = client.askForProducts();

        // Then it should return the expected list of products
        assertEquals(products, new HashMap<String, Integer>() {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Coke Zero", 3);
            put("Fanta", 4);
            put("Sprite", 6);
        }});

        server.stop();
    }

    @Ignore
    @Test
    public void testServerAcceptsRequestToStop() throws IOException, EnglishDeserializationError {
        // Given that I have a TCP client connected to the server
        VendingMachineClient client = new VendingMachineClient("localhost", server.getPort());

        // When I call askForProducts
        Map<String, Integer> products1 = client.askForProducts();

        // Then it should return the expected list of products
        assertEquals(products1, new HashMap<String, Integer>() {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Coke Zero", 3);
            put("Fanta", 4);
            put("Sprite", 6);
        }});

        // When I ask the server to stop
        Boolean stopped = client.stopServer();
        // Then the server should have stopped
        assertTrue(stopped);
    }
}
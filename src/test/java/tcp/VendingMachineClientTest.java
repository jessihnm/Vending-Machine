package tcp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serialization.EnglishDeserializationError;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class VendingMachineClientTest {
    public static final String HOSTNAME = "127.0.0.1";
    VendingMachineServerManager server = null;
    VendingMachineClient client = null;

    @BeforeEach
    public void setUp() throws IOException {
        // Given I have the TCP server running
        server = new VendingMachineServerManager();
        server.start();
    }

    @AfterEach
    public void tearDown() {
        server.stop();
    }

    @Test
    public void testClientConnectsToServer() throws IOException, EnglishDeserializationError, VendingMachineClientDisconnected {
        // Given that I have a TCP client connected to the server
        VendingMachineClient client = new VendingMachineClient(HOSTNAME, server.getPort());

        // When I call askForProducts
        Map<String, Integer> products = client.askForProducts();

        // Then it should return the expected list of products
        assertThat(new HashMap<String, Integer>() {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Coke Zero", 3);
            put("Fanta", 4);
            put("Sprite", 6);
        }}, is(products));
    }

    @Test
    public void testServerAcceptsRequestToStop() throws IOException, EnglishDeserializationError, VendingMachineClientDisconnected {
        // Given that I have a TCP client connected to the server
        VendingMachineClient client = new VendingMachineClient(HOSTNAME, server.getPort());

        // When I call askForProducts
        Map<String, Integer> products1 = client.askForProducts();

        // Then it should return the expected list of products
        assertThat(new HashMap<String, Integer>() {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Coke Zero", 3);
            put("Fanta", 4);
            put("Sprite", 6);
        }}, is(products1));

        // When I ask the server to stop
        Boolean stopped = client.stopServer();
        // Then the server should have stopped
        assertThat(stopped, is(true));

        VendingMachineClientDisconnected error = assertThrows(VendingMachineClientDisconnected.class, () -> client.askForProducts());
        assertThat(error.getMessage(), is("Client is not connected to " + client.getServerAddress()));
    }
}
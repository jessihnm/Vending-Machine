package tcp;

public class VendingMachineClientDisconnected extends Exception {

    public VendingMachineClientDisconnected(VendingMachineClient client) {
        super("Client is not connected to " + client.getServerAddress());
    }
}

package tcp;

import java.io.IOException;

public class TCPServerError extends IOException {

    public TCPServerError(String message) {
        super(message);
    }
}

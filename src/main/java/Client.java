import presentation.LoginView;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket = null;
        try {
           socket = new Socket("localhost", 1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginView login = new LoginView(socket);
        login.init();
    }
}
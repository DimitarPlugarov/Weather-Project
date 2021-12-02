import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket server = new Socket("127.0.0.1", 1211);
        BufferedReader input = new BufferedReader(new InputStreamReader(server.getInputStream()));
        String response;
        response=input.readLine();
        System.out.println(response);
    }
}

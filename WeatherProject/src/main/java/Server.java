import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        Weather.hourlyCheck();//Starts hourly weather update
        ServerSocket server = new ServerSocket(1211);
        while (true){
            Socket client = server.accept();
            new ClientThread(client).start();
        }
    }
}

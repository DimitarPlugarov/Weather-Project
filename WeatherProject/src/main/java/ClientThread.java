import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket client;
    private PrintStream output;
    String weatherReport;
    public ClientThread(Socket client){
        this.client=client;
    }

    @Override
    public void run(){
        try {
            output = new PrintStream(client.getOutputStream());
            weatherReport=Weather.weather;
        } catch (IOException e) {
            e.printStackTrace();
        }
        output.println(weatherReport);
    }
}

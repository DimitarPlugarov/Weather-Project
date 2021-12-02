import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket client;
    private PrintStream output;
    public ClientThread(Socket client){
        this.client=client;
    }

    @Override
    public void run(){
        try {
            output = new PrintStream(client.getOutputStream());
            output.println(Weather.weather);
            output.println(Weather.getLatLong("latitude"));
            output.println(Weather.getLatLong("longitude"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(output!=null) output.close();
                if(client!=null) client.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }
}

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket server = new Socket("localhost", 1211);
        BufferedReader input=new BufferedReader(new InputStreamReader(server.getInputStream()));
        try {
            System.out.println(input.readLine());
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new  URI("https://www.google.com/maps/@"+input.readLine()+ ","+input.readLine()+",15z"));
            input.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (URISyntaxException e) {
            System.out.println("URIException");
            e.printStackTrace();
        }
    }
}

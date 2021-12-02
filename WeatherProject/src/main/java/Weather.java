import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Weather {
    static String weather;

    static Runnable updateWeather = () -> {
        try {
            getWeather();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    };
    static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void hourlyCheck(){//Updates weather every hour
        executor.scheduleAtFixedRate(updateWeather, 0, 1, TimeUnit.HOURS);
    }

    static String getIP() throws IOException {
        //retrieves host IP which is later used to determine location
        String myIP = "";
        BufferedReader in = null;
        try {
            URL whatIsMyIP = new URL("http://checkip.amazonaws.com");
            in = new BufferedReader(new InputStreamReader(whatIsMyIP.openStream()));
            myIP = in.readLine();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return myIP;
    }

    static String getLocation() throws IOException {
        //Uses host IP to determine city
        String city="";
        String url = "https://tools.keycdn.com/geo.json?host="+ getIP();
        CloseableHttpClient client = HttpClients.custom().build();
        HttpUriRequest request = RequestBuilder.get()
                .setUri(url)
                .setHeader("User-Agent","keycdn-tools:https://tu-sofia.bg")
                .build();
        CloseableHttpResponse response;

        try{
            response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String entityString = EntityUtils.toString(entity);
            city = new JSONObject(entityString).getJSONObject("data").getJSONObject("geo").getString("city");
        }catch (IOException ioe){
            ioe.getStackTrace();
        }
        return city;
    }

    public static void getWeather() throws IOException {
        //retrieves weather information in host city
        double temperature=0.0;
        final String APIKey = "0e1fcc92129037c8cdd641d67a22825d";
        String cityName = getLocation();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName +"&units=metric&appid=" + APIKey;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response;

        try{
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String entityString = EntityUtils.toString(entity);
            temperature = new JSONObject(entityString).getJSONObject("main").getDouble("temp");
        }
        catch (IOException ioe){
            System.out.println("IOException");
            ioe.getStackTrace();
        }
        weather = "The temperature in "+cityName +" is: " +temperature+"°C";
    }

}

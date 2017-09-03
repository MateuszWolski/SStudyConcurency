import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class WeatherClient {
    private static final String APP_ID = "appid=125b6505debc760f4994ee6b931bb62c";
    private static final String ROOT_API = "http://api.openweathermap.org/data/2.5/weather";
    private static final String ID = "id";

    public Optional<String> checkWeatherByCity(Cities city) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(createUrl(city));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException(String.format("Failes %s", connection.getResponseMessage()));
            }

            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            return Optional.of(readResponse(inputStreamReader));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return Optional.empty();
    }

    private String createUrl(Cities cities) {
        return String.format("%s?%s=%s&%s", ROOT_API, ID, cities.getValue(), APP_ID);
    }

    private String readResponse(InputStreamReader input) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(input);
        StringBuilder sb = new StringBuilder();
        String output;
        String result;
        while ((output = bufferedReader.readLine()) != null) {
            sb.append(output).append("/n");
        }
        return sb.toString();
    }
}

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class ParserTest {
    WeatherClient weatherClient = new WeatherClient();
    Parser parser = new Parser();

    @Test
    public void shouldParseWeatherResponse() {
        Optional<String> result = weatherClient.checkWeatherByCity(Cities.WARSAW);
        Optional<OpenWeatherResponse> openWeatherResponse = parser.parseResponse(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertTrue(openWeatherResponse.isPresent());
    }
}

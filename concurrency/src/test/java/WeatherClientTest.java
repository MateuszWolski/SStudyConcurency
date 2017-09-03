import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class WeatherClientTest {
    WeatherClient weatherClient  = new WeatherClient();
    @Test
    public void weatherInWarsawTest(){
        Optional<String> result  = weatherClient.checkWeatherByCity(Cities.WARSAW);
        Assertions.assertTrue(result.isPresent());
    }

}

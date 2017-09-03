import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

public class Parser {

    public Optional<OpenWeatherResponse> parseResponse(Optional<String> response) {
        ObjectMapper mapper = new ObjectMapper();
        Optional<OpenWeatherResponse> openWeatherResponse = Optional.empty();
        try {
            if (response.isPresent()) {
                openWeatherResponse = Optional.of(mapper.readValue(response.get(), OpenWeatherResponse.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return openWeatherResponse;
    }
}

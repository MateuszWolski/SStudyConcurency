import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {
    private Coordinations coord;
    private Weather main;
    private Wind wind;
    private String name;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordinations getCoord() {
        return coord;
    }

    public void setCoord(Coordinations coord) {
        this.coord = coord;
    }

    public Weather getMain() {
        return main;
    }

    public void setMain(Weather main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return String.format("City: %s, temp: %s", name, main.getTemp());
    }
}

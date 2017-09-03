import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class SimlpeTest {

    WeatherClient weatherClient = new WeatherClient();
    Parser parser = new Parser();

    @Test
    public void test1() {
        Runnable task = () -> {
            String thread = Thread.currentThread().getName();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("Thread name: %s", thread));
        };

        task.run();

        Thread thread = new Thread(task);
        thread.start();
    }

    @Test
    public void test2() {

        Runnable task1 = () -> {
            String thread = Cities.WARSAW.toString();
            System.out.println(String.format("Thread name: %s", thread));
            weatherClient.checkWeatherByCity(Cities.WARSAW);
        };

        Runnable task2 = () -> {
            String thread = Cities.LODZ.toString();
            System.out.println(String.format("Thread name: %s", thread));
            weatherClient.checkWeatherByCity(Cities.LODZ);
        };

        Thread thread1 = new Thread(task1);
        thread1.start();

        Thread thread2 = new Thread(task2);
        thread2.start();
    }


    @Test
    public void test3() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            String thread = Cities.WARSAW.toString();
            System.out.println(String.format("Thread name: %s", thread));
            weatherClient.checkWeatherByCity(Cities.WARSAW);
        });
        executor.submit(() -> {
            String thread = Cities.LODZ.toString();
            System.out.println(String.format("Thread name: %s", thread));
            weatherClient.checkWeatherByCity(Cities.LODZ);
        });
        System.out.println("Finish");
        executor.shutdown();
        System.out.println("Finished");
    }

    @Test
    public void test4() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            return getWeather(Cities.WARSAW);
        });
        Callable<Optional<OpenWeatherResponse>> task = () -> {
            return getOpenWeatherResponse(Cities.LODZ);
        };
        Future<Optional<OpenWeatherResponse>> future = executor.submit(task);

        System.out.println("future done? " + future.isDone());
        Optional<OpenWeatherResponse> result = future.get();// its blocking till result will occurred
        System.out.println("future done? " + future.isDone());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertTrue(result.get().getName().equalsIgnoreCase(Cities.LODZ.name()));
    }

    @Test
    public void shoulRunCallableList() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<Optional<OpenWeatherResponse>>> callables = Arrays.asList(
                () -> getOpenWeatherResponse(Cities.WARSAW),
                () -> getOpenWeatherResponse(Cities.LONDON),
                () -> getOpenWeatherResponse(Cities.BARCELONA),
                () -> getOpenWeatherResponse(Cities.LODZ));

        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
        System.out.println("finished all tasks");
    }

    @Test
    public void shouldRunEach1SecondAfterStart() throws InterruptedException, ExecutionException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
        
        List<Callable<Optional<OpenWeatherResponse>>> callables = Arrays.asList(
                () -> getOpenWeatherResponse(Cities.WARSAW),
                () -> getOpenWeatherResponse(Cities.LONDON),
                () -> getOpenWeatherResponse(Cities.BARCELONA),
                () -> getOpenWeatherResponse(Cities.LODZ));

        System.out.println("Start:" + LocalDateTime.now().toLocalTime());
        ScheduledFuture<?> futureWarsaw = executor.schedule(callables.get(0), 1, TimeUnit.SECONDS);
        ScheduledFuture<?> futureLondon = executor.schedule(callables.get(0), 1, TimeUnit.SECONDS);
        ScheduledFuture<?> futureBarcelona = executor.schedule(callables.get(0), 1, TimeUnit.SECONDS);
        ScheduledFuture<?> futureLodz = executor.schedule(callables.get(0), 1, TimeUnit.SECONDS);
        futureWarsaw.get();
        futureLondon.get();
        futureBarcelona.get();
        futureLodz.get();
        System.out.println("Finish:" + LocalDateTime.now().toLocalTime());
        System.out.println("finished all tasks");
    }


    private Optional<OpenWeatherResponse> getOpenWeatherResponse(Cities city) {
        System.out.println(LocalDateTime.now().toLocalTime());
        String thread = city.toString();
        System.out.println(String.format("Thread name: %s", thread));
        return parser.parseResponse(weatherClient.checkWeatherByCity(city));
    }

    private Optional<String> getWeather(Cities city) {
        String thread = city.toString();
        System.out.println(String.format("Thread name: %s", thread));
        return weatherClient.checkWeatherByCity(city);
    }

}

import org.junit.jupiter.api.Test;

public class SimlpeTest {

    @Test
    public void test1(){
        Runnable task = () ->{
            String thread = Thread.currentThread().getName();
            System.out.println(String.format("Thread name: %s", thread));
        };

        task.run();
    }


}

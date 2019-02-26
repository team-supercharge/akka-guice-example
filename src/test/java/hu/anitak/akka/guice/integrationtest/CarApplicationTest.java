package hu.anitak.akka.guice.integrationtest;

import hu.anitak.akka.guice.CarApplication;
import hu.anitak.akka.guice.model.Car;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;

public class CarApplicationTest {

    @Test
    public void test() throws InterruptedException {
        CarApplication application = new CarApplicationTestModule().getInjector().getInstance(CarApplication.class);
        Thread.sleep(3000);
        CompletionStage resultStage = application.start(Arrays.asList("Ferrari"));
        resultStage.thenApplyAsync(result -> {
            if (result instanceof List && !((List) result).isEmpty() && ((List) result).get(0) instanceof Car) {
                List<Car> resultCars = (List<Car>) result;
                assertEquals("Ferrari", resultCars.get(0).getName());
            }

            return null;
        });
    }
}

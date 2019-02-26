package hu.anitak.akka.guice.unittest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import hu.anitak.akka.guice.actor.command.InsertCarCommand;
import hu.anitak.akka.guice.actor.command.SelectCarCommand;
import hu.anitak.akka.guice.model.Car;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.duration.Duration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CarManagerActorTest {
    private ActorSystem system;
    private TestKit testActor;
    private TestKit testCarRepositoryActor;

    @Inject
    @Named("car-manager-actor")
    private ActorRef testCarManagerActor;

    @Before
    public void setup() throws InterruptedException {
        system = ActorSystem.create();
        testActor = new TestKit(system);
        testCarRepositoryActor = new TestKit(system);
        new CarManagerActorTestModule(system).getInjector().injectMembers(this);
        Thread.sleep(3000);
        ActorRef carRepositoryActor = system.actorFor("user/car-manager-actor/car-repository-actor");
        carRepositoryActor.tell(testCarRepositoryActor.getRef(), testActor.getRef());
    }

    @After
    public void cleanUp() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testInsertCar() {
        Car carEntity = new Car("Renault");
        testCarManagerActor.tell(new InsertCarCommand(carEntity), testActor.getRef());
        testCarRepositoryActor.expectMsg("CarRepository with InsertCarCommand has been called");
    }

    @Test
    public void testSelectCars() {
        testCarManagerActor.tell(new SelectCarCommand(), testActor.getRef());
        testCarRepositoryActor.expectMsg("CarRepository with SelectCarCommand has been called");
        Object response = testActor.receiveOne(Duration.Inf());
        assertTrue(response instanceof List);
        assertEquals(2, ((List) response).size());
        Car car1 = (Car)((List) response).get(0);
        Car car2 = (Car)((List) response).get(1);
        assertEquals("Kia", car1.getName());
        assertEquals("Fiat", car2.getName());
    }
}

package hu.anitak.akka.guice;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import hu.anitak.akka.guice.actor.command.InsertCarCommand;
import hu.anitak.akka.guice.actor.command.SelectCarCommand;
import hu.anitak.akka.guice.model.Car;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class CarApplication {
    private final ActorSystem actorSystem;
    private final ActorRef carManagerActor;
    private final Timeout timeout;

    @Inject
    public CarApplication(ActorSystem actorSystem, @Named("car-manager-actor") ActorRef carManagerActor) {
        this.actorSystem = actorSystem;
        this.carManagerActor = carManagerActor;
        this.timeout = Timeout.apply(5, TimeUnit.SECONDS);
    }

    public CompletionStage start(List<String> carNames) {
        try {
            for (String carName : carNames) {
                Car carEntity = new Car(carName);
                carManagerActor.tell(new InsertCarCommand(carEntity), ActorRef.noSender());
            }

            return PatternsCS.ask(carManagerActor, new SelectCarCommand(), timeout);
        } finally {
            actorSystem.terminate();
        }
    }
}

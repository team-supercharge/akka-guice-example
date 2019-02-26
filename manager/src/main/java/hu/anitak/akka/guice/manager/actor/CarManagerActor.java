package hu.anitak.akka.guice.manager.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import hu.anitak.akka.guice.actor.command.InsertCarCommand;
import hu.anitak.akka.guice.actor.command.SelectCarCommand;
import hu.anitak.akka.guice.injection.ChildInjectionFactory;
import hu.anitak.akka.guice.injection.InjectedActorSupport;

import java.util.concurrent.TimeUnit;

public class CarManagerActor extends AbstractActor implements InjectedActorSupport {
    private final ActorRef carRepositoryActor;
    private final Timeout timeout;

    @Inject
    public CarManagerActor(@Named("car-repository-actor-factory") ChildInjectionFactory childFactory) {
        this.carRepositoryActor = injectedChild(childFactory, "car-repository-actor");
        this.timeout = Timeout.apply(5, TimeUnit.SECONDS);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(InsertCarCommand.class, command -> insertCar(command))
            .match(SelectCarCommand.class, command -> selectAllCars(getSender()))
            .build();
    }

    private void insertCar(InsertCarCommand command) {
        //do some business logic
        carRepositoryActor.tell(command, getSelf());
    }

    private void selectAllCars(ActorRef sender) {
        //do some business logic
        PatternsCS.ask(carRepositoryActor, new SelectCarCommand(), timeout)
            .thenAccept(dbResult -> sender.tell(dbResult, getSelf()));
    }

}

package hu.anitak.akka.guice.unittest;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import hu.anitak.akka.guice.actor.command.InsertCarCommand;
import hu.anitak.akka.guice.actor.command.SelectCarCommand;
import hu.anitak.akka.guice.model.Car;

import java.util.Arrays;

public class MockedCarRepositoryActor extends AbstractActor {
    private ActorRef testActor;

    @Override
    public Receive createReceive() {
        return initTestActor();
    }

    private Receive initTestActor() {
        return receiveBuilder()
            .match(ActorRef.class, actorRef -> {
                this.testActor = actorRef;
                getContext().become(receiveOriginalActorCommand());
            })
            .build();
    }

    private Receive receiveOriginalActorCommand() {
        return receiveBuilder()
            .match(InsertCarCommand.class, command ->
                testActor.tell("CarRepository with InsertCarCommand has been called", getSelf()))
            .match(SelectCarCommand.class, command -> {
                testActor.tell("CarRepository with SelectCarCommand has been called", getSelf());
                getSender().tell(Arrays.asList(new Car("Kia"), new Car("Fiat")), getSelf());
            })
            .build();
    }
}

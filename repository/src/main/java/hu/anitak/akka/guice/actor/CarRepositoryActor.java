package hu.anitak.akka.guice.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.google.inject.Inject;
import hu.anitak.akka.guice.actor.command.InsertCarCommand;
import hu.anitak.akka.guice.actor.command.SelectCarCommand;
import hu.anitak.akka.guice.repository.CarDatabaseService;

public class CarRepositoryActor extends AbstractActor {
    private final CarDatabaseService carDatabaseService;

    @Inject
    public CarRepositoryActor(CarDatabaseService carDatabaseService) {
        this.carDatabaseService = carDatabaseService;
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        this.carDatabaseService.connect();
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        this.carDatabaseService.close();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(InsertCarCommand.class, command -> insertCar(command, getSender()))
            .match(SelectCarCommand.class, command -> selectAllCars(getSender()))
            .build();
    }

    private void insertCar(InsertCarCommand command, ActorRef sender) {
        carDatabaseService.insertCar(command.getCarEntity());
        sender.tell("Success", getSelf());
    }

    private void selectAllCars(ActorRef sender) {
        sender.tell(carDatabaseService.selectCars(), getSelf());
    }
}

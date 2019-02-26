package hu.anitak.akka.guice.actor.command;

import hu.anitak.akka.guice.model.Car;

public class InsertCarCommand {
    private final Car carEntity;

    public InsertCarCommand(Car carEntity) {
        this.carEntity = carEntity;
    }

    public Car getCarEntity() {
        return carEntity;
    }
}

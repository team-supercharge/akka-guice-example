package hu.anitak.akka.guice.repository;

import hu.anitak.akka.guice.model.Car;

import java.util.ArrayList;
import java.util.List;

public final class CarDatabaseCassandraService implements CarDatabaseService {

    public void connect() {
        //implement cassandra connection
    }

    public void insertCar(Car carEntity) {
        //implement insert
    }

    public List<Car> selectCars() {
        //implement select
        return new ArrayList();
    }

    public void close() {
        //implement connection closing
    }

}

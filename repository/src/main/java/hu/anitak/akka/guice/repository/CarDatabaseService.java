package hu.anitak.akka.guice.repository;

import hu.anitak.akka.guice.model.Car;

import java.util.List;

public interface CarDatabaseService {

    void connect();

    void insertCar(Car carEntity);

    List<Car> selectCars();

    void close();

}

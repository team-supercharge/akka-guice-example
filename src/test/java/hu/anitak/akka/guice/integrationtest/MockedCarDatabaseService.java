package hu.anitak.akka.guice.integrationtest;

import hu.anitak.akka.guice.model.Car;
import hu.anitak.akka.guice.repository.CarDatabaseService;

import java.util.ArrayList;
import java.util.List;

public final class MockedCarDatabaseService implements CarDatabaseService {

    private List<Car> carList;

    public void connect() {
        carList = new ArrayList();
    }

    public void insertCar(Car carEntity) {
        carList.add(carEntity);
    }

    public List<Car> selectCars() {
        return carList;
    }

    public void close() {
        carList.clear();
    }
}

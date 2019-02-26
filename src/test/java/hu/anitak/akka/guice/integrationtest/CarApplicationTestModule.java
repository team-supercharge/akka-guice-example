package hu.anitak.akka.guice.integrationtest;

import akka.actor.ActorSystem;
import com.google.inject.Guice;
import hu.anitak.akka.guice.actor.CarRepositoryActor;
import hu.anitak.akka.guice.injection.AbstractActorProviderModule;
import hu.anitak.akka.guice.manager.actor.CarManagerActor;
import hu.anitak.akka.guice.repository.CarDatabaseService;

public final class CarApplicationTestModule extends AbstractActorProviderModule {

    private final ActorSystem actorSystem;

    public CarApplicationTestModule() {
        this.actorSystem = ActorSystem.create();
        injector = Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        bind(ActorSystem.class).toInstance(actorSystem);
        bind(CarDatabaseService.class).toInstance(new MockedCarDatabaseService());
        bindActor(actorSystem, CarManagerActor.class, "car-manager-actor");
        bindActorChildFactory(CarRepositoryActor.class, "car-repository-actor-factory");
    }
}

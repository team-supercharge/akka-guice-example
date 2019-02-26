package hu.anitak.akka.guice;

import akka.actor.ActorSystem;
import com.google.inject.Guice;
import hu.anitak.akka.guice.actor.CarRepositoryActor;
import hu.anitak.akka.guice.injection.AbstractActorProviderModule;
import hu.anitak.akka.guice.manager.actor.CarManagerActor;
import hu.anitak.akka.guice.repository.CarDatabaseCassandraService;
import hu.anitak.akka.guice.repository.CarDatabaseService;

public final class CarApplicationModule extends AbstractActorProviderModule {
    private final ActorSystem actorSystem;
    private static CarApplicationModule moduleInstance;

    private CarApplicationModule() {
        this.actorSystem = ActorSystem.create();
        injector = Guice.createInjector(this);
    }

    public static CarApplicationModule getModuleInstance() {
        if (moduleInstance == null) {
            moduleInstance = new CarApplicationModule();
        }
        return moduleInstance;
    }

    @Override
    protected void configure() {
        bind(ActorSystem.class).toInstance(actorSystem);
        bind(CarDatabaseService.class).toInstance(new CarDatabaseCassandraService());
        bindActor(actorSystem, CarManagerActor.class, "car-manager-actor");
        bindActorChildFactory(CarRepositoryActor.class, "car-repository-actor-factory");
    }
}

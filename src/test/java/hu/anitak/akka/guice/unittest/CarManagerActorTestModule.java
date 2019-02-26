package hu.anitak.akka.guice.unittest;

import akka.actor.ActorSystem;
import com.google.inject.Guice;
import hu.anitak.akka.guice.injection.AbstractActorProviderModule;
import hu.anitak.akka.guice.manager.actor.CarManagerActor;

public final class CarManagerActorTestModule extends AbstractActorProviderModule {
    private final ActorSystem actorSystem;

    public CarManagerActorTestModule(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
        injector = Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        bindActor(actorSystem, CarManagerActor.class, "car-manager-actor");
        bindActorChildFactory(MockedCarRepositoryActor.class, "car-repository-actor-factory");
    }
}

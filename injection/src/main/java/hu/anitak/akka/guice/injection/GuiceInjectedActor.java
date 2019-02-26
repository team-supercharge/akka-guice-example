package hu.anitak.akka.guice.injection;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import com.google.inject.Injector;

public class GuiceInjectedActor implements IndirectActorProducer {

    private final Injector injector;
    private final Class<? extends Actor> concreteActorClass;

    public GuiceInjectedActor(Injector injector, Class<? extends Actor> concreteActorClass) {
        this.injector = injector;
        this.concreteActorClass = concreteActorClass;
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return concreteActorClass;
    }

    @Override
    public Actor produce() {
        return injector.getInstance(concreteActorClass);
    }
}

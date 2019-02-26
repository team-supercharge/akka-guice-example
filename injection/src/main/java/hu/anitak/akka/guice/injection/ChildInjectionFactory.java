package hu.anitak.akka.guice.injection;

import akka.actor.Actor;

public class ChildInjectionFactory {
    private final Class<? extends Actor> childActorClass;

    public ChildInjectionFactory(Class<? extends Actor> childActorClass) {
        this.childActorClass = childActorClass;
    }

    public Class<? extends Actor> getChildActorClass() {
        return childActorClass;
    }
}

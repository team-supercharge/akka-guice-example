package hu.anitak.akka.guice.injection;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import java.util.function.Function;

public interface InjectedActorSupport {

    default ActorRef injectedChild(ChildInjectionFactory factory, String name, Function<Props, Props> props) {
        return ActorProviderModuleFactory.getInstance()
            .createInjectedActor(context(), name, factory.getChildActorClass(), props);
    }

    default ActorRef injectedChild(ChildInjectionFactory factory, String name) {
        return injectedChild(factory, name, Function.identity());
    }

    ActorContext context();
}

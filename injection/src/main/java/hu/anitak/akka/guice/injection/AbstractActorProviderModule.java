package hu.anitak.akka.guice.injection;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.util.Providers;
import javax.inject.Provider;
import java.util.function.Function;

public abstract class AbstractActorProviderModule extends AbstractModule {

    protected Injector injector;

    public AbstractActorProviderModule() {
        ActorProviderModuleFactory.registerInstance(this);
    }

    public Injector getInjector() {
        return injector;
    }

    public ActorRef createInjectedActor(ActorRefFactory parentContext, String name, Class<? extends Actor> actorClass,
                                        Function<Props, Props> props) {
        return new ActorRefProvider(parentContext, name, actorClass, props).get();
    }

    protected void bindActor(ActorSystem system, Class<? extends Actor> actorClass, String name) {
        bind(ActorRef.class)
            .annotatedWith(Names.named(name))
            .toProvider(Providers.guicify(new ActorRefProvider(system, name, actorClass)));
    }

    protected void bindActorChildFactory(Class<? extends Actor> childActorClass, String name) {
        bind(ChildInjectionFactory.class)
            .annotatedWith(Names.named(name))
            .toInstance(new ChildInjectionFactory(childActorClass));
    }

    private class ActorRefProvider implements Provider<ActorRef> {
        private final ActorRefFactory parentContext;
        private final String name;
        private final Class<? extends Actor> actorClass;
        private final Function<Props, Props> props;

        ActorRefProvider(ActorRefFactory parentContext, String name, Class<? extends Actor> actorClass) {
            this(parentContext, name, actorClass, Function.identity());
        }

        ActorRefProvider(ActorRefFactory parentContext, String name, Class<? extends Actor> actorClass,
                         Function<Props, Props> props) {
            this.name = name;
            this.actorClass = actorClass;
            this.parentContext = parentContext;
            this.props = props;
        }

        @Override
        public ActorRef get() {
            Props appliedProps = props.apply(Props.create(GuiceInjectedActor.class, injector, actorClass));
            return parentContext.actorOf(appliedProps, name);
        }
    }
}

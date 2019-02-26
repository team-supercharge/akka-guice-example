package hu.anitak.akka.guice.injection;

public final class ActorProviderModuleFactory {

    private static AbstractActorProviderModule instance;

    private ActorProviderModuleFactory() {
        //factory class
    }

    public static <T extends AbstractActorProviderModule> void registerInstance(T concreteInstance) {
        instance = concreteInstance;
    }

    public static AbstractActorProviderModule getInstance() {
        return instance;
    }
}

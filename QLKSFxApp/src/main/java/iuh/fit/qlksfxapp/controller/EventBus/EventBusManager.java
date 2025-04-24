package iuh.fit.qlksfxapp.controller.EventBus;

import com.google.common.eventbus.EventBus;

public class EventBusManager {
    private static final EventBus eventBus = new EventBus();

    private EventBusManager() {}

    public static EventBus getInstance() {
        return eventBus;
    }

    public static void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    public static void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    public static void post(Object event) {
        eventBus.post(event);
    }
}
package org.alienideology.jcord.internal.event;

import org.alienideology.jcord.command.CommandFramework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * EventManager - The manager of DispatcherAdaptors, EvenSubscribers and CommandFrameworks
 * @author AlienIdeology
 */
public class EventManager {

    private List<DispatcherAdaptor> dispatchers = new ArrayList<>();

    private List<Object> subscribers = new ArrayList<>();
    private HashMap<Class<? extends Event>, ObjectContainer> methods = new HashMap<>();

    private List<CommandFramework> frameworks = new ArrayList<>();

    /**
     * Register objects that extend DispatcherAdaptor, used to perform actions when a event is fired.
     * @see #registerCommandFrameworks(CommandFramework...) for native command framework support.
     * @param dispatchers The dispatchers to register
     * @return EventManager for chaining.
     */
    public EventManager registerDispatcherAdaptors(DispatcherAdaptor... dispatchers) {
        this.dispatchers.addAll(Arrays.asList(dispatchers));
        return this;
    }

    /**
     * Register objects that have methods annotated as EventSubscriber
     * @param subscribers The subscriber object
     * @return EventManager for chaining.
     */
    @SuppressWarnings("unchecked")
    public EventManager registerEventSubscribers(Object... subscribers) {
        this.subscribers.addAll(Arrays.asList(subscribers));
        for (Object obj : subscribers) {
            for (Method method : obj.getClass().getMethods()) {
                if (method.isAnnotationPresent(EventSubscriber.class)) {
                    Class<?>[] parameters = method.getParameterTypes();

                    if (parameters.length == 1 && Event.class.isAssignableFrom(parameters[0])) {
                        Class<? extends Event> event = (Class<? extends Event>) parameters[0];
                        methods.put(event, new ObjectContainer(obj, method));
                    }
                }
            }
        }
        return this;
    }

    /**
     * Register Native CommandFramework.
     * It is recommended to only register one command framework.
     * @param frameworks The frameworks to register
     * @return EventManager for chaining.
     */
    public EventManager registerCommandFrameworks(CommandFramework... frameworks) {
        for (CommandFramework framework : frameworks) {
            this.dispatchers.add(framework.getDispatcher());
            this.frameworks.add(framework);
        }
        return this;
    }

    public List<DispatcherAdaptor> getDispatcherAdaptors() {
        return dispatchers;
    }

    public List<Object> getEventSubscribers() {
        return subscribers;
    }

    public List<CommandFramework> getCommandFrameworks() {
        return frameworks;
    }

    @SuppressWarnings("unchecked")
    public void onEvent(Event event) {
        dispatchers.forEach(d -> d.dispatchEvent(event));

        Class<? extends Event> eventClass = event.getClass();
        for (;;) {
            for (Class<? extends Event> param : methods.keySet()) {

                if (param.isAssignableFrom(eventClass)) {
                    try {
                        methods.get(param).method.invoke(methods.get(param).object, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            eventClass = (Class<? extends Event>) eventClass.getSuperclass();
            if (eventClass == Event.class) break;
        }

    }

    private class ObjectContainer {
        public Object object;
        public Method method;

        ObjectContainer(Object object, Method method) {
            this.object = object;
            this.method = method;
        }
    }

}

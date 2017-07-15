package org.alienideology.jcord.event;

import net.jodah.typetools.TypeResolver;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.bot.command.CommandFramework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * EventManager - The managers of DispatcherAdaptors, EvenSubscribers and CommandFrameworks
 * @author AlienIdeology
 */
public class EventManager {
    private Identity identity;

    private List<DispatcherAdaptor> dispatchers = new ArrayList<>();

    private List<Object> subscribers = new ArrayList<>();
    private ConcurrentHashMap<Method, MethodContainer> methods = new ConcurrentHashMap<>();

    private List<CommandFramework> frameworks = new ArrayList<>();

    /**
     * Register objects that extend DispatcherAdaptor, used to perform actions when a event is fired.
     *
     * @see #registerCommandFrameworks(CommandFramework...) for native command framework support.
     * @param dispatchers The dispatchers to register
     * @return EventManager for chaining.
     */
    public EventManager registerDispatcherAdaptors(DispatcherAdaptor... dispatchers) {
        this.dispatchers.addAll(Arrays.asList(dispatchers));
        return this;
    }

    /**
     * Deregister DispatcherAdaptors.
     *
     * @param dispatchers The dispatchers to deregister.
     * @return EventManager for chaining.
     */
    public EventManager deregisterDispatcherAdaptors(DispatcherAdaptor... dispatchers) {
        this.dispatchers.removeAll(Arrays.asList(dispatchers));
        return this;
    }

    /**
     * Register objects that have methods annotated as EventSubscriber.
     *
     * @param subscribers The subscribers object
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
                        methods.put(method, new MethodContainer(obj, event));
                    }
                }
            }
        }
        return this;
    }

    /**
     * Deregister CommandFramework.
     *
     * @param subscribers The subscribers to deregister.
     * @return EventManager for chaining.
     */
    @SuppressWarnings("unchecked")
    public EventManager deregisterEventSubscribers(Object... subscribers) {
        this.subscribers.removeAll(Arrays.asList(subscribers));
        for (Object obj : subscribers) {
            for (Method method : obj.getClass().getMethods()) {
                if (method.isAnnotationPresent(EventSubscriber.class)) {
                    Class<?>[] parameters = method.getParameterTypes();

                    if (parameters.length == 1 && Event.class.isAssignableFrom(parameters[0])) {
                        methods.remove(method);
                    }
                }
            }
        }
        return this;
    }

    /**
     * Register Native CommandFramework.
     * It is recommended to only register one command framework.
     *
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

    /**
     * Deregister CommandFramework.
     *
     * @param frameworks The frameworks to deregister.
     * @return EventManager for chaining.
     */
    public EventManager deregisterCommandFrameworks(CommandFramework... frameworks) {
        this.dispatchers.removeAll(Arrays.stream(frameworks).map(CommandFramework::getDispatcher).collect(Collectors.toList()));
        this.frameworks.removeAll(Arrays.asList(frameworks));
        return this;
    }

    /**
     * Check if an object is registered either as a {@link DispatcherAdaptor}, EventSubscriber,
     * or {@link CommandFramework}.
     *
     * @param object The object to check with.
     * @return returns true if the object is registered, false otherwise.
     */
    public boolean isRegistered(Object object) {
        return (object instanceof DispatcherAdaptor && dispatchers.contains(object)) ||
                subscribers.contains(object) ||
                (object instanceof CommandFramework && frameworks.contains(object));
    }

    /**
     * Get the dispatcher adaptors registered.
     *
     * @return A list of adaptors.
     */
    public List<DispatcherAdaptor> getDispatcherAdaptors() {
        return dispatchers;
    }

    /**
     * Get the event subscriber objects registered.
     *
     * @return A list of event subscribers.
     */
    public List<Object> getEventSubscribers() {
        return subscribers;
    }

    /**
     * Get the command frameworks registered.
     *
     * @return A list of command frameworks.
     */
    public List<CommandFramework> getCommandFrameworks() {
        return frameworks;
    }

    /**
     * Block the thread and wait for the next specified event.
     * The timeout is {@code 0}, which means the thread will be blocked forever if the result is never returned.
     *
     * @param filter The filter to specify which event to wait for.
     * @param <T> The event to return.
     * @return the event.
     */
    public <T extends Event> T waitForNext(Predicate<T> filter) {
        return waitForNext(filter, 0, null);
    }

    /**
     * Block the thread and wait for the next specified event.
     *
     * @param filter The filter to specify which event to wait for.
     * @param timeout The timeout in milliseconds. After the timeout, the thread will no longer be blocked, and the result will return {@code null}.
     * @param <T> The event to return.
     * @return the event.
     */
    public <T extends Event> T waitForNext(Predicate<T> filter, long timeout) {
        return waitForNext(filter, timeout, null);
    }

    /**
     * Block the thread and wait for the next specified event.
     *
     * @param filter The filter to specify which event to wait for.
     * @param timeout The timeout in milliseconds. After the timeout, the thread will no longer be blocked, and the result will return {@code null}.
     * @param onTimeout The actions to perform when timeout exceeds.
     * @param <T> The event to return.
     * @return the event, or null if there are no such event fired before the timeout.
     */
    public <T extends Event> T waitForNext(Predicate<T> filter, long timeout, Runnable onTimeout) {
        return new EventWaiter<>(filter, timeout, onTimeout).getBlocking();
    }

    /**
     * A simple Seekable implementation for event waiting.
     *
     * @param <T> The event to return
     */
    private class EventWaiter <T extends Event> {
        private Object subscriber;

        private final Predicate<T> filter;
        private final long timeout;
        private final Runnable onTimeout;

        private T result;
        private AtomicBoolean isFinished = new AtomicBoolean(false);

        EventWaiter(Predicate<T> filter, long timeout, Runnable onTimeout) {
            this.filter = filter;
            this.timeout = timeout;
            this.onTimeout = onTimeout;
        }

        T getBlocking() {
            run();
            long time = 0L;
            while (!isFinished.get()) {
                if (time >= timeout && timeout > 0) {
                    deregisterEventSubscribers(subscribers);
                    if (onTimeout != null) onTimeout.run();
                    break;
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ignored) {
                }
                time += 20;
            }
            return result;
        }

        private void run() {
            Class<?> type = TypeResolver.resolveRawArgument(Predicate.class, filter.getClass());
            Thread thread = new Thread(() -> {
                subscriber = new Object() {
                    @EventSubscriber
                    public void onEvent(T event) {
                        if (type.isInstance(event) && filter.test(event)) {
                            deregisterEventSubscribers(this);
                            result = event;
                            isFinished.set(true);
                        }
                    }
                };
                registerEventSubscribers(subscriber);
            });
            thread.start();
        }
    }

    /**
     * Asynchronously wait for the specified event, and perform an action.
     * The timeout is {@code 0}, which means the action may not be cancelled if the event is never dispatched.
     *
     * @param filter The filter to specify which event to wait for.
     * @param action The consumer action.
     * @param <T> The event to filter.
     */
    public <T extends Event> void onNext(Predicate<T> filter, Consumer<T> action) {
        onNext(filter, action, 0, null);
    }

    /**
     * Asynchronously wait for the specified event, and perform an action.
     * The timeout is {@code 0}, which means the action may not be performed if the event is never dispatched.
     *
     * @param filter The filter to specify which event to wait for.
     * @param action The consumer action.
     * @param timeout The timeout in milliseconds. After the timeout, the action will not be performed.
     * @param <T> The event to filter.
     */
    public <T extends Event> void onNext(Predicate<T> filter, Consumer<T> action, long timeout) {
        onNext(filter, action, timeout, null);
    }

    /**
     * Asynchronously wait for the specified event, and perform an action.
     *
     * @param filter The filter to specify which event to wait for.
     * @param action The consumer action.
     * @param timeout The timeout in milliseconds. After the timeout, the action will not be performed.
     * @param onTimeout The actions to perform if timeout exceeds.
     * @param <T> The event to filter.
     */
    public <T extends Event> void onNext(Predicate<T> filter, Consumer<T> action, long timeout, Runnable onTimeout) {
        Class<?> type = TypeResolver.resolveRawArgument(Predicate.class, filter.getClass());

        /* Event Subscriber */
        Object subscriber = new Object() {
            @EventSubscriber
            public void onEvent(T event) {
                if (type.isInstance(event) && filter.test(event)) {
                    action.accept(event);
                    // Deregistering Event Subscribers result in ConcurrentModificationException
                    // So we use a ConcurrentHashMap
                    deregisterEventSubscribers(this);
                }
            }
        };

        /* Run Asynchronously */
        Thread thread = new Thread(() -> {
            registerEventSubscribers(subscriber);
            long time = 0L;
            while (isRegistered(subscriber)) {
                if (time >= timeout && timeout > 0) {
                    deregisterEventSubscribers(subscriber);
                    if (onTimeout != null) onTimeout.run();
                    break;
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ignored) {}
                time += 20;
            }
        });
        thread.run();
    }

    @SuppressWarnings("unchecked")
    public void dispatchEvent(Event event) {
        /* Dispatcher Adaptors */
        // This includes the command frameworks
        dispatchers.forEach(d -> d.dispatchEvent(event));

        /* Event Subscribers */
        // Event class
        Class<? extends Event> eventClass = event.getClass();

        // Continue until the super class is the object class
        while (eventClass.getSuperclass() != null) {
            for (Method method : methods.keySet()) {
                MethodContainer oc = methods.get(method);

                if (oc.event.isAssignableFrom(eventClass)) {
                    try {
                        method.invoke(oc.object, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            eventClass = (Class<? extends Event>) eventClass.getSuperclass();
        }

    }

    /**
     * A container for Event Subscriber methods
     */
    private class MethodContainer {
        public Object object;
        public Class<? extends Event> event;

        MethodContainer(Object object, Class<? extends Event> event) {
            this.object = object;
            this.event = event;
        }
    }

    public EventManager setIdentity(Identity identity) {
        this.identity = identity;
        return this;
    }

}

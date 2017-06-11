package org.alienideology.jcord.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @author AlienIdeology
 */
public class Seekable<V> {

    private Callable<V> callable;
    private V result;
    private boolean isFinished;
    private List<Thread> threads = new ArrayList<>();

    public Seekable(Callable<V> callable) {
        this.callable = callable;
    }
    
    public V getBlocking() {
        run();
        while(!isFinished) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {}
        }
        return result;
    }

    public void getAsync(Consumer<V> action) {
        getAsync(action, Throwable::printStackTrace);
    }

    public void getAsync(Consumer<V> action, Consumer<Throwable> failure) {
        Thread thread = new Thread(() -> {
            try {
                result = callable.call();
                action.accept(result);
            } catch (Exception e) {
                failure.accept(e);
                throw new RuntimeException(e);
            }
            isFinished = true;
        });
        thread.start();
        threads.add(thread);
    }

    public void cancel() {
        threads.forEach(Thread::interrupt);
    }

    public Callable<V> getCallable() {
        return callable;
    }

    public V getResult() {
        return result;
    }

    public boolean isFinished() {
        return isFinished;
    }

    private void run() {
        Thread thread = new Thread(() -> {
            try {
                result = callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            isFinished = true;
        });
        thread.start();
        threads.add(thread);
    }

}

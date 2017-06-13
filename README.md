# J-Cord
~~Another~~ A Discord API Wrapper for Java <br />

## Features

- Events Handling and Dispatching
- Native CommandFramework support
- Native Emoji Support

## How to Use

- Build an Identity
```java
    Identity bot = new IdentityBuilder()
        .setIdentityType(IdentityType.BOT)
        .useToken(Token.TOP_SECRET)
        .setEventManager(
            new EventManager().registerDispatchers(new ExampleDispatcher()))
        .build(true);
```
- EventManager (Ways to subscribe to events):
 1. Use `DispatcherAdaptor`: <br />
    Objects registered as adaptors must extends DispatcherAdaptor. <br />
    DispatcherAdaptor example:
    ```java
        public class ExampleDispatcher extends DispatcherAdaptor {
            @Override
            public void onMessageCreate (MessageCreateEvent event) {
                if (event.getMessage().getContent().startsWith("?") { // Prefix
                    // Do stuff
                }
            }
        }
    ```
    <br /> When building identity:
    ```java
        new EventManager().registerDispatchers(
            new ExampleDispatcher())
        )
    ```
 2. Use `EventSubscriber` annotation: <br />
    Register objects that have methods annotates as EventSubscriber. <br />
    EventSubscriber example:
    ```java
        public class ExampleSubscriber {
            @EventSubscriber
            public void onMessageCreateEvent(MessageCreateEvent event) {
                System.out.println(event.getChannel());
            }
        }
    ```
    <br /> When building identity:
    ```java
        new EventManager().registerEventSubscriber(new ExampleSubscriber())
    ```
- CommandFramework
 1. Create classes that implements CommandResponder (Empty interface)
 2. Annotate methods as @Command
    ```java
        public class ExampleResponder {
            @Command (aliases = {"ping", "pong", "thump"}
            public String onPingCommand (String[] args, MessageCreateEvent event) {
                return event.getUser().mention()+" pong!";
            }
        }
    ```
 3. Register CommandResponders in a CommandFramework
 4. Register the CommandFramework in EventManager
    ```java
        new EventManager().registerCommandFrameworks(
            new CommandFramework()
                .setPrefixes("=").registerCommandResponder(new ExampleResponder())
        )
    ```

## More

- For seeing into the future, read [Todo.md](/docs/Todo.md)
- Also read [Object_Hierarchy.md](/docs/Object_Hierarchy.md)
for more information about this project's structure.
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
        new EventManager().registerDispatchers(
            new ExampleDispatcher())
        )
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
    When building identity:
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
    When building identity:
    ```java
    new EventManager().registerEventSubscriber(
        new ExampleSubscriber()
    )
    ```
- CommandFramework
 1. Create classes that implements CommandResponder (Empty interface)
 2. Annotate methods as @Command
    ```java
    public class ExampleResponder implements CommandResponder {
        @Command (aliases = {"ping", "pong", "thump"})
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
            .setPrefixes("=").registerCommandResponder(
                new ExampleResponder()
            )
    )
    ```
- PostAgent (Post bot status to websites): <br />
 1. Create a `Bot` object
    ```java
    // Build Identity
    Bot bot = new Bot(identity);
    PostAgent agent = bot.getPostAgent();
    ```
 2. Post status (Can chain methods)
    1. DiscordBots
        ```java
        agent.toDiscordBots(YOUR_API_TOKEN);
        ```
    2. Discord Bot List
        ```java
        agent.toDiscordBotList(YOUR_API_TOKEN);
        ```
    3. Discord List Bots
        ```java
        agent.toDiscordListBots(YOUR_API_TOKEN);
        ```
    Note: You must create an account and get a token from one of the websites first!

## More
- Javadocs: [AlienIdeology.Github.io/J-Cord/](https://alienideology.github.io/J-Cord/)
- For seeing into the future, read [Todo.md](/docs/Todo.md)
- Also read [Object_Hierarchy.md](/docs/Object_Hierarchy.md)
for more information about this project's structure.
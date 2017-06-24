# J-Cord
~~Another~~ A Discord API Wrapper for Java <br />

[![](https://jitpack.io/v/AlienIdeology/J-Cord.svg)](https://jitpack.io/#AlienIdeology/J-Cord)

## Features
- Events Handling and Dispatching
- CommandFramework Support
- Emoji Support

## Download
- Jar (Coming soon)
- Gradle (In your `build.gradle` file)
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile 'com.github.AlienIdeology:J-Cord:-SNAPSHOT'
}
```
- Maven (In your `pom.xml` file)
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.AlienIdeology</groupId>
    <artifactId>J-Cord</artifactId>
    <version>-SNAPSHOT</version>
</dependency>
```

## How to Use

### Bot & Selfbot
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
- PostAgent (Post bot status to websites):
 - Setting up
    1. [DiscordBots](https://bots.discord.pw/)
        ```java
        PostAgent DiscordBotsAgent = PostAgent.DISCORD_BOTS
            .setIdentity(identity) // Set the Identity object, which is used to post shard and guild count
            .setAPIToken(YOUR_TOKEN_HERE) // The token for Discord Bots API
            .post(); // Post the status
        ```
    2. [Discord Bot List](https://discordbots.org/)
        ```java
        PostAgent DiscordBotsAgent = PostAgent.DISCORD_BOT_LIST
            .setIdentity(identity)
            .setAPIToken(YOUR_TOKEN_HERE) // The token for Discord Bot List API
            .post(); // Post the status
        ```
    3. [Discord List Bots](https://bots.discordlist.net/)
        ```java
        PostAgent DiscordBotsAgent = PostAgent.DISCORD_LIST
                .setIdentity(identity)
                .addPostField("token", YOUR_TOKEN_HERE) // The token for Discord List API
                .post(); // Post the status
        ```
 - Post Automatically <br />
    After setting up the post agent, you can add it to the `Bot`:
    ```java
    // Build Identity
    identity.getAsBot().addPostAgent(agent);
    ```
    The bot status will automatically be posted on GuildCreateEvent, GuildUnavailableEvent, and GuildDeleteEvent.


For more examples, see [ExampleBot.java](/src/test/java/ExampleBot.java).
### OAuth
Coming soon~~ <br />
### Webhook
Coming soon~~ <br />

## More
- Javadocs: [AlienIdeology.Github.io/J-Cord/](https://alienideology.github.io/J-Cord/)
- For seeing into the future, read [Todo.md](/docs/Todo.md)
- Also read [Object_Hierarchy.md](/docs/Object_Hierarchy.md)
for more information about this project's structure.
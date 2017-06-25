# J-Cord
[![](https://jitpack.io/v/AlienIdeology/J-Cord.svg)](https://jitpack.io/#AlienIdeology/J-Cord)
<br />
~~Another~~ A Discord API Wrapper for Java

## Features
- Builders and managers to makes managing and creating objects easier.
- Multiple choices for receiving events.
- Built in, easy to use Command Framework via Reflection.
- Get emojis by aliases use in Discord. No more external emoji dependencies.
- Post bot status to bot listing websites automatically.
- ~~Built in support for Webhooks and OAuth~~ Soon™

## Download
- Jar (Soon™)
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

## Support
[Discord Support Server](https://discord.gg/6UDkwb4)

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
  - Setting Up the Agent
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
    4. Custom website
        ```java
        PostAgent DiscordBotsAgent = new PostAgent(identity)
                .setAPIName("Discord Bla Bla Bla Bots Bla Bla Bla List") // Isn't the name typically like that?
                .setPostUrl("same_api_endpoint") // An API EndPoint URL
                .setJsonShardIDKey("shard_id") // The json field for shard ID (0 based)
                .setJsonShardKey("shad_count") // The json field for shard count
                .setJsonServerKey("server_count") // The server count
                .addPostField("some_json_key", "some_value") // Add whatever is required
                .post(); // Or you can use .post(Consumer<MultipartBody>) to add custom fields or headers, too
        ```
  - Post Automatically <br />
    After setting up the post agent, you can add it to the `Bot`:
    ```java
    // Build Identity
    identity.getAsBot().addPostAgent(agent);
    ```
    The bot status will automatically be posted on GuildCreateEvent, GuildUnavailableEvent, and GuildDeleteEvent.

For more examples, see this [example bot](/src/test/java/bot/).

## Contributing
Please fork this project, and read [Contribution Documentation](/docs/Contribution.md).

## External Dependencies
- [NV Websocket Client](https://github.com/TakahikoKawasaki/nv-websocket-client) `v2.2`
- [Unirest for Java](https://github.com/Mashape/unirest-java) `v1.4.9`
- [JSON-java](https://github.com/stleary/JSON-java) `v20160212`
- [Apache Commons Lang 3](https://commons.apache.org/proper/commons-lang/) `v3.4`
- [Apache Commons IO](https://commons.apache.org/proper/commons-io/) `2.5`

## More
- Javadocs: [AlienIdeology.Github.io/J-Cord/](https://alienideology.github.io/J-Cord/)
- For features coming soon, read [Todo.md](/docs/Todo.md)
- Also read [Object Hierarchy](/docs/Object_Hierarchy.md) and [Event Hierarchy](/docs/Event_Hierarchy.md)
for more information about this project's structure.
- Don't forget to check out the wiki section!
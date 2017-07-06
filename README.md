# J-Cord
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![](https://jitpack.io/v/AlienIdeology/J-Cord.svg)](https://jitpack.io/#AlienIdeology/J-Cord)
[![Build Status](https://travis-ci.org/AlienIdeology/J-Cord.svg?branch=master)](https://travis-ci.org/AlienIdeology/J-Cord)
[![Discord](https://discordapp.com/api/guilds/324381670100828181/widget.json)](https://discord.gg/6UDkwb4)
<br />
A Discord API Wrapper for Java

## Features
- Builders and managers to makes creating and managing discord objects easier.
- Multiple choices for receiving events.
- Easy to use command system via reflection.
- Post bot status to bot listing websites automatically.
- Built in support for Webhook and OAuth 2
- Get emojis by aliases use in Discord. No more external emoji dependencies.

## Download
- Jar - [See the latest release](https://github.com/AlienIdeology/J-Cord/releases/latest)
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
Note: Using `-SNAPSHOT` as the version will automatically be the latest commit from
branch `master`. To use the latest commit of other branches, use `BranchName-SNAPSHOT`.
You may also use a `release tag` or `commit hash` to specify the version you intend to use.

## Support
Ask questions in the [Discord Support Server](https://discord.gg/6UDkwb4)
or [open an issue](https://github.com/AlienIdeology/J-Cord/issues).

## How to Use

### Bot & Selfbot
#### Identity
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
#### Event
- Ways to subscribe to events: <br />
1. Use `DispatcherAdaptor` <br />
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
2. Use `EventSubscriber` annotation
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
- Wait for an Event
    - Asynchronously (Perform the consumer action when a specified event is dispatched before the timeout)
    ```java
    // Get the Event Manager
    eventManager.<MessageCreateEvent>onNext(
        event -> event.getUser().isSelf(), // Only get message create events that are from the identity
        event -> System.out.println("I just said: " + event.getMessage().getContent(), // Perform actions
        3000, // Timeouts in millisecond
        () -> System.out.println("Still no response after 30 seconds!") // Actions to perform if timeout exceeds
    );
    ```
    - Synchronously (Blocking the thread until event dispatched or timeout exceeds)
    ```java
    // Get the Event Manager
    MessageCreateEvent event = eventManager.waitForNext(
        event -> event.getUser().isSelf(), // Only get message create events that are from the identity
        3000, // Timeouts in millisecond
        () -> System.out.println("Still no response after 30 seconds!") // Actions to perform if timeout exceeds
    );

    System.out.println("I just said: " + event.getMessage().getContent()

    ```
#### Command System
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
#### PostAgent
 - Setting Up the Agent
   1. [DiscordBots](https://bots.discord.pw/)
    ```java
    PostAgent agent = PostAgent.DISCORD_BOTS
        .setIdentity(identity) // Set the Identity object, which is used to post shard and guild count
        .setAPIToken(YOUR_TOKEN_HERE) // The token for Discord Bots API
        .post(); // Post the status
    ```
   2. [Discord Bot List](https://discordbots.org/)
    ```java
    PostAgent agent = PostAgent.DISCORD_BOT_LIST
        .setIdentity(identity)
        .setAPIToken(YOUR_TOKEN_HERE) // The token for Discord Bot List API
        .post(); // Post the status
    ```
   3. [Discord List](https://bots.discordlist.net/)
    ```java
    PostAgent agent = PostAgent.DISCORD_LIST
        .setIdentity(identity)
        .addPostField("token", YOUR_TOKEN_HERE) // The token for Discord List API
        .post(); // Post the status
    ```
   4. Custom website
    ```java
    PostAgent agent = new PostAgent(identity)
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
    The bot status will automatically be posted on `GuildCreateEvent`, `GuildUnavailableEvent`, and `GuildDeleteEvent`.

### OAuth
- Using the `OAuthBuilder`
```java
OAuthBuilder builder = new OAuthBuilder()
    .setClientId(ID) // Application's ID
    .setClientSecret(SECRET) // Application's secret
    .setRedirectUrl(URL) // Used to redirect an user agent and provide authorization code for the oauth
    .setScopes(Scope.BOT, Scope.IDENTIFY);
```
- Get the Authorization URL for the application
```java
String authorizationUrl = builder.buildUrl();
```
- Build an `OAuth` instance
```java
OAuth application = builder
    .buildOAuth()
    .autoAuthorize(); // Automatically get the authorization code
```

### Emoji
- Using `EmojiTable`
```java
// Do not create a new instance of a emoji table
// Since it takes up so much resources
EmojiTable table = JCord.EMOJI_TABLE;
```
- Get an Emoji by an attribute:
```java
EmojiTable.Emoji emoji;

emoji = table.getByName("face with tears of joy");

emoji = table.getByKeyword("laugh");

emoji = table.getByAlias(":joy:");

emoji = table.getByUnicode("\uD83D\uDE02");

// All the methods above returns the emoji 😂
```
- Get Emojis:
```java
List<EmojiTable.Emoji> emojis;

emojis = table.getEmojis(); // Returns all emojis in Discord
emojis = table.getByCategory(EmojiTable.Category.PEOPLE); // Returns a list of emojis that falls into the people category
```

## Examples
- [Simple Bot](/src/test/java/bot/)
(With DispatcherAdaptor, EventSubscriber, and CommandFramework's examples)

## Contributing
Please fork this project, and read [Contribution Documentation](/docs/Contribution.md).

## Dependencies
- [NV Websocket Client](https://github.com/TakahikoKawasaki/nv-websocket-client) `v2.2`
- [NanoHTTPD](https://github.com/NanoHttpd/nanohttpd) `v2.2.0`
- [Unirest for Java](https://github.com/Mashape/unirest-java) `v1.4.9`
- [JSON-java](https://github.com/stleary/JSON-java) `v20160212`
- [Apache Commons Lang 3](https://commons.apache.org/proper/commons-lang/) `v3.4`
- [Apache Commons IO](https://commons.apache.org/proper/commons-io/) `2.5`
- [JetBrains Annotations](https://mvnrepository.com/artifact/org.jetbrains/annotations/) `v15.0`
- [TypeTools](https://github.com/jhalterman/typetools) `v0.5.0`

## More
- Javadocs: [AlienIdeology.Github.io/J-Cord/](https://alienideology.github.io/J-Cord/)
- Todo List: [Todo.md](/docs/Todo.md)
- Project structure:
  - [Object Hierarchy.md](/docs/Object_Hierarchy.md)
  - [Event Hierarchy.md](/docs/Event_Hierarchy.md)
- Don't forget to check out the [wiki section](https://github.com/AlienIdeology/J-Cord/wiki)!
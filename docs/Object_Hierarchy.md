# Object Hierarchy

## Identity
Identity - The identity of a bot (without shards), a shard, or a human (client).
<br />
IdentityBuilder - Used to set token, IdentityType, DispatcherAdaptors,
and build the Identity.
<br />
IdentityType (Enumeration) - The types of identities available for building.
 - Bot - A bot account (Can be either a not-sharded bot, a shard, or an application)
 - Human - A human account (Client side, selfbot)

## Bot
Bot - The core of a Discord Bot
 - BotInviteBuilder - A builder for building bot invite URL.
 - PostAgent - An agent used to post bot status to bots websites.

## Discord Objects
Discord Objects - Entities such as Guild, User, or Channel.
 - ISnowFake (Interface) - Objects that has ID.
 - IMention (Interface) - Objects that are mentionable.
 - IUser - A base entity, can be a member of guild/private channel, and bot/human.
 - Presence - A status of a user, containing information about the user online status and game.
   - Game - A playing or streaming status of a user.
   - OnlineStatus (Enumeration) - The online status of a user.
 - Permission (Enumeration) - A way to limit and grant certain abilities to members.
 - EmojiTable - A collection of Default Discord Emojis.
   - Emoji - An emoji in Discord (Not GuildEmoji).

#### Guild
IGuild - A collection of users and channels, often referred to in the UI as a server.
 - IMember - A user representation in a guild.
 - IRole - A label that can be put on a set of guild members.
 - IGuildEmoji - A custom emoji that can be used within a guild.
 - Region (Enumeration) - Used for guild voice channel connection.

#### Channel
IChannel - A communication pipeline.
 - IMessageChannel - A channel that allows users to send message.
   - ITextChannel - A GuildChannel for text messages. (GuildChannel)
   - IPrivateChannel - A one-to-one channel between two users.
   - MessageHistory - The history of a MessageChannel, used to get lists of messages.

 - IVoiceChannel - A GuildChannel for audio connections. (GuildChannel)
 - IGuildChannel (Interface) - A Channel that exist in a guild.

#### Message
IMessage - A text bubble some user just said.
 - IStringMessage - Normal messages that only contains string.
 - IEmbedMessage - Embed messages that can by sent by bot.
 - IReaction - A emoji that users reacted under a message.
 - Builders
   - MessageBuilder - Build a message, can be StringMessage or EmbedMessage.
   - EmbedMessageBuilder - Build a JSONObject of an embed, used to build
     embed messages and send in channels.

### Managers
 - IGuildManager - The manager that manages and perform actions upon a guild.
 - IMemberManager - The manager that manages and perform actions upon a member.
 - IRoleManager - A manager that manages a role in the guild.

## Event
Event - Whenever a change happens to an entity, an event get fired.
 - EventManager - The manager of DispatcherAdaptors, EvenSubscribers and CommandFrameworks.
   - DispatcherAdaptor - Event listener used to listen to events and perform actions.
   - EventSubscriber - An annotation used to annotate methods that subscribe an event.
   - CommandFramework - See Command section. <br />
See [Event_Hierarchy.md](/docs/Event_Hierarchy.md) for more information.

## Exception
 - ErrorResponseException - An exception for Json Error Responses.

## Command
 - Command (Annotation) -  A command annotation used on methods for the native command framework.
 - CommandFramework - The core command framework of J-Cord. Uses reflections to invoke methods.
 - CommandResponder (Interface) - All command classes must implements CommandResponder in order to be added to the framework.

## Gateway
 ~~Internal Objects~~
<br />
Gateway - The communication between J-Cord and Discord API.
 - GatewayAdaptor - Communication client for Discord GateWay.
 - OPCode (Enumeration) - OP Code sent by Discord GateWay server.
 - DisconnectionCode (Enumeration) - Disconnection Code sent when Discord gateway closed.
 - ErrorCode (Enumeration) - HTTP Response/Error Codes.
 - ErrorResponse (Enumeration) - Json Error Responses.
 - Requester - A Http Requester for HttpPath.
 - HttpPath (Static) - Used to set HttpRequest Paths and request them.
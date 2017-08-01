# Object Hierarchy

## Core

### Identity
 - JCord - Contains information about this library and useful constants.
 - Identity - The identity of a bot (without shards), a shard, or a human (client).
 - IdentityBuilder - Used to set token, IdentityType, DispatcherAdaptors, and build the Identity.
 - IdentityType (Enumeration) - The types of identities available for building.
   - Bot - A bot account (Can be either a not-sharded bot, a shard, or an application)
   - Client - A human account (Client side, selfbot)

### Bot
Bot - The core of a Discord Bot
 - Application - Information about a bot's oauth application.
 - BotInviteBuilder - A builder for building bot invite URL.
 - PostAgent - An agent used to post bot status to bots websites.

#### Command
 - Command (Annotation) -  A command annotation used on methods for the native command framework.
 - CommandFramework - The core command framework of J-Cord. Uses reflections to invoke methods.
 - CommandResponder - All command classes must implements CommandResponder in order to be added to the framework.

#### Emoji
 - Emojis - A collection of Default Discord Emojis.
   - Emoji - An unicode emoji in Discord.
   - EmojiCategory - Categories of emojis in the Discord client UI.

### Client
IClient - Represents a Discord client.
IClientObject - Generic Client Objects.
 - IProfile - The client user, with Discord Nitro and more information.
 - IClientSetting - he app settings of a Discord client.
 - IRelationship - A connection between two Discord user accounts.
 - IGuildSetting - A guild setting specifically for clients.
 - ITextChannelSetting - A channel notification setting for clients.
 - MessageNotification - The message notification level on the client side.

## Discord Objects
IDiscordObject - Entities such as Guild, User, or Channel.
 - ISnowFake  - Objects that has ID.
 - IMention - Objects that are mentionable.
 - IInvite - A piece of url used to invite users to a guild.
 - Icon - An encoded(base64) image, can be an avatar or picture.

### Audit Log
AuditLog - An audit log that belongs to a guild. Used to get log entries.
 - ILogEntry - An audit log entry, with information such as changes and options of an entry.
 - LogType (Enumeration) - Types of log entries.
 - LogOption (Enumeration) - Optional information that are only visible for certain {@link LogType}.
 - ILogChange - A change to a certain value. An audit log entry can contain multiple changes.
 - ChangeType (Enumeration) - Types of log changes.

### Guild
IGuild - A collection of users and channels, often referred to in the UI as a server.
 - IMember - A user representation in a guild.
 - IRole - A label that can be put on a set of guild members.
 - IGuildEmoji - A custom emoji that can be used within a guild.
 - Region (Enumeration) - Used for guild voice channel connection.

### Channel
IChannel - A communication pipeline.
 - IMessageChannel - A channel that allows users to send message.
   - ITextChannel - A GuildChannel for text messages. (GuildChannel)
   - IPrivateChannel - A one-to-one channel between two users.
   - MessageHistory - The history of a MessageChannel, used to get lists of messages.

 - IVoiceChannel - A GuildChannel for audio connections. (GuildChannel)
 - IGuildChannel (Interface) - A Channel that exist in a guild.

### Message
IMessage - A text with embeds or attachments that can be sent by anyone.
 - IEmbed - Embeds that can by sent by bot.
 - IReaction - A emoji that users reacted under a message.
 - MessageProcessor - The processor of a message. This can process the whole message, or act like a reader with a cursor.

### User
IUser - A base entity, can be a member of guild/private channel, and bot/human.
 - Presence - A status of a user, containing information about the user online status and game.
   - Game - A playing or streaming status of a user.
   - OnlineStatus (Enumeration) - The online status of a user.
 - IWebhook - A low effort way to send messages to channels.

### Permission
Permission (Enumeration) - A way to limit and grant certain abilities to members.
 - PermCheckable - An object that can be checked with permissions.
 - PermOverwrite - A channel setting that overwrite permissions of a guild member or role.
 - OverwriteCheckable - An object that can be checked with PermOverwrites.

### Managers
 - IGuildManager - The manager that manages and perform actions upon a guild.
 - IChannelManager - A manager for both text and voice channels.
 - IInviteManager - A manager for managing invites in both a guild or a guild channel.
 - IMemberManager - The manager that manages and perform actions upon a member.
 - IRoleManager - A manager that manages a role in the guild.
 - IWebhookManager - A manager for modifying, executing and deleting webhooks.
 - ISelfManager - A manager that manages self user.

### Builders
 - ChannelBuilder - A GuildChannel builder for creating text and voice channels.
 - RoleBuilder - A builder for creating a role in a guild.
 - MessageBuilder - Build a message.
 - EmbedMessageBuilder - Build a JSONObject of an embed, used to build
   embed messages and send in channels.

## Event
Event - Whenever a change happens to an entity, an event get fired.
 - EventManager - The manager of DispatcherAdaptors, EvenSubscribers and CommandFrameworks.
   - DispatcherAdaptor - Event listener used to listen to events and perform actions.
   - EventSubscriber (Annotation) - An annotation used to annotate methods that subscribe an event.
   - CommandFramework - See Command section. <br />
See [Event_Hierarchy.md](/docs/Event_Hierarchy.md) for more information.

## OAuth
OAuth - Used to build applications that utilize authentication and data from the Discord API.
 - OAuthBuilder - A builder for building instance of OAuth and OAuth authorization URL.
 - Scope - Provides access to certain resources of a user account.

## Utility
 - Cache - Cache objects into a customized list.
 - Seekable - Multi-threaded future.
 - DataUtils - Utilities for I/O and data.
 - Log
   - Logger - Official Logger for JCord.
   - LogMode - The logger mode, used to filter logs for the JCordLogger.
   - LogLevel - The logger level, used to indicate the log types.

## Internal

### Exception
 - HigherHierarchyException - When the identity tries to modify a member or role higher than it's hierarchy, this exception is fired.
 - PermissionException - When the identity request an action that it does not have permission to do so, a PermissionException is thrown.
 - RateLimitException - An exception for gateway rate limits.
 - ErrorResponseException - An exception for Json Error Responses.
 - HttpErrorException - An exception for Http Error Codes.
 - ScopeException - When the identity try to access a resource outside of its scopes.

### Gateway
Gateway - The communication between J-Cord and Discord API.
 - GatewayAdaptor - Communication client for Discord GateWay.
 - Requester - A Http Requester for HttpPath.
 - HttpPath (Static) - Used to set HttpRequest Paths and request them.
 - OPCode (Enumeration) - OP Code sent by Discord GateWay server.
 - DisconnectionCode (Enumeration) - Disconnection Code sent when Discord gateway closed.
 - ErrorCode (Enumeration) - HTTP Response/Error Codes.
 - ErrorResponse (Enumeration) - Json Error Responses.

### Object
The package contains implementations of the Discord objects.
 - Jsonable - Objects that can be serialized to json.
 - ObjectBuilder - A builder for building Discord objects from json.
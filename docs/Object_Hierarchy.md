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

## Discord Objects
Discord Objects - entities such as Guild, User, or Channel.
 - SnowFake (Interface) - Objects that has ID.
 - Mention (Interface) - Objects that are mentionable.
 - User - A base entity, can be a member of guild/private channel, and bot/human.
 - Region - Used for guild voice channel connection.
 - Permission - A way to limit and grant certain abilities to members.
 - Emojis - A list of Emoji objects. Used to get Emoji objects by alias or keyword.
 - Emoji - A emoji global default in Discord.

#### Guild
Guild - A collection of users and channels, often referred to in the UI as a server.
 - Member - A user representation in a guild.
 - Role - A label that can be put on a set of guild members.
 - GuildEmoji - A custom emoji that can be used within a guild.

#### Channel
Channel - A communication pipeline.
 - MessageChannel - A channel that allows users to send message.
   - TextChannel - A GuildChannel for text messages. (GuildChannel)
   - PrivateChannel - A one-to-one channel between two users.

 - VoiceChannel - A GuildChannel for audio connections. (GuildChannel)
 - GuildChannel (Interface) - A Channel that exist in a guild.

#### Message
Message - A text bubble some user just said.
 - StringMessage - Normal messages that only contains string.
 - EmbedMessage - Embed messages that can by sent by bot.
 <br /><br />
 - Builders
   - MessageBuilder - Build a message, can be StringMessage or EmbedMessage.
   - EmbedMessageBuilder - Build a JSONObject of an embed, used to build
     embed messages and send in channels.

## Event
Event - Whenever a change happens to an entity, an event get fired.
 - GatewayEvent - Events that are fired at connection.
   - ReadyEvent - Event that fired when the Discord server is ready.
   - ResumedEvent - Event fired when reconnect to the Discord server.
 - GuildEvent - Any events that happens under a guild.
   - GuildCreateEvent - Fired whenever a guild is detected.
   - GuildRoleCreatedEvent - Fired whenever a role is created.

## Exception
 - ErrorResponseException - An exception for Json Error Responses.

## Gateway
 ~~Internal Objects~~
<br />
Gateway - The communication between J-Cord and Discord API.
 - GatewayAdaptor - Communication client for Discord GateWay.
 - OPCode (Enumeration) - OP Code sent by Discord GateWay server.
 - DisconnectionCode (Enumeration) - Disconnection Code sent when Discord gateway closed.
 - ErrorResponse (Enumeration) - Json Error Responses.
 - HttpPath (Static) - Used to set HttpRequest Paths and request them.
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
 - SnowFake (Interface) - Objects that has ID and mentionable.

#### Guild
Guild - A collection of users and channels, often referred to in the UI as a server.
 - Region - Used for guild voice channel connection.

#### Channel
Channel - A communication pipeline.
 - GuildChannel - A Channel that exist in a guild.
   - TextChannel - A GuildChannel for text messages.
   - VoiceChannel - A GuildChannel for audio connections.
 - PrivateChannel - A one-to-one channel between two users.

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
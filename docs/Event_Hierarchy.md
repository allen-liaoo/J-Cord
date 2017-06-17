Event - Whenever a change happens to an entity, an event get fired.
 - GatewayEvent - Events that are fired at connection.
   - ReadyEvent - Event that fired when the Discord server is ready.
   - ResumedEvent - Event fired when reconnect to the Discord server.
 - GuildEvent - Any events that happens under a guild.
   - GuildCreateEvent - Fired whenever a guild is detected. Do not use this event.
   - GuildUpdateEvent - Fired whenever a guild's setting is changed.
     - GuildOwnerUpdateEvent
     - GuildRegionUpdateEvent
     - GuildIconUpdateEvent
     - GuildVerificationUpdateEvent
     - GuildNotificationUpdateEvent
     - GuildMFAUpdateEvent
     - GuildAFKTimeoutUpdateEvent

   - GuildDeleteEvent - Fired whenever the identity left a guild, or the user deletes a guild.
   - GuildUnavailableEvent
   - GuildMemberEvent
     - GuildMemberJoinEvent
     - GuildMemberLeaveEvent
     - GuildMemberBanEvent
     - GuildUnbanEvent (Special event that doesn't really counts as a GuildMemberEvent. <br />
       There is no member getter for the event)
   - GuildRoleCreatedEvent - Fired whenever a role is created.
 - MessageEvent - Events that are related to a message.
   - MessageCreateEvent
   - MessageUpdateEvent
   - MessageDeleteEvent
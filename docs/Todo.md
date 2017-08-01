# Todo List

### Description
 - [x] Done : Tested and working.
 - [ ] Half-Done : Missing stuff.
 - [ ] Not Done

### Discord Objects
 - [x] Guild
  - [x] Member
  - [x] Role
 - [x] User
 - [x] Channel
  - [x] GuildChannel
      - [x] TextChannel
      - [x] VoiceChannel
  - [x] PrivateChannel
  - [x] Group
  - [x] IChannelManager (For both text and voice)
 - [x] Message
 - [x] Permission
 - [x] Emoji
 - [x] Invite
 - [x] InviteBuilder
 - [x] Webhook

### Planned Features
1. OAuth - Get information about the authorized users and perform actions.
2. Client - Create objects such as Group, Connection, Friend, etc.
3. Bot - Get the bot application information.
4. MessageProcessor - Add the ability to ignore markdowns and mentions
5. Command Line Interface - A built in command line interface, used to get status about the identity, and shutdown, startup identities and connections.
6. Events - Events for clients, guild members, voice states.
6. Audio Support - Add user voice states, connections. Add a new web socket client for voice connection. Get received and sent voice data. Built-in audio players, track loaders, easy to extend and customize.
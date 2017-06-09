package org.alienideology.jcord.command;

/**
 * CommandResponder - All command classes must implements CommandResponder in order to be added to the framework.
 * @author AlienIdeology
 */
public interface CommandResponder {

    /* Description:
        This is an empty interface. You can choose the name of your methods and parameters.
        Make sure to add the @Command annotation to the methods.
     */

    /* Example 1:

        public class PingCommand implements CommandResponder {
            @Command (aliases = {"ping"})
            public String onPingCommand () {
                return "pong!";
            }
        }

        Returning String, MessageBuilder, or EmbedMessageBuilder will automatically be sent back to the
        MessageChannel (MessageCreateEvent#getChannel)
     */

    /* Example 2:

        public class HelpCommand implements CommandResponder {
            private final String help_msg = "WELP!!!";

            @Command (aliases = {"help", "commands"}, guildOnly = true}
            public EmbedMessageBuilder onHelpCommand(Guild guild, Member member) {
                EmbedMessageBuilder embed = new EmbedMessageBuilder()
                    .setTitle(member.getName(), null)
                    .setDescription(help_msg);
                return embed;
            }
        }

        The parameters Guild and Member will be the MessageCreateEvent#getGuild and MessageCreateEvent#getMember, respectively.
     */

    /* List of available parameters:
        (Define "Event" as MessageCreateEvent)

        String : Message Content
        String[] : Message Content split by white space characters, put into an array.
        Integer or int : Event Sequence

        Identity : Identity of the Event
        User : Author or the Event
        Message : The message of the Event
        Guild : The guild of the Event, may be null for PrivateChannels
            Guild.Verification : The guild verification level.
            Guild.Notification : The guild notification level.
            Guild.AFK_Timeout : The guild afk timeout.
        Member : The member object of the author.

        Channel or MessageChannel : The MessageChannel this event is sent in.
        TextChannel : The TextChannel this event is sent in. May be null.
        PrivateChannel : The PrivateChannel this event is sent in. May be null.

        MessageCreateEvent : The MessageCreateEvent itself.
        GuildMessageCreateEvent : The GuildMessageCreateEvent. May be null.
        PrivateMessageCreateEvent ; The PrivateMessageCreateEvent. May be null.

        List<T> when T is:
            Guild : A list of guilds under the identity.
            Member : A list of members under the guild. (Null for PrivateMessageCreateEvent)
            User : A list of users under the guild. (Or under the identity for PrivateMessageCreateEvent)
            Role : A list of roles under the guild. (All roles under the identity for PrivateMessageCreateEvent)
            GuildEmoji : A list of guild emojis (Null for PrivateMessageCreateEvent)
            TextChannel : A list of text channels under the guild. (Null for PrivateMessageCreateEvent)
            VoiceChannel : A list of voice channels under the guild. (Null for PrivateMessageCreateEvent)
            PrivateChannel : A list of private channels under the identity.

        Null : For all other parameters that the Framework did not recognize.

     */

}

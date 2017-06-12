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

            @Command (aliases = {"help", "commands", "list commands"}, guildOnly = true} // aliases support whitespaces in between.
            public EmbedMessageBuilder onHelpCommand(Guild guild, Member member) {
                EmbedMessageBuilder embed = new EmbedMessageBuilder()
                    .setTitle(member.getName(), null)
                    .setDescription(help_msg);
                return embed;
            }
        }

        The parameters Guild and Member will be the MessageCreateEvent#getGuild and MessageCreateEvent#getMember, respectively.
        See below for more information on parameters.
     */

    /* List of available parameters:
        (Note: "Event" is MessageCreateEvent)

        String : Message Content
        String[] : Message Content split by white space characters, put into an array. (Not including the prefix and alias)

            Example Message: "?list help commands"
                String[] args = new String[] {"help", "commands"}; // "?" is the prefix, and "list" is the alias

        Integer or int : Event Sequence

        Identity : Identity of the Event
        User : Author or the Event
        Message : The message of the Event
        Guild : The guild of the Event, may be null for PrivateChannels
        Member : The member object of the author

        Channel or MessageChannel : The MessageChannel this event is sent in.
        TextChannel : The TextChannel this event is sent in. May be null.
        PrivateChannel : The PrivateChannel this event is sent in. May be null.

        MessageCreateEvent : The MessageCreateEvent itself.
        IGuildMessageCreateEvent : The IGuildMessageCreateEvent. May be null.
        PrivateMessageCreateEvent ; The PrivateMessageCreateEvent. May be null.

        Null : For all other parameters that the Framework did not recognize.
        Note that primitives other than int will throw IllegalArgumentException.

     */

}

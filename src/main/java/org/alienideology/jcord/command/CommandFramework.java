package org.alienideology.jcord.command;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.event.message.GuildMessageCreateEvent;
import org.alienideology.jcord.event.message.MessageCreateEvent;
import org.alienideology.jcord.event.message.PrivateMessageCreateEvent;
import org.alienideology.jcord.object.Guild;
import org.alienideology.jcord.object.Message;
import org.alienideology.jcord.object.channel.*;
import org.alienideology.jcord.object.guild.GuildEmoji;
import org.alienideology.jcord.object.guild.Member;
import org.alienideology.jcord.object.guild.Role;
import org.alienideology.jcord.object.message.EmbedMessageBuilder;
import org.alienideology.jcord.object.message.MessageBuilder;
import org.alienideology.jcord.object.user.User;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * CommandFramework - The core command framework of J-Cord.
 * @author AlienIdeology
 */
public class CommandFramework {

    private List<String> prefixes;
    private HashMap<Command, ResponderContainer> annotations;
    private DispatcherAdaptor dispatcher;

    /**
     * Default Constructor
     */
    public CommandFramework() {
        this.prefixes = new ArrayList<>();
        this.annotations = new HashMap<>();
        this.dispatcher = new DispatcherAdaptor() {

            @Override
            public void onGuildMessageCreate(GuildMessageCreateEvent event) {
                if(filterSource(event)) return;
                for (Command command : annotations.keySet()) {
                    if (command.privateOnly()) return;
                    handleCommand(command, new CommandParser(event));
                }
            }

            @Override
            public void onPrivateMessageCreate(PrivateMessageCreateEvent event) {
                if(filterSource(event)) return;
                for (Command command : annotations.keySet()) {
                    if (command.guildOnly()) return;
                    handleCommand(command, new CommandParser(event));
                }
            }

            private boolean filterSource(MessageCreateEvent event) {
                if (event.getUser().isBot() || event.getUser().isWebHook() || event.getMessage().isFromSelf())
                    return true;

                if (prefixes == null || prefixes.size() == 0) {
                    IllegalStateException exception = new IllegalStateException("Prefix need to be set before listening to messages!");
                    exception.printStackTrace();
                    throw exception;
                }

                for (String prefix : prefixes) {
                    if (event.getMessage().getContent().startsWith(prefix))
                        return false;
                }
                return true;
            }

        };
    }

    /**
     * Set the prefix of this CommandFramework.
     * If no prefix is set, then the framework will not respond to any commands.
     * @param prefixes The varargs of prefixes. Can be more than 1.
     * @return The CommandFramework for chaining.
     */
    public CommandFramework setPrefixes(String... prefixes) {
        this.prefixes.addAll(Arrays.asList(prefixes));
        return this;
    }

    /**
     * Register objects that extends CommandResponder
     * @param commands The varargs of command objects
     * @return The CommandFramework for chaining.
     */
    public CommandFramework registerCommandResponder(CommandResponder... commands) {
        for (CommandResponder responder : commands) {
            for (Method method : responder.getClass().getMethods()) {
                if (method.isAnnotationPresent(Command.class)) {
                    annotations.put(method.getAnnotation(Command.class), new ResponderContainer(responder, method));
                }
            }
        }
        return this;
    }

    @SuppressWarnings("InstantiatingObjectToGetClassObject")
    private void handleCommand(Command command, CommandParser parser) {
        for (String alias : command.aliases()) {
            if (alias.equals(parser.invoke)) {
                Method method = annotations.get(command).method;

                MessageCreateEvent event = parser.event;
                Identity identity = event.getIdentity();
                Message message = event.getMessage();
                Guild guild = event.getGuild();
                Member member = event.getMember();

                Object[] params = new Object[method.getParameterTypes().length];

                /* Initialize Parameters */
                for (int i = 0; i < params.length; i++) {
                    Type type = method.getParameterTypes()[i];

                    if (type == String.class) {
                        params[i] = message.getContent();
                    } else if (type == String[].class) {
                        params[i] = parser.args;
                    } else if (type == Integer.class || type == int.class) {
                        params[i] = event.getSequence();
                    } else if (type == Identity.class) {
                        params[i] = identity;
                    } else if (type == User.class) {
                        params[i] = event.getUser();
                    } else if (type == Message.class) {
                        params[i] = message;
                    } else if (type == Guild.class) {
                        params[i] = guild;
                    } else if (type == Guild.Verification.class) {
                        params[i] = guild.getVerificationLevel();
                    } else if (type == Guild.Notification.class) {
                        params[i] = guild.getNotificationsLevel();
                    } else if (type == Guild.AFK_Timeout.class) {
                        params[i] = guild.getAfkTimeout();
                    } else if (type == Member.class) {
                        params[i] = member;
                    } else if (type == Channel.class || type == MessageChannel.class) {
                        params[i] = event.getChannel();
                    } else if (type == TextChannel.class) {
                        params[i] = event.getTextChannel();
                    } else if (type == PrivateChannel.class) {
                        params[i] = event.getPrivateChannel();
                    } else if (type == MessageCreateEvent.class) {
                        params[i] = event;
                    } else if (type == GuildMessageCreateEvent.class && event instanceof GuildMessageCreateEvent) {
                        params[i] = (GuildMessageCreateEvent) event;
                    } else if (type == PrivateMessageCreateEvent.class && event instanceof PrivateMessageCreateEvent) {
                        params[i] = (PrivateMessageCreateEvent) event;
                    } else if (type == new ArrayList<Guild>().getClass()) {
                        params[i] = identity.getGuilds();
                    } else if (type == new ArrayList<Member>().getClass() && event instanceof GuildMessageCreateEvent) {
                        params[i] = guild.getMembers();
                    } else if (type == new ArrayList<User>().getClass()) {
                        if (event instanceof GuildMessageCreateEvent) {
                            params[i] = guild.getUsers();
                        } else {
                            params[i] = identity.getUsers();
                        }
                    } else if (type == new ArrayList<Role>().getClass()) {
                        if (event instanceof GuildMessageCreateEvent) {
                            params[i] = member.getRoles();
                        } else {
                            params[i] = identity.getAllRoles();
                        }
                    } else if (type == new ArrayList<GuildEmoji>().getClass() && event instanceof GuildMessageCreateEvent) {
                        params[i] = guild.getGuildEmojis();
                    } else if (type == new ArrayList<TextChannel>().getClass() && event instanceof GuildMessageCreateEvent) {
                        params[i] = guild.getTextChannels();
                    } else if (type == new ArrayList<VoiceChannel>().getClass() && event instanceof GuildMessageCreateEvent) {
                        params[i] = guild.getVoiceChannels();
                    } else if (type == new ArrayList<PrivateChannel>().getClass()) {
                        params[i] = identity.getGuilds();
                    } else {
                        params[i] = null;
                    }
                }

                try {
                    Object invoked = method.invoke(annotations.get(command).responder, params);

                    /* Reply with return values */
                    if (invoked instanceof String) {
                        event.getChannel().sendMessage((String) invoked);
                    } else if (invoked instanceof MessageBuilder) {
                        event.getChannel().sendMessage((MessageBuilder) invoked);
                    } else if (invoked instanceof EmbedMessageBuilder) {
                        event.getChannel().sendMessage((EmbedMessageBuilder) invoked);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @return A list of prefixes
     */
    public List<String> getPrefixes() {
        return prefixes;
    }

    /**
     * @return The DispatcherAdaptor of this CommandFramework
     */
    public DispatcherAdaptor getDispatcher() {
        return dispatcher;
    }

    /**
     * @return A list of {@code Command} annotations registered by registering CommandResponders.
     */
    public List<Command> getAnnotations() {
        return new ArrayList<>(annotations.keySet());
    }

    private class ResponderContainer {
        public CommandResponder responder;
        public Method method;

        public ResponderContainer(CommandResponder responder, Method method) {
            this.responder = responder;
            this.method = method;
        }

    }

    private class CommandParser {

        String invoke;
        String[] args;
        MessageCreateEvent event;

        CommandParser(MessageCreateEvent event) {
            String content = event.getMessage().getContent();
            for (String prefix : prefixes) {
                if (content.startsWith(prefix)) {
                    content = content.replaceFirst(prefix, "");
                    break;
                }
            }

            String[] split = content.split("\\s+");
            this.invoke = split.length == 0 ? null : split[0];
            this.args = split.length <= 1 ? new String[]{} : Arrays.copyOfRange(split, 1, split.length-1);
            this.event = event;
        }
    }

}

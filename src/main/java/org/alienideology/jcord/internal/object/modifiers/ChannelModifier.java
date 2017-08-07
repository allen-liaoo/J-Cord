package org.alienideology.jcord.internal.object.modifiers;

import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.modifiers.Attribute;
import org.alienideology.jcord.handle.modifiers.IChannelModifier;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;

/**
 * @author AlienIdeology
 */
public final class ChannelModifier extends Modifier<AuditAction<Void>> implements IChannelModifier {

    private final IGuildChannel channel;

    private Attribute<IChannelModifier, String> nameAttr;
    private Attribute<IChannelModifier, Integer> positionAttr;
    private Attribute<IChannelModifier, String> topicAttr;
    private Attribute<IChannelModifier, Integer> bitrateAttr;
    private Attribute<IChannelModifier, Integer> userLimitAttr;

    public ChannelModifier(IGuildChannel channel) {
        super(channel.getIdentity());
        this.channel = channel;
        setupAttributes();
    }

    @Override
    public IGuildChannel getGuildChannel() {
        return channel;
    }

    @Override
    public IChannelModifier name(String name) {
        return nameAttr.setValue(name);
    }

    @Override
    public IChannelModifier position(int position) {
        return positionAttr.setValue(position);
    }

    @Override
    public IChannelModifier topic(String topic) {
        return topicAttr.setValue(topic);
    }

    @Override
    public IChannelModifier bitrate(int bitrate) {
        return bitrateAttr.setValue(bitrate);
    }

    @Override
    public IChannelModifier userLimit(int limit) {
        return userLimitAttr.setValue(limit);
    }

    @Override
    public Attribute<IChannelModifier, String> getNameAttr() {
        return nameAttr;
    }

    @Override
    public Attribute<IChannelModifier, Integer> getPositionAttr() {
        return positionAttr;
    }

    @Override
    public Attribute<IChannelModifier, String> getTopicAttr() {
        return topicAttr;
    }

    @Override
    public Attribute<IChannelModifier, Integer> getBitrateAttr() {
        return bitrateAttr;
    }

    @Override
    public Attribute<IChannelModifier, Integer> getUserLimitAttr() {
        return userLimitAttr;
    }

    @Override
    public AuditAction<Void> modify() {
        if (!channel.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        return new AuditAction<Void>(getIdentity(), HttpPath.Channel.MODIFY_CHANNEL, channel.getId()) {
            @Override
            protected Void request(Requester requester) {
                requester.updateRequestWithBody(request -> request.body(getUpdatableJson())).performRequest();
                reset(); // Reset attributes after requested
                return null;
            }
        };
    }

    @Override
    protected void setupAttributes() {
        nameAttr = new Attribute<IChannelModifier, String>("name", this, channel::getName) {
            @Override
            public void checkValue(String value) throws IllegalArgumentException {
                if (!IGuildChannel.isValidChannelName(value)) {
                    throw new IllegalArgumentException("Invalid guild channel name!");
                }
            }

            @Override
            public Object getAltValue() {
                if (newValue == null)
                    newValue = "";
                return super.getAltValue();
            }
        };
        positionAttr = new Attribute<IChannelModifier, Integer>("position", this, channel::getPosition) {
            @Override
            public void checkValue(Integer value) throws IllegalArgumentException {}
        };
        topicAttr = new Attribute<IChannelModifier, String>("topic", this,
                channel instanceof  ITextChannel ? ((ITextChannel) channel)::getTopic : null) {
            @Override
            public void checkValue(String value) throws IllegalArgumentException {
                if (getGuildChannel() instanceof IVoiceChannel) {
                    throw new IllegalArgumentException("Cannot modify the topic of a voice channel!");
                }
                if (!ITextChannel.isValidTopic(value)) {
                    throw new IllegalArgumentException("Invalid text channel topic!");
                }
            }

            @Override
            public Object getAltValue() {
                if (newValue == null)
                    newValue = "";
                return super.getAltValue();
            }
        };
        bitrateAttr = new Attribute<IChannelModifier, Integer>("bitrate", this,
                channel instanceof  IVoiceChannel ? ((IVoiceChannel) channel)::getBitrate : null) {
            @Override
            public void checkValue(Integer value) throws IllegalArgumentException {
                if (getGuildChannel() instanceof ITextChannel) {
                    throw new IllegalArgumentException("Cannot modify the bitrate of a text channel!");
                }
                if (value < IVoiceChannel.BITRATE_MIN) {
                    throw new IllegalArgumentException("The bitrate can not be lower than "+ IVoiceChannel.BITRATE_MIN +"!");
                } else if (value > IVoiceChannel.BITRATE_MAX) {
                    if (channel.getGuild().getSplash() != null && value > IVoiceChannel.VOICE_CHANNEL_BITRATE_VIP_MAX) { // Guild is VIP
                        throw new IllegalArgumentException("The bitrate of a vip guild can not be greater than "+ IVoiceChannel.VOICE_CHANNEL_BITRATE_VIP_MAX+"!");
                    } else {
                        throw new IllegalArgumentException("The bitrate of a normal guild can not be greater than "+ IVoiceChannel.BITRATE_MAX +"!");
                    }
                }
            }
        };
        userLimitAttr = new Attribute<IChannelModifier, Integer>("user_limit", this,
                channel instanceof  IVoiceChannel ? ((IVoiceChannel) channel)::getUserLimit : null) {
            @Override
            public void checkValue(Integer value) throws IllegalArgumentException {
                if (getGuildChannel() instanceof ITextChannel) {
                    throw new IllegalArgumentException("Cannot modify the user limit of a text channel!");
                }
                if (!IVoiceChannel.isValidUserLimit(value)) {
                    throw new IllegalArgumentException("Invalid voice channel user limit!");
                }
            }
        };
        addAttributes(nameAttr, positionAttr, topicAttr, bitrateAttr, userLimitAttr);
    }

}

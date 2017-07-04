package org.alienideology.jcord.internal.object.user;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;

/**
 * @author AlienIdeology
 */
public class Webhook extends DiscordObject implements IWebhook {

    private final String id;

    private IGuild guild;
    private ITextChannel channel;
    private IUser author;
    private String defaultName;
    private String defaultAvatar;
    private String token;

    public Webhook(IdentityImpl identity, String id) {
        super(identity);
        this.id = id;
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public ITextChannel getChannel() {
        return channel;
    }

    @Override
    public IUser getAuthor() {
        return author;
    }

    @Override
    public String getDefaultName() {
        return defaultName;
    }

    @Override
    public String getDefaultAvatar() {
        return defaultAvatar;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getId() {
        return id;
    }

    /*-----------------------Setters------------------------*/

    public void setGuild(IGuild guild) {
        this.guild = guild;
    }

    public void setChannel(ITextChannel channel) {
        this.channel = channel;
    }

    public void setAuthor(IUser author) {
        this.author = author;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public void setDefaultAvatar(String defaultAvatar) {
        this.defaultAvatar = defaultAvatar;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

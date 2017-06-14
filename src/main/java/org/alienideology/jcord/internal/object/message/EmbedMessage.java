package org.alienideology.jcord.internal.object.message;

import org.alienideology.jcord.handle.message.IEmbedMessage;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.Message;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.user.User;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author AlienIdeology
 */
public final class EmbedMessage extends Message implements IEmbedMessage {

    private String title;
    private String description;
    private String url;

    private OffsetDateTime TimeStamp;
    private Color color;

    private Author author = null;
    private List<Field> fields = new ArrayList<>();
    private Thumbnail thumbnail = null;
    private Video video = null;
    private Provider provider = null;
    private Image image = null;
    private Footer footer = null;

    public EmbedMessage(IdentityImpl identity, String id, User author, String content, String timeStamp,
                        List<User> mentions, List<Role> mentionedRoles, List<Attachment> attachments, boolean isTTs, boolean mentionedEveryone, boolean isPinned) {
        super(identity, id, author, content, timeStamp, mentions, mentionedRoles, attachments, isTTs, mentionedEveryone, isPinned);
    }

    public EmbedMessage setEmbed(String title, String description, String url, String embed_timestamp, int color) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.TimeStamp = embed_timestamp == null ? null : OffsetDateTime.parse(embed_timestamp);
        this.color = new Color(color);
        return this;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public OffsetDateTime getTimeStamp() {
        return TimeStamp;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Author getAuthorField() {
        return author;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    @Override
    public Video getVideo() {
        return video;
    }

    @Override
    public Provider getProvider() {
        return provider;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public Footer getFooter() {
        return footer;
    }

    public EmbedMessage setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public EmbedMessage addFields(Field... fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public EmbedMessage setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public EmbedMessage setVideo(Video video) {
        this.video = video;
        return this;
    }

    public EmbedMessage setProvider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public EmbedMessage setImage(Image image) {
        this.image = image;
        return this;
    }

    public EmbedMessage setFooter(Footer footer) {
        this.footer = footer;
        return this;
    }

}

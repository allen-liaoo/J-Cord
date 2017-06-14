package org.alienideology.jcord.internal.object.message;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.object.Message;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.user.User;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * EmbedMessage - A message that can only be sent by a bot or webhook.
 * The message have a special block embed surround it.
 * @author AlienIdeology
 */
public class EmbedMessage extends Message {

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

    public EmbedMessage(Identity identity, String id, User author, String content, String timeStamp,
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public OffsetDateTime getTimeStamp() {
        return TimeStamp;
    }

    public Color getColor() {
        return color;
    }

    public Author getAuthorField() {
        return author;
    }

    public List<Field> getFields() {
        return fields;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public Video getVideo() {
        return video;
    }

    public Provider getProvider() {
        return provider;
    }

    public Image getImage() {
        return image;
    }

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

    public static class Author {
        private String name;
        private String url;
        private String iconUrl;
        private String proxyIconUrl;

        public Author(String name, String url, String iconUrl, String proxyIconUrl) {
            this.name = name;
            this.url = url;
            this.iconUrl = iconUrl;
            this.proxyIconUrl = proxyIconUrl;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public String getProxyIconUrl() {
            return proxyIconUrl;
        }
    }

    public static class Field {
        private String name;
        private String value;
        private boolean inline;

        public Field(String name, String value, boolean inline) {
            this.name = name;
            this.value = value;
            this.inline = inline;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public boolean isInline() {
            return inline;
        }
    }

    public static class Thumbnail {
        private String url;
        private String proxy_url;
        private int height;
        private int width;

        public Thumbnail(String url, String proxy_url, int height, int width) {
            this.url = url;
            this.proxy_url = proxy_url;
            this.height = height;
            this.width = width;
        }

        public String getUrl() {
            return url;
        }

        public String getProxy_url() {
            return proxy_url;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }

    public static class Video {
        private String url;
        private int height;
        private int width;

        public Video(String url, int height, int width) {
            this.url = url;
            this.height = height;
            this.width = width;
        }

        public String getUrl() {
            return url;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }

    public static class Provider {
        private String name;
        private String url;

        public Provider(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class Image {
        private String url;
        private String proxy_url;
        private int height;
        private int width;

        public Image(String url, String proxy_url, int height, int width) {
            this.url = url;
            this.proxy_url = proxy_url;
            this.height = height;
            this.width = width;
        }

        public String getUrl() {
            return url;
        }

        public String getProxy_url() {
            return proxy_url;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }

    public static class Footer {
        private String text;
        private String iconUrl;
        private String proxyIconUrl;

        public Footer(String text, String iconUrl, String proxyIconUrl) {
            this.text = text;
            this.iconUrl = iconUrl;
            this.proxyIconUrl = proxyIconUrl;
        }

        public String getText() {
            return text;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public String getProxyIconUrl() {
            return proxyIconUrl;
        }
    }

}

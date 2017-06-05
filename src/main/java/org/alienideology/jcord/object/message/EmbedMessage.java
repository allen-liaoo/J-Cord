package org.alienideology.jcord.object.message;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.User;

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
// TODO: Finish other Video, Image, Footer (Static Inner Classes)
public class EmbedMessage extends Message {

    private String title;
    private String description;
    private String url;

    private OffsetDateTime TimeStamp;
    private Color color;

    private Author author = null;
    private List<Field> fields = new ArrayList<>();
    private Thumbnail thumbnail = null;

    public EmbedMessage(Identity identity, String id, User author, String timeStamp, boolean isTTs, boolean mentionedEveryone, boolean isPinned) {
        super(identity, id, author, timeStamp, isTTs, mentionedEveryone, isPinned);
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

    public EmbedMessage setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public EmbedMessage addFields(Field... fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
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

}

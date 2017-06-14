package org.alienideology.jcord.handle.message;

import org.alienideology.jcord.internal.object.message.EmbedMessage;

import java.awt.*;
import java.time.OffsetDateTime;

/**
 * EmbedMessage - A message that can only be sent by a bot or webhook.
 * The message have a special block embed surround it.
 * @author AlienIdeology
 */
public interface IEmbedMessage extends IMessage {

    String getTitle();

    String getDescription();

    String getUrl();

    OffsetDateTime getTimeStamp();

    Color getColor();

    EmbedMessage.Author getAuthorField();

    java.util.List<EmbedMessage.Field> getFields();

    EmbedMessage.Thumbnail getThumbnail();

    EmbedMessage.Video getVideo();

    EmbedMessage.Provider getProvider();

    EmbedMessage.Image getImage();

    EmbedMessage.Footer getFooter();

    class Author {
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

    class Field {
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

    class Thumbnail {
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

    class Video {
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

    class Provider {
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

    class Image {
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

    class Footer {
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

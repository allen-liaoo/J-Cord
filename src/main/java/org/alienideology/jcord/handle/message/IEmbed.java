package org.alienideology.jcord.handle.message;

import org.alienideology.jcord.internal.object.message.Embed;

import java.awt.*;
import java.time.OffsetDateTime;

/**
 * Embed - A message that can only be sent by a bot or webhook.
 * The message have a special block embed surround it.
 *
 * @author AlienIdeology
 */
public interface IEmbed {

    /**
     * Get the title of the embed.
     *
     * @return The string title.
     */
    String getTitle();

    /**
     * Get the description of the embed.
     *
     * @return The string description.
     */
    String getDescription();

    /**
     * Get the URL of the title.
     *
     * @return The url.
     */
    String getUrl();

    /**
     * Get the timestamp of this embed. This is not the message created time.
     *
     * @return The offset datetime.
     */
    OffsetDateTime getTimeStamp();

    /**
     * Get the color of this embed.
     *
     * @return The embed's color.
     */
    Color getColor();

    /**
     * Get the author field of this embed.
     *
     * @return The author field.
     */
    Embed.Author getAuthorField();

    /**
     * @return A list of fields of this embed.
     */
    java.util.List<Embed.Field> getFields();

    /**
     * Get the thumbnail of this embed.
     *
     * @return The thumbnail.
     */
    Embed.Thumbnail getThumbnail();

    /**
     * Get the video field of this embed.
     *
     * @return The video.
     */
    Embed.Video getVideo();

    /**
     * Get the provider field of this embed.
     *
     * @return The provider.
     */
    Embed.Provider getProvider();

    /**
     * Get the image of this embed.
     *
     * @return The image.
     */
    Embed.Image getImage();

    /**
     * Get the footer of this embed.
     *
     * @return The footer.
     */
    Embed.Footer getFooter();

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

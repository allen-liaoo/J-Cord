package org.alienideology.jcord.handle.message;

import org.alienideology.jcord.internal.object.message.EmbedMessage;
import org.alienideology.jcord.util.MessageUtils;

import java.awt.*;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

/**
 * EmbedMessageBuilder - A message builder for building embeds messages.
 * @author AlienIdeology
 */
public final class EmbedMessageBuilder {

    private String title;
    private String url;
    private String description = "";
    private OffsetDateTime timeStamp;
    private Color color;

    private IEmbedMessage.Author author = null;
    private List<IEmbedMessage.Field> fields = new ArrayList<>();
    private IEmbedMessage.Thumbnail thumbnail = null;
    private IEmbedMessage.Image image = null;
    private IEmbedMessage.Footer footer = null;

    public EmbedMessageBuilder() {
    }

    /**
     * Build an embed message.
     *
     * @return The embed message.
     * @throws IllegalStateException If the empty is empty. See {@link #isEmpty()}.
     */
    public EmbedMessage build() throws IllegalStateException {
        if (this.isEmpty()) {
            IllegalStateException exception = new IllegalStateException("Embed message may not be empty!");
            exception.printStackTrace();
            throw exception;
        }

        EmbedMessage embed = new EmbedMessage(null, null, null, "", timeStamp == null ? null : timeStamp.toString(),
                null, null, null, false, false, false)
            .setColor(color)
            .addFields(fields.toArray(new IEmbedMessage.Field[fields.size()]))
            .setThumbnail(thumbnail)
            .setImage(image)
            .setFooter(footer);

        return embed;
    }

    /**
     * @return True of the embed is empty.
     */
    public boolean isEmpty() {
        return (title == null || title.isEmpty())
                && (url == null || url.isEmpty())
                && (description == null || description.isEmpty())
                && timeStamp == null
                && color == null
                && author == null
                && fields.isEmpty()
                && thumbnail == null
                && image == null
                && footer == null;
    }

    /**
     * Set the title of this embed.
     * If the url is set, then the title text will be high lighted blue.
     * @param title The title text, may not be null.
     * @param url The url. May be null or empty.
     *
     * @throws IllegalArgumentException
     *          If the title is empty or null.
     *          If the url is not valid.
     *
     * @return EmbedMessageBuilder for chaining.
     */
    public EmbedMessageBuilder setTitle(String title, String url) {

        nonNull("title", title);
        validateUrl("title url", url);
        this.title = title;
        this.url = url;
        return this;
    }

    /**
     * Set the description of this embed.
     * The description is a paragraph before every fields. Markdown supported.
     * @param description The description text to be added.
     *
     * @throws IllegalArgumentException I
     *          If the description is empty or null.
     *
     * @return EmbedMessageBuilder for chaining.
     */
    public EmbedMessageBuilder setDescription(String description) {
        nonNull("description", description);
        this.description = description;
        return this;
    }

    /**
     * Append the description to this embed.
     * @param description The description text to be append.
     *
     * @throws IllegalArgumentException
     *          If the description is empty or null.
     *
     * @return EmbedMessageBuilder for chaining.
     */
    public EmbedMessageBuilder appendDescription(String description) {
        nonNull("description(append)", description);
        this.description += description;
        return this;
    }

    /**
     * Set the left color bar of this embed.
     * If no color is set, the embed will appear to be gray.
     * @param color The color of this embed.
     * @return EmbedMessageBuilder for chaining.
     */
    public EmbedMessageBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    /**
     * Set the author of this embed.
     * The name of the author will not be blue even when the url is set.
     * @param name The name of this author.
     * @param url The url link of the name. May be null or empty.
     * @param iconUrl The icon of this author. May be null or empty.
     *
     * @throws IllegalArgumentException
     *          If the name is empty or null.
     *          If the url is not valid.
     *          If the iconUrl is not valid.
     *
     * @return EmbedMessageBuilder for chaining.
     */
    public EmbedMessageBuilder setAuthor(String name, String url, String iconUrl) {
        nonNull("author name", name);
        validateUrl("author url", url);
        validateUrl("author icon url", iconUrl);
        this.author = new IEmbedMessage.Author(name, url, iconUrl, null);
        return this;
    }

    /**
     * Add a field to this embed.
     * Maximum of three fields per line.
     * @param name The name of this field.
     * @param value The texts of this field.
     * @param inline True if this field is inline with other fields.
     *
     * @throws IllegalArgumentException
     *          If the name is empty or null.
     *          If the value is empty or null.
     *
     * @return EmbedMessageBuilder for chaining.
     */
    public EmbedMessageBuilder addField(String name, String value, boolean inline) {
        nonNull("field name", name);
        nonNull("field value", value);
        this.fields.add(new IEmbedMessage.Field(name, value, inline));
        return this;
    }

    /**
     * Set the thumbnail of this embed.
     * Thumbnail appears on the right side of an embed.
     * @param url The url of this thumbnail image.
     *
     * @throws IllegalArgumentException
     *          if the url is null or empty.
     *          If the url is not valid.
     *
     * @return EmbedMessageBuilder for chaining.
     */
    public EmbedMessageBuilder setThumbnail(String url) {
        nonNull("thumbnail url", url);
        validateUrl("thumbnail", url);
        this.thumbnail = new IEmbedMessage.Thumbnail(url, null, 0, 0);
        return this;
    }

    /**
     * Set the image of this embed.
     * Image appears at the bottom of an embed, after all fields and before the footer.
     * @param url The url of this image.
     * @throws IllegalArgumentException
     *          If the url is empty or null.
     *          If the url is not valid.
     *
     * @return EmbedMessageBuilder for chaining.
     */
    public EmbedMessageBuilder setImage(String url) {
        nonNull("image url", url);
        validateUrl("image", url);
        this.image = new IEmbedMessage.Image(url, null, 0, 0);
        return this;
    }

    /**
     * Set the TimeStamp of this embed.
     *
     * @param timeStamp
     *          The time stamp.
     *
     * @return EmbedMessageBuilder for chaining.
     */
    public EmbedMessageBuilder setTimeStamp(TemporalAccessor timeStamp) {
        if (timeStamp == null) {
            this.timeStamp = null;
        } else if (timeStamp instanceof OffsetDateTime){
            this.timeStamp = (OffsetDateTime) timeStamp;
        } else{
            ZoneOffset offset;

            try {
                offset = ZoneOffset.from(timeStamp);
            } catch (DateTimeException dte) {
                offset = ZoneOffset.UTC;
            }

            try {
                if (timeStamp instanceof Instant) {
                    this.timeStamp = OffsetDateTime.ofInstant((Instant) timeStamp, offset);
                } else if (timeStamp instanceof LocalDateTime) {
                    this.timeStamp = OffsetDateTime.of(LocalDateTime.from(timeStamp), offset);
                }
            } catch (DateTimeException ate) {
                setTimeStampNow(offset);
            }
        }
        return this;
    }

    /**
     * Set the TimeStamp of this embed to Instant#now from the provided time zone.
     * @param zone The time zone of this client.
     * @return EmbedMessageBuilder for chaining.
     */
    public EmbedMessageBuilder setTimeStampNow(ZoneId zone) {
        this.timeStamp = OffsetDateTime.ofInstant(Instant.now(), zone);
        return this;
    }

    /**
     * Set the TimeStamp of this embed to Instant#now.
     * This method uses UTC as the time zone.
     * See EmbedMessageBuilder#setTimeStampNow for more information.
     * @return EmbedMessageBuilder for chaining.
     */
    @Deprecated
    public EmbedMessageBuilder setTimeStampNow() {
        setTimeStampNow(ZoneOffset.UTC);
        return this;
    }

    /**
     * Set the footer of this embed.
     * @param text The text in the footer.
     * @param icon_url The icon in the footer. May be null or empty.
     * @return EmbedMessageBuilder for chaining.
     */
    public EmbedMessageBuilder setFooter(String text, String icon_url) {
        nonNull("footer text", text);
        validateUrl("footer url", icon_url);
        this.footer = new IEmbedMessage.Footer(text, icon_url, null);
        return this;
    }

    private void nonNull(String field, String name) {
        if (name == null || name.isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException("The value of the \""+field+"\" may not be empty!");
            exception.printStackTrace();
            throw exception;
        }
    }

    private void validateUrl(String field, String url) {
        if (url == null || url.isEmpty()) return;
        if (!url.matches(MessageUtils.PATTERN_IS_URL.pattern())) {
            IllegalArgumentException exception = new IllegalArgumentException("The value of the \""+field+"\" is not a valid url!");
            exception.printStackTrace();
            throw exception;
        }
    }

}

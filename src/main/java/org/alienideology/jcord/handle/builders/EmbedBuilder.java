package org.alienideology.jcord.handle.builders;

import org.alienideology.jcord.handle.message.IEmbed;
import org.alienideology.jcord.handle.message.MessageProcessor;
import org.alienideology.jcord.internal.object.message.Embed;

import java.awt.*;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

/**
 * EmbedBuilder - A message builder for building embeds messages.
 * @author AlienIdeology
 */
public final class EmbedBuilder implements Buildable<EmbedBuilder, Embed> {

    private String title;
    private String url;
    private String description = "";
    private OffsetDateTime timeStamp;
    private Color color;

    private IEmbed.Author author;
    private List<IEmbed.Field> fields = new ArrayList<>();
    private IEmbed.Thumbnail thumbnail;
    private IEmbed.Image image;
    private IEmbed.Footer footer;

    public EmbedBuilder() {
        clear();
    }

    /**
     * Build an embed message.
     *
     * @throws IllegalStateException If the empty is empty. See {@link #isEmpty()}.
     * @return The embed message.
     */
    @Override
    public Embed build() throws IllegalStateException {
        if (this.isEmpty()) {
            IllegalStateException exception = new IllegalStateException("Embed message may not be empty!");
            exception.printStackTrace();
            throw exception;
        }

        return new Embed()
            .setTitle(title)
            .setUrl(url)
            .setDescription(description)
            .setColor(color)
            .setTimeStamp(timeStamp)
            .setAuthor(author)
            .addFields(fields.toArray(new IEmbed.Field[fields.size()]))
            .setThumbnail(thumbnail)
            .setImage(image)
            .setFooter(footer);
    }

    @Override
    public EmbedBuilder clear() {
        title = null;
        url = null;
        description = "";
        timeStamp = null;
        color = null;
        author = null;
        fields.clear();
        thumbnail = null;
        image = null;
        footer = null;
        return this;
    }

    /**
     * Check if an embed is empty or not.
     *
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
     * @return EmbedBuilder for chaining.
     */
    public EmbedBuilder setTitle(String title, String url) {

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
     * @return EmbedBuilder for chaining.
     */
    public EmbedBuilder setDescription(String description) {
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
     * @return EmbedBuilder for chaining.
     */
    public EmbedBuilder appendDescription(String description) {
        nonNull("description(append)", description);
        this.description += description;
        return this;
    }

    /**
     * Set the left color bar of this embed.
     * If no color is set, the embed will appear to be gray.
     * @param color The color of this embed.
     * @return EmbedBuilder for chaining.
     */
    public EmbedBuilder setColor(Color color) {
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
     * @return EmbedBuilder for chaining.
     */
    public EmbedBuilder setAuthor(String name, String url, String iconUrl) {
        nonNull("author name", name);
        validateUrl("author url", url);
        validateUrl("author icon url", iconUrl);
        this.author = new IEmbed.Author(name, url, iconUrl, null);
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
     * @return EmbedBuilder for chaining.
     */
    public EmbedBuilder addField(String name, String value, boolean inline) {
        nonNull("field name", name);
        nonNull("field value", value);
        this.fields.add(new IEmbed.Field(name, value, inline));
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
     * @return EmbedBuilder for chaining.
     */
    public EmbedBuilder setThumbnail(String url) {
        nonNull("thumbnail url", url);
        validateUrl("thumbnail", url);
        this.thumbnail = new IEmbed.Thumbnail(url, null, 0, 0);
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
     * @return EmbedBuilder for chaining.
     */
    public EmbedBuilder setImage(String url) {
        nonNull("image url", url);
        validateUrl("image", url);
        this.image = new IEmbed.Image(url, null, 0, 0);
        return this;
    }

    /**
     * Set the TimeStamp of this embed.
     *
     * @param timeStamp
     *          The time stamp.
     *
     * @return EmbedBuilder for chaining.
     */
    public EmbedBuilder setTimeStamp(TemporalAccessor timeStamp) {
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
     * @param zone The time zone of this java.client.
     * @return EmbedBuilder for chaining.
     */
    public EmbedBuilder setTimeStampNow(ZoneId zone) {
        this.timeStamp = OffsetDateTime.ofInstant(Instant.now(), zone);
        return this;
    }

    /**
     * Set the TimeStamp of this embed to Instant#now.
     * This event uses UTC as the time zone.
     * See EmbedBuilder#setTimeStampNow for more information.
     * @return EmbedBuilder for chaining.
     */
    @Deprecated
    public EmbedBuilder setTimeStampNow() {
        setTimeStampNow(ZoneOffset.UTC);
        return this;
    }

    /**
     * Set the footer of this embed.
     * @param text The text in the footer.
     * @param icon_url The icon in the footer. May be null or empty.
     * @return EmbedBuilder for chaining.
     */
    public EmbedBuilder setFooter(String text, String icon_url) {
        nonNull("footer text", text);
        validateUrl("footer url", icon_url);
        this.footer = new IEmbed.Footer(text, icon_url, null);
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
        if (!url.matches(MessageProcessor.PATTERN_URL.pattern())) {
            IllegalArgumentException exception = new IllegalArgumentException("The value of the \""+field+"\" is not a valid url!");
            exception.printStackTrace();
            throw exception;
        }
    }

}

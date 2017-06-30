package org.alienideology.jcord.internal.object.message;

import org.alienideology.jcord.handle.message.IEmbed;
import org.alienideology.jcord.internal.object.Buildable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author AlienIdeology
 */
public final class Embed implements IEmbed, Buildable {

    private String title;
    private String description = "";
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

    public Embed() {
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        if (title != null) json.put("title", title);
        if (url != null) json.put("url", url);
        if (!description.isEmpty()) json.put("description", description);
        if (TimeStamp != null) {
            json.put("timestamp", TimeStamp.format(DateTimeFormatter.ISO_DATE_TIME));
        }
        if (color != null) json.put("color", color.getRGB() & 0xFFFFFF);
        if (author != null) {
            JSONObject authorJson = new JSONObject();
            authorJson.put("name", author.getName());   // NonNull
            if (author.getUrl() != null) authorJson.put("url", author.getUrl());
            if (author.getIconUrl() != null) authorJson.put("icon_url", author.getIconUrl());
            json.put("author", authorJson);
        }
        if (!fields.isEmpty()) {
            JSONArray array = new JSONArray();
            for (IEmbed.Field field : fields) {
                array.put(new JSONObject()
                        .put("name", field.getName())   // NonNull
                        .put("value", field.getValue())   // NonNull
                        .put("inline", field.isInline()));   // NonNull
            }
            if (array.length() != 0) json.put("fields", array);
        }
        if (thumbnail != null) {
            json.put("thumbnail", new JSONObject()
                    .put("url", thumbnail.getUrl()));   // NonNull
        }
        if (image != null) {
            json.put("image", new JSONObject()
                    .put("url", image.getUrl()));   // NonNull
        }
        if (footer != null) {
            JSONObject footerJson = new JSONObject()
                    .put("text", footer.getText());   // NonNull
            if (footer.getIconUrl() != null) footerJson.put("icon_url", footer.getIconUrl());
            json.put("footer", footerJson);
        }
        return json;
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

    public Embed setTitle(String title) {
        this.title = title;
        return this;
    }

    public Embed setDescription(String description) {
        this.description = description;
        return this;
    }

    public Embed setUrl(String url) {
        this.url = url;
        return this;
    }

    public Embed setTimeStamp(OffsetDateTime timeStamp) {
        TimeStamp = timeStamp;
        return this;
    }

    public Embed setColor(Color color) {
        this.color = color;
        return this;
    }

    public Embed setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public Embed addFields(Field... fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public Embed setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public Embed setVideo(Video video) {
        this.video = video;
        return this;
    }

    public Embed setProvider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public Embed setImage(Image image) {
        this.image = image;
        return this;
    }

    public Embed setFooter(Footer footer) {
        this.footer = footer;
        return this;
    }

}

package org.alienideology.jcord.object.message;

import org.alienideology.jcord.object.Mention;
import org.json.JSONObject;

/**
 * MessageBuilder - A builder for building general messages.
 * @author AlienIdeology
 */
public final class MessageBuilder {

    private StringBuilder content;
    private boolean isTTS = false;

    private EmbedMessageBuilder embed = null;

    /**
     * Default Constructor
     */
    public MessageBuilder() {
        this.content = new StringBuilder("");
    }

    /**
     * Parametric Constructor
     * @param content The content of this message.
     */
    public MessageBuilder(String content) {
        this.content = new StringBuilder(content);
    }

    public JSONObject build() {
        JSONObject json = new JSONObject();
        json.put("content", content.toString());
        json.put("tts", isTTS);

        if (embed != null) json.put("embed", embed.build());

        return json;
    }

    /**
     * Set the content of this message.
     * For string messages only.
     * @param content The content of this message.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder setContent(String content) {
        this.content = new StringBuilder(content);
        return this;
    }

    /**
     * Append content to this message.
     * @param content The object of the content to be appended.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendContent(Object content) {
        this.content.append(String.valueOf(content));
        return this;
    }

    /**
     * Format and append content to this message.
     * @param format The string format.
     * @param args The varargs to be formatted.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendContentFormat(String format, Object... args) {
        this.content.append(String.format(format, args));
        return this;
    }

    /**
     * Append a default code block without language specified.
     * @param code The code inside this code block.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendCodeBlock(String code) {
        appendCodeBlock("", code);
        return this;
    }

    /**
     * Append a code block with language specified.
     * @param language The name of the language.
     * @param code The code inside this code block.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendCodeBlock(String language, String code) {
        this.content.append("```").append(language)
                .append("\n\n").append(code)
                .append("```\n");
        return this;
    }

    /**
     * Append a mention to the content.
     * @param mention An object that implements Mention.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendMention(Mention mention) {
        this.content.append(mention.mention());
        return this;
    }

    /**
     * Set the TTS of this message.
     * @param TTS True if this message is a tts message.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder setTTS(boolean TTS) {
        isTTS = TTS;
        return this;
    }

    /**
     * [API Use Only]
     * Set the embeds of this message.
     * Use MessageChannel#sendMessage(EmbedMessageBuilder) instead of this.
     * @param builder The EmbedMessageBuilder
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder setAsEmbed(EmbedMessageBuilder builder) {
        embed = builder;
        return this;
    }

    public int length() {
        return content.length();
    }

    /**
     * @return true if the content of this message is longer than the max limit.
     */
    public boolean isTooLong() {
        return content.length() > Message.MAX_CONTENT_LENGTH;
    }

    public boolean isEmpty() {
        return content.toString().isEmpty() && embed.isEmpty();
    }

    public String getContent() {
        return content.toString();
    }

    public StringBuilder getStringBuilder() {
        return content;
    }

    public boolean isTTS() {
        return isTTS;
    }

    public EmbedMessageBuilder getEmbed() {
        return embed;
    }
}

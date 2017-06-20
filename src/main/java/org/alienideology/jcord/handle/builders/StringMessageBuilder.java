package org.alienideology.jcord.handle.builders;

import org.alienideology.jcord.handle.EmojiTable;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.message.IStringMessage;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.message.StringMessage;

/**
 * StringMessageBuilder - A builder for building string messages.
 * @author AlienIdeology
 */
public final class StringMessageBuilder {

    private final EmojiTable emojis = new EmojiTable();

    private StringBuilder content;
    private boolean isTTS = false;

    /**
     * Default Constructor
     */
    public StringMessageBuilder() {
        this.content = new StringBuilder("");
    }

    /**
     * Parametric Constructor
     * @param content The content of this message.
     */
    public StringMessageBuilder(String content) {
        this.content = new StringBuilder(content);
    }

    /**
     * Build a string message.
     * @see org.alienideology.jcord.handle.channel.IMessageChannel#sendMessage(IStringMessage)
     * @see org.alienideology.jcord.handle.channel.IMessageChannel#editMessage(String, IStringMessage)
     * @see IMessage#edit(IStringMessage)
     * @see IMessage#reply(IStringMessage)
     *
     * @return The string message.
     */
    public IStringMessage build() {
        return new StringMessage(null, null, null, content.toString(), null,
                null, null, null, isTTS, false, false);
    }

    /**
     * Set the content of this message.
     * For string messages only.
     * @param content The content of this message.
     * @return StringMessageBuilder for chaining.
     */
    public StringMessageBuilder setContent(String content) {
        this.content = new StringBuilder(content);
        return this;
    }

    /**
     * Append content to this message.
     * @param content The object of the content to be appended.
     * @return StringMessageBuilder for chaining.
     */
    public StringMessageBuilder appendContent(Object content) {
        this.content.append(String.valueOf(content));
        return this;
    }

    /**
     * Format and append content to this message.
     * @param format The string format.
     * @param args The varargs to be formatted.
     * @return StringMessageBuilder for chaining.
     */
    public StringMessageBuilder appendContentFormat(String format, Object... args) {
        this.content.append(String.format(format, args));
        return this;
    }

    /**
     * Append a default code block without language specified.
     * @param code The code inside this code block.
     * @return StringMessageBuilder for chaining.
     */
    public StringMessageBuilder appendCodeBlock(String code) {
        appendCodeBlock("", code);
        return this;
    }

    /**
     * Append a code block with language specified.
     * @param language The name of the language.
     * @param code The code inside this code block.
     * @return StringMessageBuilder for chaining.
     */
    public StringMessageBuilder appendCodeBlock(String language, String code) {
        this.content.append("```").append(language)
                .append("\n\n").append(code)
                .append("```\n");
        return this;
    }

    /**
     * Append a mention to the content.
     * @param mention An object that implements IMention.
     * @return StringMessageBuilder for chaining.
     */
    public StringMessageBuilder appendMention(IMention mention) {
        this.content.append(mention.mention());
        return this;
    }

    /**
     * Append a guild mention to the content.
     * @deprecated See {@link #appendMention(IMention)}
     * @param guildEmoji An GuildEmoji to be appended.
     * @return StringMessageBuilder for chaining.
     */
    @Deprecated
    public StringMessageBuilder appendGuildEmoji(GuildEmoji guildEmoji) {
        this.content.append(guildEmoji.mention());
        return this;
    }

    /**
     * Append an Emoji to the content by its alias.
     * @param alias An alias of an Emoji object.
     * @return StringMessageBuilder for chaining.
     */
    public StringMessageBuilder appendEmoji(String alias) {
        this.content.append(emojis.getByAlias(alias) != null ? emojis.getByAlias(alias).getUnicode() : "");
        return this;
    }

    /**
     * Append an Emoji to the content.
     * @deprecated See {@link #appendEmoji(String)} for appending emoji by alias.
     * @param emoji An native Emoji object.
     * @return StringMessageBuilder for chaining.
     */
    @Deprecated
    public StringMessageBuilder appendEmoji(EmojiTable.Emoji emoji) {
        this.content.append(emoji.getUnicode());
        return this;
    }

    /**
     * Set the TTS of this message.
     * @param TTS True if this message is a tts message.
     * @return StringMessageBuilder for chaining.
     */
    public StringMessageBuilder setTTS(boolean TTS) {
        isTTS = TTS;
        return this;
    }

    /**
     * @return The string content length
     */
    public int length() {
        return content.length();
    }

    /**
     * @return true if the content of this message is longer than the max limit.
     */
    public boolean isTooLong() {
        return content.length() > Message.MAX_CONTENT_LENGTH;
    }

    /**
     * @return True if the content is empty.
     */
    public boolean isEmpty() {
        return content.toString().isEmpty();
    }

    /**
     * Get the content of this message being built.
     *
     * @return The string content
     */
    public String getContent() {
        return content.toString();
    }

    /**
     * Get the StringBuilder of this content.
     *
     * @return The content StringBuilder
     */
    public StringBuilder getContentStringBuilder() {
        return content;
    }

    /**
     * @return True if this message builder will build an tts message.
     * @see IMessage#isTTS()
     */
    public boolean isTTS() {
        return isTTS;
    }

}

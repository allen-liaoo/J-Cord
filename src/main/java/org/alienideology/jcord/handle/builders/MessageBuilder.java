package org.alienideology.jcord.handle.builders;

import org.alienideology.jcord.handle.EmojiTable;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.message.IEmbed;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;
import org.alienideology.jcord.internal.object.message.Embed;
import org.alienideology.jcord.internal.object.message.Message;

/**
 * MessageBuilder - A builder for building messages.
 *
 * @author AlienIdeology
 */
public final class MessageBuilder {

    private final EmojiTable emojis = new EmojiTable();

    private StringBuilder content;
    private IEmbed embed;
    private boolean isTTS = false;

    /**
     * Default Constructor
     */
    public MessageBuilder() {
        this.content = new StringBuilder("");
    }

    /**
     * Parametric Constructor
     *
     * @param content The content of this message.
     */
    public MessageBuilder(String content) {
        this.content = new StringBuilder(content);
    }

    /**
     * Build a string message.
     *
     * @see org.alienideology.jcord.handle.channel.IMessageChannel#sendMessage(IMessage)
     * @see org.alienideology.jcord.handle.channel.IMessageChannel#editMessage(String, IMessage)
     * @see IMessage#edit(IMessage)
     * @see IMessage#reply(IMessage)
     *
     * @return The string message.
     */
    public IMessage build() {
        Message message = new Message(null, null, null, content.toString(), null,
                null, null, null, isTTS, false, false);
        if (embed != null)
                message.addEmbed(embed);
        return message;
    }

    /**
     * Set the content of this message.
     *
     * @param content The content of this message.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder setContent(String content) {
        this.content = new StringBuilder(content);
        return this;
    }

    /**
     * Append content to this message.
     *
     * @param content The object of the content to be appended.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendContent(Object content) {
        this.content.append(String.valueOf(content));
        return this;
    }

    /**
     * Format and append content to this message.
     *
     * @param format The string format.
     * @param args The varargs to be formatted.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendContentFormat(String format, Object... args) {
        this.content.append(String.format(format, args));
        return this;
    }

    /**
     * Append a line to the content.
     *
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendLine() {
        this.content.append("\n");
        return this;
    }

    /**
     * Escape the markdowns for this message.
     *
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder escapeMarkdowns() {
        this.content.insert(0, "\\");
        return this;
    }

    /**
     * Append italic words to the content.
     *
     * @param italics The string to be italics.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendItalics(String italics) {
        this.content.append("*").append(italics).append("*");
        return this;
    }

    /**
     * Append bold words to the content.
     *
     * @param bold The string to be bold.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendBold(String bold) {
        this.content.append("**").append(bold).append("**");
        return this;
    }

    /**
     * Append strikeouts to the content.
     *
     * @param strikeout The string to be strikeouts
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendStrikeout(String strikeout) {
        this.content.append("~~").append(strikeout).append("~~");
        return this;
    }

    /**
     * Append underline words to the content.
     *
     * @param underline The string to be underlined.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendUnderline(String underline) {
        this.content.append("__").append(underline).append("__");
        return this;
    }

    /**
     * Append one line code block to the content.
     *
     * @param code The string to be included in he code block.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendCode(String code) {
        this.content.append("`").append(code).append("`");
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
     *
     * @param mention An object that implements IMention.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendMention(IMention mention) {
        this.content.append(mention.mention());
        return this;
    }

    /**
     * Append a guild mention to the content.
     *
     * @param guildEmoji An GuildEmoji to be appended.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendGuildEmoji(GuildEmoji guildEmoji) {
        this.content.append(guildEmoji.mention());
        return this;
    }

    /**
     * Append an Emoji to the content by its alias.
     *
     * @param alias An alias of an Emoji object.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendEmoji(String alias) {
        this.content.append(emojis.getByAlias(alias) != null ? emojis.getByAlias(alias).getUnicode() : "");
        return this;
    }

    /**
     * Append an Emoji to the content.
     *
     * @param emoji An native Emoji object.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder appendEmoji(EmojiTable.Emoji emoji) {
        this.content.append(emoji.getUnicode());
        return this;
    }

    /**
     * Set the TTS of this message.
     *
     * @param TTS True if this message is a tts message.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder setTTS(boolean TTS) {
        isTTS = TTS;
        return this;
    }

    /**
     * Set the embed part of this message.
     *
     * @param embed The embed message built by {@link EmbedBuilder}.
     * @return MessageBuilder for chaining.
     */
    public MessageBuilder setEmbed(IEmbed embed) {
        this.embed = embed;
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
        return content.length() > IMessage.MAX_CONTENT_LENGTH;
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

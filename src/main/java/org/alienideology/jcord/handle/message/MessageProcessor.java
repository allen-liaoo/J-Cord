package org.alienideology.jcord.handle.message;

import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.emoji.Emoji;
import org.alienideology.jcord.handle.emoji.Emojis;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.user.IUser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MessageProcessor - The processor of a message.
 * This can process the whole message, or act like a reader with a cursor.
 *
 * @author AlienIdeology
 */
public class MessageProcessor {

    public final static Pattern PATTERN_MENTIONS = Pattern.compile("<(@|@!|#|@&|:(\\w+):)[0-9]+>");
    public final static Pattern PATTERN_MENTION_USER = Pattern.compile("<@[0-9]+>");
    public final static Pattern PATTERN_MENTION_MEMBER = Pattern.compile("<@![0-9]+>");
    public final static Pattern PATTERN_MENTION_ROLE = Pattern.compile("<@&[0-9]+>");
    public final static Pattern PATTERN_MENTION_CHANNEL = Pattern.compile("<#[0-9]+>");
    public final static Pattern PATTERN_MENTION_EMOJI = Pattern.compile("<:\\w+:[0-9]+>");
    public final static Pattern PATTERN_WORD = Pattern.compile("(\\w)+(?=\\s|\\n|$)");
    public final static Pattern PATTERN_URL = Pattern.compile("(https|http|attachment)(://)(.+[^\\s])([.])(.+[^\\s])");
    public final static Pattern PATTERN_INVITE = Pattern.compile("(?i)(discord\\.gg/)([A-Za-z0-9]{5,})(?=\\b|$)");
    public final static Pattern PATTERN_EMOJI = Pattern.compile(
            "(?:[\\u2700-\\u27bf]|(?:\\ud83c[\\udde6-\\uddff]){2}|[\\ud800-\\udbff][\\udc00-\\udfff]|[\\u0023-\\u0039]\\ufe0f?\\u20e3|\\u3299|\\u3297|\\u303d|\\u3030|\\u24c2|\\ud83c[\\udd70-\\udd71]|\\ud83c[\\udd7e-\\udd7f]|\\ud83c\\udd8e|\\ud83c[\\udd91-\\udd9a]|\\ud83c[\\udde6-\\uddff]|[\\ud83c[\\ude01-\\ude02]|\\ud83c\\ude1a|\\ud83c\\ude2f|[\\ud83c[\\ude32-\\ude3a]|[\\ud83c[\\ude50-\\ude51]|\\u203c|\\u2049|[\\u25aa-\\u25ab]|\\u25b6|\\u25c0|[\\u25fb-\\u25fe]|\\u00a9|\\u00ae|\\u2122|\\u2139|\\ud83c\\udc04|[\\u2600-\\u26FF]|\\u2b05|\\u2b06|\\u2b07|\\u2b1b|\\u2b1c|\\u2b50|\\u2b55|\\u231a|\\u231b|\\u2328|\\u23cf|[\\u23e9-\\u23f3]|[\\u23f8-\\u23fa]|\\ud83c\\udccf|\\u2934|\\u2935|[\\u2190-\\u21ff]]]])");

    private final IMessage message;
    private final String content;

    /* Reader's Cursor */
    private int cursor = 0;

    /* Remaining String */
    private String remain;

    public MessageProcessor(IMessage message) {
        this.message = message;
        this.content = message.getContent();
        this.remain = content;
    }

    /**
     * Jump forwards or backwards with the the given amount.
     *
     * @param amount The integer amount.
     * @return MessageProcessor for chaining.
     */
    public MessageProcessor jump(int amount) {
        return jumpTo(cursor + amount);
    }

    /**
     * Jump to a certain index.
     *
     * @param index The index.
     * @return MessageProcessor for chaining.
     */
    public MessageProcessor jumpTo(int index) {
        cursor = index;
        remain = content.substring(cursor);
        return this;
    }

    /**
     * Reset the reader, put the cursor to {@code 0}.
     *
     * @return MessageProcessor for chaining.
     */
    public MessageProcessor reset() {
        cursor = 0;
        remain = content;
        return this;
    }

    /**
     * Check if the remaining content still has character(s) or not.
     *
     * @return True if the content still has character(s).
     */
    public boolean hasNext() {
        return content.length() > cursor;
    }

    /**
     * Get the next character.
     *
     * @exception IllegalArgumentException If there is no character remain.
     * @return The next character
     */
    public char getNextChar() {
        if (!hasNext()) {
            throw new IllegalArgumentException("Next \"Char\" not found!");
        } else {
            jump(1);
            return content.charAt(cursor);
        }
    }

    /**
     * Get the next word.
     *
     * @exception IllegalArgumentException If there is no word remain.
     * @return The next word, wrapped as a {@link Token}.
     */
    public Token getNextWord() {
        if (!hasNext()) {
            throw new IllegalArgumentException("Next \"Word\" not found!");
        }
        Matcher matcher = PATTERN_WORD.matcher(remain);

        final String content;
        final int end;
        boolean found = true;
        if (!matcher.find()) {
            content = "";
            end = content.length();
            found = false;
        } else {
            content = matcher.group();
            end = matcher.end();
        }

        Token token = new Token(content, cursor, end);
        jumpTo(found ? cursor + matcher.end() : content.length());
        return token;
    }

    /**
     * Get the next line.
     * If there are no new line found, then this will return the remaining content.
     *
     * @exception IllegalArgumentException If there is no characters remain.
     * @return The next line as a token.
     */
    public Token getNextLine() {
        if (!hasNext()) {
            throw new IllegalArgumentException("Next \"Line\" not found!");
        }
        int end = remain.indexOf("\n");
        if (end == -1) { // No new line found
            end = remain.length();
        }
        Token token = new Token(content.substring(cursor, end), cursor, cursor + end);
        jumpTo(cursor + end);
        return token;
    }

    /**
     * Check if the remaining content contains the markdowns combined.
     * For example, {@link org.alienideology.jcord.handle.message.IMessage.Markdown#STRIKEOUT}
     * combined with {@link org.alienideology.jcord.handle.message.IMessage.Markdown#ITALICS} would result in
     * {@code ~~*}.
     *
     * @param markdowns The markdowns to check with.
     * @return True if the remaining content contains the markdowns.
     */
    public boolean hasNextMarkdowns(IMessage.Markdown... markdowns) {
        return remain.contains(IMessage.Markdown.getCombinedString(false, markdowns));
    }

    /**
     * Get the next content that has the markdowns.
     * For example, getting a content such as {@code ~~*Strike and italics!*~~} requires to pass {@link org.alienideology.jcord.handle.message.IMessage.Markdown#ITALICS}
     * and {@link org.alienideology.jcord.handle.message.IMessage.Markdown#STRIKEOUT} in the parameter.
     *
     * @exception IllegalArgumentException If there is no characters remain.
     * @param markdowns The markdowns to get.
     * @return The content that has the markdownds surrounding it.
     */
    public Token getNextMarkdowns(IMessage.Markdown... markdowns) {
        final String openMarkdowns = IMessage.Markdown.getCombinedString(false, markdowns);
        final int start = remain.indexOf(openMarkdowns);
        if (start == -1 || !hasNextMarkdowns(markdowns)) {
            throw new IllegalArgumentException("Next \"Markdown\" not found!");
        }

        final String newS = remain.substring(start);
        final String closeMarkdowns = IMessage.Markdown.getCombinedString(true, markdowns);
        final int end = newS.indexOf(closeMarkdowns);
        if (end == -1) {
            throw new IllegalArgumentException("Next \"Markdown\" not found!");
        }

        Token token = new Token(remain.substring(start, end), cursor + start, cursor + end);
        jumpTo(cursor + end);
        return token;
    }

    /**
     * Check if the remaining content contains a string.
     *
     * @param s The string to check with.
     * @return True if the contents contains the string.
     */
    public boolean hasNextString(String s) {
        return remain.contains(s);
    }

    /**
     * Get the next string.
     *
     * @param s The string target.
     * @return The string found.
     */
    public Token getNextString(String s) {
        if (!hasNextString(s)) {
            throw new IllegalArgumentException("Next \"Sequence\" not found!");
        }
        final int start = remain.indexOf(s);
        final int end = cursor + start + s.length();
        Token token = new Token(remain.substring(start, s.length()), cursor + start, end);
        jumpTo(cursor + end);
        return token;
    }

    /**
     * Check if the regex matches the remaining content.
     *
     * @param regex The regex.
     * @return True if they match.
     */
    public boolean matchesRegex(String regex) {
        return matchesRegex(Pattern.compile(regex));
    }

    /**
     * Check if the regex matches the remaining content.
     *
     * @param pattern The regex pattern.
     * @return True if they match.
     */
    public boolean matchesRegex(Pattern pattern) {
        return hasNext() && pattern.matcher(remain).find();
    }

    /**
     * Get the next token by a regex.
     *
     * @param pattern The regex pattern.
     * @return A regex token, contains the matcher.
     */
    public RegexToken getNextByRegex(Pattern pattern) {
        if (!matchesRegex(pattern)) {
            throw new IllegalArgumentException("Next \"Regex Match\" not found!");
        }

        Matcher matcher = pattern.matcher(remain);
        final int end = cursor + matcher.end();
        RegexToken token = new RegexToken(matcher.group(), cursor + matcher.start(), end, matcher);
        jumpTo(cursor + end);
        return token;
    }

    /**
     * Check if the remaining content contains mention or not.
     *
     * @return True if there is still a mention left.
     */
    public boolean hasNextMention() {
        return hasNext() && PATTERN_MENTIONS.matcher(remain).find();
    }

    /**
     * Get the next mention, can be any kind of mention.
     * Note that this {@link GenericToken} would use {@link String} as its generic type, and the string would be mentioned ID.
     *
     * @exception IllegalArgumentException If there is no mentions remain.
     * @return The next mention, with the mentioned object as the {@code String} ID.
     */
    public GenericToken<String> getNextMention() {
        RegexToken token = getNextByRegex(PATTERN_MENTIONS);
        return new GenericToken<>(token.getContent(), token.getStart(), token.getEnd(), token.getMatcher().group(2));
    }

    /**
     * Get the next user mention.
     * Note that user mention is different from member mention.
     * Used mention is in {@code <@ID>} format, while member mention is in {@code <@!ID>} format.
     *
     * @exception IllegalArgumentException If there is no user mention remain.
     * @return The next user mention.
     */
    public GenericToken<IUser> getNextUserMention() {
        RegexToken token = getNextByRegex(PATTERN_MENTION_USER);

        return new GenericToken<>(token.getContent(), token.getStart(), token.getEnd(),
                message.getIdentity().getUser(token.getMatcher().group(2)));
    }

    /**
     * Get the next member mention.
     * Member mentions should show nickname, and it only appears in Guild.
     *
     * @exception IllegalArgumentException If there is no member mention remain.
     * @exception NullPointerException If the message is not from a guild.
     * @return The next member mention.
     */
    public GenericToken<IMember> getNextMemberMention() {
        RegexToken token = getNextByRegex(PATTERN_MENTION_MEMBER);

        return new GenericToken<>(token.getContent(), token.getStart(), token.getEnd(),
                message.getGuild().getMember(token.getMatcher().group(2)));
    }

    /**
     * Get the next role mention.
     *
     * @exception IllegalArgumentException If there is no role mention remain.
     * @exception NullPointerException If the message is not from a guild.
     * @return The next role mention.
     */
    public GenericToken<IRole> getNextRoleMention() {
        RegexToken token = getNextByRegex(PATTERN_MENTION_ROLE);

        return new GenericToken<>(token.getContent(), token.getStart(), token.getEnd(),
                message.getGuild().getRole(token.getMatcher().group(2)));
    }

    /**
     * Get the next TextChannel mention.
     *
     * @exception IllegalArgumentException If there is no text channel mention remain.
     * @exception NullPointerException If the message is not from a guild.
     * @return The next text channel mention.
     */
    public GenericToken<ITextChannel> getNextChannelMention() {
        RegexToken token = getNextByRegex(PATTERN_MENTION_CHANNEL);

        return new GenericToken<>(token.getContent(), token.getStart(), token.getEnd(),
                message.getGuild().getTextChannel(token.getMatcher().group(2)));
    }

    /**
     * Get the next GuildEmoji mention.
     *
     * @exception IllegalArgumentException If there is no guild emoji mention remain.
     * @exception NullPointerException If the message is not from a guild.
     * @return The next guild emoji mention.
     */
    public GenericToken<IGuildEmoji> getNextGuildEmojiMention() {
        RegexToken token = getNextByRegex(PATTERN_MENTION_EMOJI);

        return new GenericToken<>(token.getContent(), token.getStart(), token.getEnd(),
                message.getGuild().getGuildEmoji(token.getMatcher().group(2)));
    }

    /**
     * Get the next emoji mention.
     *
     * @exception IllegalArgumentException If there is no emoji mention remain.
     * @return The next emoji mention.
     */
    // TODO: Test this #getNextEmoji
    public GenericToken<Emoji> getNextEmoji() {
        RegexToken token = getNextByRegex(PATTERN_EMOJI);

        return new GenericToken<>(token.getContent(), token.getStart(), token.getEnd(),
                Emojis.getByUnicode(token.getContent()));
    }

    /**
     * Get the next url.
     *
     * @exception IllegalArgumentException If there is no url remain.
     * @throws MalformedURLException When declaring URL object.
     * @return The next url.
     */
    public GenericToken<URL> getNextUrl() throws MalformedURLException {
        RegexToken token = getNextByRegex(PATTERN_INVITE);

        return new GenericToken<>(token.getContent(), token.getStart(), token.getEnd(), new URL(token.getContent()));
    }

    /**
     * Get the next invite.
     *
     * @exception IllegalArgumentException If there is no invite remain.
     * @return The next invite.
     */
    public GenericToken<String> getNextInvite() {
        RegexToken token = getNextByRegex(PATTERN_INVITE);

        return new GenericToken<>(token.getContent(), token.getStart(), token.getEnd(), token.getContent());
    }

    /**
     * Get the message this is processing.
     *
     * @return The message.
     */
    public IMessage getMessage() {
        return message;
    }

    /**
     * Get the message content.
     *
     * @return The content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Get the cursor of this processor.
     *
     * @return The cursor.
     */
    public int getCursor() {
        return cursor;
    }

    /**
     * Get the remaining content.
     * THis is equivalent to {@code #getContent()#substring(#getCursor())}.
     *
     * @return The remaining content.
     */
    public String getRemainContent() {
        return remain;
    }

    /**
     * A sequence of chars in a message.
     */
    public static class Token {

        private final String content;
        private final int start;
        private final int end;

        Token(String content, int start, int end) {
            this.content = content;
            this.start = start;
            this.end = end;
        }

        /**
         * Get the content of a token, it can be a word, line, or mention.
         *
         * @return The token content.
         */
        public String getContent() {
            return content;
        }

        /**
         * Get the starting index of this token.
         *
         * @return The index.
         */
        public int getStart() {
            return start;
        }

        /**
         * Get the ending index of this token.
         *
         * @return The index.
         */
        public int getEnd() {
            return end;
        }

    }

    /**
     * A token that is matched/found by a regex.
     */
    public static class RegexToken extends Token {

        private Matcher matcher;

        RegexToken(String content, int start, int end, Matcher matcher) {
            super(content, start, end);
            this.matcher = matcher;
        }

        public Matcher getMatcher() {
            return matcher;
        }

    }

    /**
     * A generified token class, used to return different mentions and types of tokens.
     *
     * @param <T> The mentioned content or object.
     */
    public final static class GenericToken<T> extends Token {

        private T mention;

        GenericToken(String content, int start, int end, T mention) {
            super(content, start, end);
            this.mention = mention;
        }

        /**
         * Get the mentioned content or object.
         *
         * @return The mention.
         */
        public T getMentioned() {
            return mention;
        }

    }

    /**
     * Process the original content of this message.
     *
     * @param noMention Should process mention or not.
     *                      Original: {@code <@ID>}
     *                      Processed: {@code @Name#Discriminator}
     *X
     * @param noMarkdown Should include markdown or not.
     *                      Original: {@code **```java\n\nhi```**}
     *                      Processed: {@code hi}
     * @return The message processed.
     */
    public String getProcessedContent(boolean noMention, boolean noMarkdown) {
        String process = content;
        if (noMention) {

        /* Message from TextChannel */
        if (!message.getChannel().isPrivate()) {
            /* Member Mentions */
            for (IMember member : message.getMentionedMembers()) {
                process = process.replaceAll(member.mention(), "@" +
                        (member.getNickname().isEmpty()?member.getUser().getName():member.getNickname())
                        +"#"+member.getUser().getDiscriminator());
            }

            /* TextChannel Mentions */
            for (ITextChannel tc : message.getGuild().getTextChannels()) {
                process = process.replaceAll(tc.mention(), "#"+tc.getName());
            }

            /* Role Mentions */
            for (IRole role : message.getMentionedRoles()) {
                process = process.replaceAll(role.mention(), "@"+role.getName());
            }

            for (IGuildEmoji emoji : message.getGuild().getGuildEmojis()) {
                process = process.replaceAll(emoji.mention(), ":"+emoji.getName()+":");
            }

        /* Message from PrivateChannel */
        } else {
            /* User Mentions */
            for (IUser user : message.getMentions()) {
                process = process.replaceAll(user.mention(), "@"+user.getName()+"#"+user.getDiscriminator());
            }
        }

        // TODO: Emojis Mentions

        }

        if (noMarkdown) {
            process = stripSimpleMarkdown(process);
//            final String codeBlock = "```";
//
//            String temp = "";
//            int lastClose = 0;
//
//            /* Code Blocks */
//            while (process.contains(codeBlock)) {
//                int open = process.indexOf(codeBlock);
//                int close = process.lastIndexOf(codeBlock);
//
//                /* Simple Markdowns */
//                temp += stripSimpleMarkdown(process.substring(lastClose, open));
//
//                System.out.println(open+"\t"+close);
//                System.out.println(process.indexOf("\n"));
//
//                // Code block is matched
//                if (open != close) {
//                    String newString = process.substring(0, open) +
//                            process.substring((process.indexOf("\n") >= close-1 || !process.contains("\n")) ? open+3 : process.indexOf("\n"), close);
//                    if (process.length() > close+3)
//                        newString += process.substring(close);
//                    temp += newString;
//                } else {
//                    break;
//                }
//                temp += stripSimpleMarkdown(process.substring(close));
//                lastClose = close;
        }
//            process = temp;

//        }
        return process;
    }

    private String stripSimpleMarkdown (String md) {
        final String[] MARKDOWNS = new String[] {"***", "**", "*", "~~", "__"};
        for (String markdown : MARKDOWNS) {
            System.out.println(markdown);
            while (md.contains(markdown)) {
                int open = md.indexOf(markdown);
                int close = md.lastIndexOf(markdown);
                // Markdown is matched
                if (open != close) {
                    String newString = md.substring(0, open) + md.substring(open+markdown.length(), close);
                    if (md.length() > close+markdown.length())
                        newString += md.substring(close);
                    System.out.println(newString);
                    md = newString;
                } else {
                    break;
                }
            }
        }
        return md;
    }

}

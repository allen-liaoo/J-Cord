package org.alienideology.jcord.internal.object.message;

import org.alienideology.jcord.handle.Buildable;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.message.IStringMessage;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.user.User;
import org.json.JSONObject;

import java.util.List;

/**
 * @author AlienIdeology
 */
public final class StringMessage extends Message implements IStringMessage, Buildable {

    public StringMessage(IdentityImpl identity, String id, User author, String content, String timeStamp,
                         List<User> mentions, List<Role> mentionedRoles, List<Attachment> attachments, boolean isTTs, boolean mentionedEveryone, boolean isPinned) {
        super(identity, id, author, content, timeStamp, mentions, mentionedRoles, attachments, isTTs, mentionedEveryone, isPinned);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject()
            .put("content", content);
        if (isTTS) json.put("tts", isTTS);
        return json;
    }

    public String processContent(boolean noMention, boolean noMarkdown) {
        String process = content;
        if (noMention) {

            /* Message from TextChannel */
            if (!channel.isPrivate()) {
                /* Member Mentions */
                for (IMember member : getMentionedMembers()) {
                    process = process.replaceAll(member.mention(), "@" +
                            (member.getNickname().isEmpty()?member.getUser().getName():member.getNickname())
                            +"#"+member.getUser().getDiscriminator());
                }

                /* TextChannel Mentions */
                for (ITextChannel tc : getGuild().getTextChannels()) {
                    process = process.replaceAll(tc.mention(), "#"+tc.getName());
                }

                /* Role Mentions */
                for (Role role : mentionedRoles) {
                    process = process.replaceAll(role.mention(), "@"+role.getName());
                }

                for (IGuildEmoji emoji : getGuild().getGuildEmojis()) {
                    process = process.replaceAll(emoji.mention(), ":"+emoji.getName()+":");
                }

            /* Message from PrivateChannel */
            } else {
                /* User Mentions */
                for (User user : mentions) {
                    process = process.replaceAll(user.mention(), "@"+user.getName()+"#"+user.getDiscriminator());
                }
            }

            // TODO: EmojiTable Mentions

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

    @Override
    public String toString() {
        return "ID: "+id+"\tContent: "+content;
    }

}

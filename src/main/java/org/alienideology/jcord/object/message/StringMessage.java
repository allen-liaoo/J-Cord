package org.alienideology.jcord.object.message;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Message;
import org.alienideology.jcord.object.guild.GuildEmoji;
import org.alienideology.jcord.object.guild.Role;
import org.alienideology.jcord.object.user.User;
import org.alienideology.jcord.object.channel.TextChannel;
import org.alienideology.jcord.object.guild.Member;

import java.util.List;

/**
 * StringMessage - Normal messages that only contains string.
 * @author AlienIdeology
 */
public class StringMessage extends Message {

    public StringMessage(Identity identity, String id, User author, String content, String timeStamp,
                         List<User> mentions, List<Role> mentionedRoles, boolean isTTs, boolean mentionedEveryone, boolean isPinned) {
        super(identity, id, author, content, timeStamp, mentions, mentionedRoles, isTTs, mentionedEveryone, isPinned);
    }

    public String processContent(boolean noMention, boolean noMarkdown) {
        String process = content;
        if (noMention) {

            /* Message from TextChannel */
            if (!channel.isPrivate()) {
                /* Member Mentions */
                for (Member member : getMentionedMembers()) {
                    process = process.replaceAll(member.mention(), "@" +
                            (member.getNickname().isEmpty()?member.getUser().getName():member.getNickname())
                            +"#"+member.getUser().getDiscriminator());
                }

                /* TextChannel Mentions */
                for (TextChannel tc : getGuild().getTextChannels()) {
                    process = process.replaceAll(tc.mention(), "#"+tc.getName());
                }

                /* Role Mentions */
                for (Role role : mentionedRoles) {
                    process = process.replaceAll(role.mention(), "@"+role.getName());
                }

                for (GuildEmoji emoji : getGuild().getGuildEmojis()) {
                    process = process.replaceAll(emoji.mention(), ":"+emoji.getName()+":");
                }

            /* Message from PrivateChannel */
            } else {
                /* User Mentions */
                for (User user : mentions) {
                    process = process.replaceAll(user.mention(), "@"+user.getName()+"#"+user.getDiscriminator());
                }
            }

            // TODO: EmojiList Mentions

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

package org.alienideology.jcord.object.message;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.User;

/**
 * StringMessage - Normal messages that can be sent by everyone.
 * @author AlienIdeology
 */
public class StringMessage extends Message {

    private String content;

    public StringMessage(Identity identity, String id, User author, String timeStamp, boolean isTTs, boolean mentionedEveryone, boolean isPinned, String content) {
        super(identity, id, author, timeStamp, isTTs, mentionedEveryone, isPinned);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    // TODO: Finish this method
    public String getProcessedContent(boolean noMention, boolean noMarkdown) {
        String process = content;
        if (noMention) {

        }
        if (noMarkdown) {

        }
        return process;
    }

    @Override
    public String toString() {
        return "ID: "+id+"\tContent: "+content;
    }
}

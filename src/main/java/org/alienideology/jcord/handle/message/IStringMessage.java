package org.alienideology.jcord.handle.message;

/**
 * StringMessage - Normal messages that only contains string.
 * @author AlienIdeology
 */
public interface IStringMessage extends IMessage {

    String processContent(boolean noMention, boolean noMarkdown);

}

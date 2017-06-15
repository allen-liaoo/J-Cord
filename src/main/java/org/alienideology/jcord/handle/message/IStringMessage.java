package org.alienideology.jcord.handle.message;

/**
 * StringMessage - Normal messages that only contains string.
 * @author AlienIdeology
 */
public interface IStringMessage extends IMessage {

    /**
     * Process the original content of this message.
     *
     * @param noMention Should process mention or not.
     *                  Process mention:
     *                      Original: <@ID>
     *                      Processed: @NickName/Username#Discriminator
     *
     * @param noMarkdown Should include markdown or not.
     *                      Original: **```java\n\nhi```**
     *                      Processed: hi
     * @return
     */
    String processContent(boolean noMention, boolean noMarkdown);

}

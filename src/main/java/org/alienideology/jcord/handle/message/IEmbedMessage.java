package org.alienideology.jcord.handle.message;

import org.alienideology.jcord.internal.object.message.EmbedMessage;

import java.awt.*;
import java.time.OffsetDateTime;

/**
 * EmbedMessage - A message that can only be sent by a bot or webhook.
 * The message have a special block embed surround it.
 * @author AlienIdeology
 */
public interface IEmbedMessage extends IMessage {

    String getTitle();

    String getDescription();

    String getUrl();

    OffsetDateTime getTimeStamp();

    Color getColor();

    EmbedMessage.Author getAuthorField();

    java.util.List<EmbedMessage.Field> getFields();

    EmbedMessage.Thumbnail getThumbnail();

    EmbedMessage.Video getVideo();

    EmbedMessage.Provider getProvider();

    EmbedMessage.Image getImage();

    EmbedMessage.Footer getFooter();

}

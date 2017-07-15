package org.alienideology.jcord.handle.client;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.user.OnlineStatus;

import java.util.List;
import java.util.Locale;

/**
 * IClientSetting - The app settings of a Discord client.
 *
 * @author AlienIdeology
 */
public interface IClientSetting extends IClientObject {

    /**
     * Get the online status of this client.
     * This will essentially be the same as {@link Identity#getSelf()#getStatus()}.
     *
     * @return The status.
     */
    OnlineStatus getStatus();

    /**
     * Get the locale language of this client.
     *
     * @return The locale.
     */
    Locale getLocale();

    /**
     * Get a list of guilds with the sorted positions listed in the client.
     *
     * @return The guild positions.
     */
    List<IGuild> getGuildsPositions();

    /**
     * Get a list of restricted guilds.
     *
     * @return The restricted guidls.
     */
    List<IGuild> getRestrictedGuilds();

    /**
     * Get if the client are allowed to show the current game.
     *
     * @return True if the client are allowed to show the current game.
     */
    boolean isShowCurrentGame();

    /**
     * Get if the client have the developer mode enabled.
     *
     * @return True if the client have the developer mode enabled.
     */
    boolean isDeveloperMode();

    /**
     * Get if the message is compactly displayed on the client.
     * The message is either displayed as {@code Cozy} or {#code Compact}.
     *
     * @return True if the message is compactly displayed on the client.
     */
    boolean isMessageDisplayCompact();

    /**
     * Get if the TTS is enabled for the client.
     *
     * @return True if the TTS is enabled for the client.
     */
    boolean isEnableTTS();

    /**
     * Get if the client automatically converted emoticons to Discord emojis.
     *
     * @return True if the client automatically converted emoticons to Discord emojis.
     */
    boolean isConvertEmoticons();

    /**
     * Get if the client show reactions on messages.
     *
     * @return True if the client render reactions.
     */
    boolean isRenderReaction();

    /**
     * Get if the client show embeds on messages, such as Rich embeds or link preview.
     *
     * @return True if the client show embeds on messages.
     */
    boolean isRenderEmbeds();

    /**
     * Get if the client inline embed media.
     * @return True if the client inline embed media.
     */
    boolean isInlineEmbedMedia();

    /**
     * Get if the client inline attachment media.
     *
     * @return True if the client inline attachment media.
     */
    boolean isInlineAttachmentMedia();

    /**
     * Get if the client automatically detects other platform accounts on the computer.
     *
     * @return True if the client automatically detects other platform accounts.
     */
    boolean isDetectPlatformAccounts();
    
}

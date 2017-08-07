package org.alienideology.jcord.handle.client.setting;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.user.OnlineStatus;

import java.time.ZoneId;
import java.util.List;

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
     * Get the time zone of this client.
     *
     * @return The time zone.
     */
    ZoneId getTimeZone();

    /**
     * Get the client user interface's theme.
     *
     * @return The theme.
     */
    IClientSetting.Theme getTheme();

    /**
     * Get the locale language of this client.
     *
     * @return The locale.
     */
    IClientSetting.Locale getLocale();

    /**
     * Get the Explicit Content Filter Level for scanning direct messages.
     *
     * @return The content filter level.
     */
    IClientSetting.ContentFilterLevel getContentFilterLevel();

    /**
     * Get the afk timeout for Discord starting to send push notifications to mobile devices.
     *
     * @return The afk timeout.
     */
    IClientSetting.PushNotificationAFKTimeout getPushNotificationAFKTimeout();

    /**
     * Get the friend sources that are allowed to add this client as friend.
     * If {@link IClientSetting.FriendSource#EVERYONE} are allowed,
     * then both {@link IClientSetting.FriendSource#FRIENDS_OF_FRIENDS} and {@link IClientSetting.FriendSource#SERVER_MEMBERS} are allowed.
     *
     * @return The friend sources.
     */
    IClientSetting.FriendSource[] getFriendSources();

    /**
     * Get a list of guilds with the sorted positions listed in the client.
     *
     * @return The guild positions.
     */
    List<IGuild> getGuildsPositions();

    /**
     * Get a list of guilds that their server members are not allowed to send direct message to the client.
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
     * Get if the client restricted every guild by default.
     * A restricted guild is a guild that its server members are not allowed to send direct message to the client.
     * @see #getRestrictedGuilds()
     *
     * @return True if the client restricted every guild by default.
     */
    boolean isGuildRestrictedByDefault();

    /**
     * Get if the message is compactly displayed on the client.
     * The message is either displayed as {@code Cozy} or {#code Compact}.
     *
     * @return True if the message is compactly displayed on the client.
     */
    boolean isMessageDisplayCompact();

    /**
     * Get if the client automatically detects other platform accounts on the computer.
     *
     * @return True if the client automatically detects other platform accounts.
     */
    boolean isDetectPlatformAccounts();

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
     * Different themes of a client
     */
    enum Theme {

        LIGHT ("light"),
        DARK ("dark"),
        UNKNOWN ("unknown");

        public final String key;

        Theme(String key) {
            this.key = key;
        }

        public static Theme getByKey(String key) {
            for (Theme theme : values()) {
                if (theme.key.equals(key))
                    return theme;
            }
            return UNKNOWN;
        }

    }

    /**
     * Different language translations of the client UI.
     */
    enum Locale {
        BULGARIAN ("bg"),
        CZECH ("cs"),
        DANISH ("da"),
        DUTCH ("nl"),
        ENGLISH_UK ("en-GB"),
        ENGLISH_US ("en-US"),
        FINNISH ("fi"),
        FRENCH ("fr"),
        GERMAN ("de"),
        ITALIAN ("it"),
        JAPANESE ("ja"),
        KOREAN ("ko"),
        NORWEGIAN ("no"),
        POLISH ("pl"),
        PORTUGUESE_BRAZILIAN ("pt-BR"),
        RUSSIAN ("ru"),
        SPANISH ("es-ES"),
        SWEDISH ("sv-SE"),
        TRADITIONAL_CHINESE ("zh-TW"),
        TURKISH ("tr"),
        UKRAINIAN ("uk"),
        UNKNOWN("unknown");

        public final String key;

        Locale(String key) {
            this.key = key;
        }

        public static Locale getByKey(String key) {
            for (Locale locale : values()) {
                if (locale.key.equals(key))
                    return locale;
            }
            return UNKNOWN;
        }

    }

    /**
     * The Explicit Content Filter Level for scanning direct messages.
     */
    enum ContentFilterLevel {
        DISABLE (0),
        SCAN_FRIEND_ONLY (1),
        SCAN_EVERYONE (2),
        UNKNOWN (-1);

        public final int key;

        ContentFilterLevel(int key) {
            this.key = key;
        }

        public static ContentFilterLevel getByKey(int key) {
            for (ContentFilterLevel level : values()) {
                if (level.key == key)
                    return level;
            }
            return UNKNOWN;
        }

    }

    /**
     * Settings to allow certain kind of people to send the client a friend request.
     *
     * Note that if {@link #EVERYONE} are allowed, then both {@link #FRIENDS_OF_FRIENDS} and {@link #SERVER_MEMBERS} are allowed.
     */
    enum FriendSource {
        EVERYONE ("all"),
        FRIENDS_OF_FRIENDS ("mutual_friends"),
        SERVER_MEMBERS ("mutual_guilds"),
        UNKNOWN ("unknown");

        public final String key;

        FriendSource(String key) {
            this.key = key;
        }

        public static FriendSource getByKey(String key) {
            for (FriendSource source : values()) {
                if (source.key.equals(key))
                    return source;
            }
            return UNKNOWN;
        }

    }

    /**
     * The Desktop client's AFK Timeout before Discord start sending push notifications to mobile devices.
     */
    enum PushNotificationAFKTimeout {

        MINUTE_1 (60),
        MINUTES_2 (120),
        MINUTES_3 (180),
        MINUTES_4 (240),
        MINUTES_5 (300),
        MINUTES_6 (360),
        MINUTES_7 (420),
        MINUTES_8 (480),
        MINUTES_9 (540),
        MINUTES_10 (600),
        UNKNOWN (-1);

        public final int key;

        PushNotificationAFKTimeout(int key) {
            this.key = key;
        }

        public static PushNotificationAFKTimeout getByKey(int key) {
            for (PushNotificationAFKTimeout timeout : values()) {
                if (timeout.key == key)
                    return timeout;
            }
            return UNKNOWN;
        }

    }
    
}

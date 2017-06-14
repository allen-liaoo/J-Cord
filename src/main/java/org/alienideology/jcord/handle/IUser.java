package org.alienideology.jcord.handle;

public interface IUser {

    /**
     * Gets the formatted mention of the user. Same as IUser#mention(true)
     *
     * @return The formatted String.
     */
    String mention();

    /**
     * Gets the formatted mention of the user
     *
     * @param val Include the nickname or not/
     * @return The formatted String.
     */
    String mention(boolean val);
}

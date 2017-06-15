package org.alienideology.jcord.handle;

import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
@FunctionalInterface
public interface Buildable {

    /**
     * Build an json object, used for building an object and sent it to Discord Gateway.
     *
     * @return The json object built.
     */
    JSONObject toJson();

}

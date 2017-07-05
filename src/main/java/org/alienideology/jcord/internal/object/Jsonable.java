package org.alienideology.jcord.internal.object;

import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
@FunctionalInterface
public interface Jsonable {

    /**
     * Build an json object, used for building an object and sent it to Discord Gateway.
     *
     * @return The json object built.
     */
    JSONObject toJson();

}

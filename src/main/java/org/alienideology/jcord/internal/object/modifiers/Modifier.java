package org.alienideology.jcord.internal.object.modifiers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.modifiers.Attribute;
import org.alienideology.jcord.handle.modifiers.IModifier;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author AlienIdeology
 */
public abstract class Modifier<R> extends DiscordObject implements IModifier<R> {

    protected List<Attribute> attributes = new ArrayList<>();

    public Modifier(Identity identity) {
        super(identity);
    }

    @Override
    public abstract R modify();

    @Override
    public void reset() {
        attributes.forEach(Attribute::reset);
    }

    @Override
    public boolean needUpdate() {
        return attributes.stream().filter(Attribute::needUpdate).count() == attributes.size();
    }

    //-------------------------Internal-------------------------

    protected abstract void setupAttributes();

    // All modifiers must set the values first by using setupAttributes()
    protected void setAttributes(Attribute... attributes) {
        this.attributes = Arrays.asList(attributes);
    }

    // In the method modify(), call this to get a general updatable json.
    // Still need to specify some enumerations since this put ket with the enum itself.
    protected JSONObject getUpdatableJson() {
        JSONObject json = new JSONObject();
        attributes.stream()
                .filter(Attribute::needUpdate)
                .forEach(attr -> json.put(attr.getKey(), attr.getAltValue()));
        return json;
    }

}

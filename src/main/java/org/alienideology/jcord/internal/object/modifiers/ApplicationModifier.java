package org.alienideology.jcord.internal.object.modifiers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.app.IApplication;
import org.alienideology.jcord.handle.modifiers.Attribute;
import org.alienideology.jcord.handle.modifiers.IApplicationModifier;
import org.alienideology.jcord.handle.modifiers.attr.IconAttribute;
import org.alienideology.jcord.internal.object.client.app.Application;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * @author AlienIdeology
 */
public final class ApplicationModifier extends Modifier<Void> implements IApplicationModifier {

    private Application application;

    private Attribute<IApplicationModifier, String> nameAttr;
    private IconAttribute<IApplicationModifier> iconAttr;
    private Attribute<IApplicationModifier, String> descriptionAttr;
    private Attribute<IApplicationModifier, List<String>> redirectUrisAttr;
    private Attribute<IApplicationModifier, Boolean> isBotPublicAttr;
    private Attribute<IApplicationModifier, Boolean> requireCodeGrantAttr;

    public ApplicationModifier(Application application) {
        super(application.getIdentity());
        this.application = application;
        setupAttributes();
    }

    @Override
    public IClient getClient() {
        return application.getClient();
    }

    @Override
    public IApplication getApplication() {
        return application;
    }

    @Override
    public IApplicationModifier name(String name) {
        return nameAttr.setValue(name);
    }

    @Override
    public IApplicationModifier icon(Icon icon) {
        return iconAttr.setValue(icon);
    }

    @Override
    public IApplicationModifier description(String description) {
        return descriptionAttr.setValue(description);
    }

    @Override
    public IApplicationModifier redirectUris(List<String> redirectUris) {
        return redirectUrisAttr.setValue(redirectUris);
    }

    @Override
    public IApplicationModifier isBotPublic(boolean isBotPublic) {
        return isBotPublicAttr.setValue(isBotPublic);
    }

    @Override
    public IApplicationModifier requireCodeGrant(boolean requireCodeGrant) {
        return requireCodeGrantAttr.setValue(requireCodeGrant);
    }

    @Override
    public Attribute<IApplicationModifier, String> getNameAttr() {
        return nameAttr;
    }

    @Override
    public IconAttribute<IApplicationModifier> getIconAttr() {
        return iconAttr;
    }

    @Override
    public Attribute<IApplicationModifier, String> getDescriptionAttr() {
        return descriptionAttr;
    }

    @Override
    public Attribute<IApplicationModifier, List<String>> getRedirectUrisAttr() {
        return redirectUrisAttr;
    }

    @Override
    public Attribute<IApplicationModifier, Boolean> getIsBotPublicAttr() {
        return isBotPublicAttr;
    }

    @Override
    public Attribute<IApplicationModifier, Boolean> getRequireCodeGrantAttr() {
        return requireCodeGrantAttr;
    }

    @Override
    public Void modify() {
        JSONObject withName = getUpdatableJson();
        // Need to have a name field, otherwise server will response with 400 BAD REQUEST
        if (!withName.has("name")) {
            withName.put("name", nameAttr.getAltValue());
        }

        JSONObject updatedApp = new Requester(getIdentity(), HttpPath.Application.MODIFY_APPLICATION)
                .request(application.getId())
                .updateRequestWithBody(request -> request.body(withName))
                .getAsJSONObject();

        // Set individual fields, due to the application is not cached and tracked by an event
        if (nameAttr.needUpdate()) {
            application.setName(nameAttr.getNewValue());
        }
        if (iconAttr.needUpdate()) {
            application.setIcon(updatedApp.isNull("icon") ? null : updatedApp.getString("icon"));
        }
        if (descriptionAttr.needUpdate()) {
            application.setDescription(descriptionAttr.getNewValue());
        }
        if (redirectUrisAttr.needUpdate()) {
            application.setRedirectUris(redirectUrisAttr.getNewValue());
        }
        if (isBotPublicAttr.needUpdate()) {
            application.setPublicBot(isBotPublicAttr.getNewValue());
        }
        if (requireCodeGrantAttr.needUpdate()) {
            application.setRequireCodeGrant(requireCodeGrantAttr.getNewValue());
        }

        reset(); // Reset all fields
        return null;
    }

    @Override
    protected void setupAttributes() {
        nameAttr = new Attribute<IApplicationModifier, String>("name", this, application::getName) {
            @Override
            public void checkValue(String value) throws IllegalArgumentException {
                if (!IApplication.isValidName(value)) {
                    throw new IllegalArgumentException("Invalid application name!");
                }
            }

            @Override
            public Object getAltValue() {
                if (newValue == null)
                    newValue = "";
                return super.getAltValue();
            }
        };
        iconAttr = new IconAttribute<>("icon", this);
        descriptionAttr = new Attribute<IApplicationModifier, String>("description", this, application::getDescription) {
            @Override
            public void checkValue(String value) throws IllegalArgumentException {
                if (!IApplication.isValidDescription(value)) {
                    throw new IllegalArgumentException("Invalid application description!");
                }
            }

            @Override
            public Object getAltValue() {
                if (newValue == null)
                    newValue = "";
                return super.getAltValue();
            }
        };
        redirectUrisAttr = new Attribute<IApplicationModifier, List<String>>("redirect_uris", this, application::getRedirectUris) {
            @Override
            public void checkValue(List<String> value) throws IllegalArgumentException {
                // TODO: Validate URI for set redirect_uris
            }

            @Override
            public Object getAltValue() {
                List<String> val =  newValue == null ? getOldValue() : newValue;
                return new JSONArray(val);
            }
        };
        isBotPublicAttr = new Attribute<IApplicationModifier, Boolean>("bot_public", this, application::isPublicBot) {
            @Override
            public void checkValue(Boolean value) throws IllegalArgumentException {}
        };
        requireCodeGrantAttr = new Attribute<IApplicationModifier, Boolean>("bot_require_code_grant", this, application::requireCodeGrant) {
            @Override
            public void checkValue(Boolean value) throws IllegalArgumentException {}
        };
        addAttributes(nameAttr, iconAttr, descriptionAttr, redirectUrisAttr, isBotPublicAttr, requireCodeGrantAttr);
    }
}

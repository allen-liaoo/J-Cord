package org.alienideology.jcord.internal.object.modifiers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.modifiers.IAttribute;
import org.alienideology.jcord.handle.modifiers.ISelfModifier;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;

/**
 * @author AlienIdeology
 */
public final class SelfModifier extends Modifier<Void> implements ISelfModifier {

    private Attribute<ISelfModifier, String> usernameAttr;
    private Attribute<ISelfModifier, Icon> avatarAttr;

    public SelfModifier(IdentityImpl identity) {
        super(identity);
        setupAttributes();
    }

    @Override
    public IUser getSelf() {
        return identity.getSelf();
    }

    @Override
    public ISelfModifier name(String name) {
        usernameAttr.setValue(name);
        return this;
    }

    @Override
    public ISelfModifier avatar(Icon icon) {
        avatarAttr.setValue(icon);
        return this;
    }

    @Override
    public IAttribute<ISelfModifier, String> getUsernameAttr() {
        return usernameAttr;
    }

    @Override
    public IAttribute<ISelfModifier, Icon> getAvatarAttr() {
        return avatarAttr;
    }

    @Override
    public Void modify() {
        new Requester(identity, HttpPath.User.MODIFY_CURRENT_USER)
                .request()
                .updateRequestWithBody(request -> request.body(getUpdatableJson()))
                .performRequest();

        updateAttributes();
        return null;
    }

    @Override
    protected void setupAttributes() {
        usernameAttr = new Attribute<ISelfModifier, String>("username", this, identity.getSelf().getName()) {
            @Override
            public void checkValue(String value) {
                if (!IUser.isValidUsername(value)) {
                    throw new IllegalArgumentException("The username is not valid!");
                }
            }

            @Override
            public Object getAltValue() {
                if (newValue == null)
                    newValue = "";
                return super.getAltValue();
            }
        };
        avatarAttr = new Attribute<ISelfModifier, Icon>("avatar", this, null) {
            @Override
            public void checkValue(Icon value) {}

            @Override
            public Icon getOldValue() {
                throw new UnsupportedOperationException("Use IUser#getAvatarUrl instead!");
            }

            @Override
            public Object getAltValue() {
                if (newValue == null)
                    newValue = Icon.DEFAULT_ICON;
                return needUpdate() ? getNewValue().getData() : null;
            }

            @Override
            public boolean needUpdate() {
                return isChanged();
            }
        };

        setAttributes(usernameAttr, avatarAttr);
    }
}

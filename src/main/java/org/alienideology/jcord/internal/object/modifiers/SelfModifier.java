package org.alienideology.jcord.internal.object.modifiers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.modifiers.Attribute;
import org.alienideology.jcord.handle.modifiers.ISelfModifier;
import org.alienideology.jcord.handle.modifiers.attr.IconAttribute;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;

/**
 * @author AlienIdeology
 */
public final class SelfModifier extends Modifier<Void> implements ISelfModifier {

    private Attribute<ISelfModifier, String> usernameAttr;
    private IconAttribute<ISelfModifier> avatarAttr;

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
        return usernameAttr.setValue(name);
    }

    @Override
    public ISelfModifier avatar(Icon icon) {
        return avatarAttr.setValue(icon);
    }

    @Override
    public Attribute<ISelfModifier, String> getUsernameAttr() {
        return usernameAttr;
    }

    @Override
    public IconAttribute<ISelfModifier> getAvatarAttr() {
        return avatarAttr;
    }

    @Override
    public Void modify() {
        new Requester(identity, HttpPath.User.MODIFY_CURRENT_USER)
                .request()
                .updateRequestWithBody(request -> request.body(getUpdatableJson()))
                .performRequest();

        reset();
        return null;
    }

    @Override
    protected void setupAttributes() {
        usernameAttr = new Attribute<ISelfModifier, String>("username", this, identity.getSelf()::getName) {
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
        avatarAttr = new IconAttribute<>("avatar", this);

        addAttributes(usernameAttr, avatarAttr);
    }
}

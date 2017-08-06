package org.alienideology.jcord.internal.object.modifiers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.modifiers.IAttribute;
import org.alienideology.jcord.handle.modifiers.IGroupModifier;
import org.alienideology.jcord.internal.object.channel.Group;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;

/**
 * @author AlienIdeology
 */
public final class GroupModifier extends Modifier<Void> implements IGroupModifier {

    private Group group;
    private Attribute<IGroupModifier, String> nameAttr;
    private Attribute<IGroupModifier, Icon> iconAttr;

    public GroupModifier(Group group) {
        super(group.getIdentity());
        this.group = group;
        setupAttributes();
    }

    @Override
    public IClient getClient() {
        return group.getClient();
    }

    @Override
    public IGroup getGroup() {
        return group;
    }

    @Override
    public IGroupModifier name(String name) {
        nameAttr.setValue(name);
        return this;
    }

    @Override
    public IGroupModifier icon(Icon icon) {
        iconAttr.setValue(icon);
        return this;
    }

    public IAttribute<IGroupModifier, String> getNameAttr() {
        return nameAttr;
    }

    public IAttribute<IGroupModifier, Icon> getIconAttr() {
        return iconAttr;
    }

    @Override
    public Void modify() {
        new Requester(getIdentity(), HttpPath.Channel.MODIFY_CHANNEL)
                .request(group.getId())
                .updateRequestWithBody(request -> request.body(getUpdatableJson()))
                .performRequest();

        updateAttributes(); // Update all attributes
        return null;
    }

    @Override
    protected void setupAttributes() {
        nameAttr = new Attribute<IGroupModifier, String>("name", this, group.getName()) {
            @Override
            public void checkValue(String value) {
                if (!IGroup.isValidName(value)) {
                    throw new IllegalArgumentException("Invalid group name!");
                }
            }

            @Override
            public Object getAltValue() {
                if (newValue == null)
                    newValue = "";
                return super.getAltValue();
            }
        };
        iconAttr = new Attribute<IGroupModifier, Icon>("icon", this, null) {
            @Override
            public void checkValue(Icon value) {}

            @Override
            public Icon getOldValue() {
                throw new UnsupportedOperationException("Use IGuild#getIconUrl instead!");
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

        setAttributes(nameAttr, iconAttr);
    }

}

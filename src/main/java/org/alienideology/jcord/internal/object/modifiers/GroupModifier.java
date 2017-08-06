package org.alienideology.jcord.internal.object.modifiers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.modifiers.Attribute;
import org.alienideology.jcord.handle.modifiers.IGroupModifier;
import org.alienideology.jcord.handle.modifiers.attr.IconAttribute;
import org.alienideology.jcord.internal.object.channel.Group;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;

/**
 * @author AlienIdeology
 */
public final class GroupModifier extends Modifier<Void> implements IGroupModifier {

    private Group group;
    private Attribute<IGroupModifier, String> nameAttr;
    private IconAttribute<IGroupModifier> iconAttr;

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

    public Attribute<IGroupModifier, String> getNameAttr() {
        return nameAttr;
    }

    public IconAttribute<IGroupModifier> getIconAttr() {
        return iconAttr;
    }

    @Override
    public Void modify() {
        new Requester(getIdentity(), HttpPath.Channel.MODIFY_CHANNEL)
                .request(group.getId())
                .updateRequestWithBody(request -> request.body(getUpdatableJson()))
                .performRequest();

        reset(); // Reset all attributes
        return null;
    }

    @Override
    protected void setupAttributes() {
        nameAttr = new Attribute<IGroupModifier, String>("name", this, group::getName) {
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
        iconAttr = new IconAttribute<>("icon", this, null);

        setAttributes(nameAttr, iconAttr);
    }

}

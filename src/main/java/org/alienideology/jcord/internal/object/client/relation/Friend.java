package org.alienideology.jcord.internal.object.client.relation;

import org.alienideology.jcord.handle.client.relation.IFriend;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class Friend extends Relationship implements IFriend {

    public Friend(Client client, Type type, User user) {
        super(client, type, user);
    }

}

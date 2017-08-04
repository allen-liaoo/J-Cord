package org.alienideology.jcord.internal.object.client.relation;

import org.alienideology.jcord.handle.client.relation.IBlockedUser;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class BlockedUser extends Relationship implements IBlockedUser {

    public BlockedUser(Client client, Type type, User user) {
        super(client, type, user);
    }

}

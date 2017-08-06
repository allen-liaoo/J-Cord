package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.client.IProfile;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public final class Profile extends User implements IProfile {

    private Client client;

    private boolean onMobile;
    private boolean isPremium;

    public Profile(Client client, IUser user, boolean onMobile, boolean isPremium) {
        super(user.getIdentity(), user.getId());
        this.client = client;
        this.onMobile = onMobile;
        this.isPremium = isPremium;
        super.setName(user.getName())
                .setDiscriminator(user.getDiscriminator())
                .setAvatar(user.getAvatarHash())
                .setEmail(user.getEmail())
                .setBot(user.isBot())
                .setWebHook(user.isWebHook())
                .setVerified(user.isVerified())
                .setMFAEnabled(user.isMFAEnabled());
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public boolean onMobile() {
        return onMobile;
    }

    @Override
    public boolean isPremium() {
        return isPremium;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", discriminator='" + discriminator + '\'' +
                ", onMobile=" + onMobile +
                ", isPremium=" + isPremium +
                '}';
    }
}

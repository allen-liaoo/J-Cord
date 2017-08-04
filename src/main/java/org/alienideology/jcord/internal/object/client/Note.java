package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.client.INote;
import org.alienideology.jcord.handle.user.IUser;

/**
 * @author AlienIdeology
 */
public final class Note extends ClientObject implements INote {

    private final String id;

    private IUser user;
    private String content;

    public Note(Client client, String userId, String content) {
        super(client);
        this.id = userId;
        this.user = client.getIdentity().getUser(userId);
        this.content = content;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        if (!super.equals(o)) return false;

        Note note = (Note) o;

        if (user != null ? !user.equals(note.user) : note.user != null) return false;
        return content.equals(note.content);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + content.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", content='" + content + '\'' +
                '}';
    }
}

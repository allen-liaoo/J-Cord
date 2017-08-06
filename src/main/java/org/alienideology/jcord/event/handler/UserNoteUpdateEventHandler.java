package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.client.note.NoteAddEvent;
import org.alienideology.jcord.event.client.note.NoteRemoveEvent;
import org.alienideology.jcord.event.client.note.NoteUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.Note;
import org.json.JSONObject;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class UserNoteUpdateEventHandler extends EventHandler {

    public UserNoteUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Client client = identity.getClient();
        String id = json.getString("id");
        String newContent = json.getString("note");
        Note note = (Note) client.getNote(id);

        ObjectBuilder clientBuilder = new ObjectBuilder(client);
        if (note == null) {
            dispatchEvent(new NoteAddEvent(client, sequence, clientBuilder.buildNote(id, newContent)));
        } else if (newContent == null || newContent.isEmpty()) {
            note.setContent(null);
            client.removeNote(id);
            dispatchEvent(new NoteRemoveEvent(client, sequence, note));
        } else if (!Objects.equals(note.getContent(), newContent)) {
            String oldContent = note.getContent();
            note.setContent(newContent);
            dispatchEvent(new NoteUpdateEvent(client, sequence, note, oldContent));
        }
    }

}

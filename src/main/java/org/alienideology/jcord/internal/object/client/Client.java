package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.client.*;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlienIdeology
 */
public class Client extends DiscordObject implements IClient {

    private Profile profile;
    private ClientSetting setting;

    private List<IRelationship> relationships = new ArrayList<>();
    private List<INote> notes = new ArrayList<>();
    private List<IGuildSetting> guildSettings = new ArrayList<>();

    public Client(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public Profile getProfile() {
        return profile;
    }

    @Override
    public ClientSetting getSetting() {
        return setting;
    }

    @Override
    @Nullable
    public IRelationship getRelationship(String userId) {
        for (IRelationship relationship : relationships) {
            if (relationship.getUser().getId().equals(userId)) {
                return relationship;
            }
        }
        return null;
    }

    @Override
    public List<IRelationship> getRelationships(IRelationship.Type type) {
        return relationships.stream().filter(r -> r.getType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public List<IRelationship> getRelationships() {
        return relationships;
    }

    @Override
    @Nullable
    public INote getNote(String userId) {
        return notes.stream()
                .filter(n -> n.getUser() != null)
                .filter(n -> n.getUser().getId().equals(userId)).findFirst().orElse(null);
    }

    @Override
    public List<INote> getNotes() {
        return notes;
    }

    @Override
    public IGuildSetting getGuildSetting(String guildId) {
        for (IGuildSetting setting : guildSettings) {
            if (setting.getGuild().getId().equals(guildId)) {
                return setting;
            }
        }
        return null;
    }

    @Override
    public List<IGuildSetting> getGuildSettings() {
        return guildSettings;
    }

    @Override
    public IApplication getApplication(String id) {
        try {
            JSONObject json = new Requester((IdentityImpl) getIdentity(), HttpPath.Application.GET_APPLICATION)
                    .request(id).getAsJSONObject();
            return new ObjectBuilder(this).buildApplication(json);
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                return null;
            } else {
                throw ex;
            }
        }
    }

    @Override
    public List<IApplication> getApplications() {
        JSONArray apps = new Requester((IdentityImpl) getIdentity(), HttpPath.Application.GET_APPLICATIONS)
                .request().getAsJSONArray();
        List<IApplication> applications = new ArrayList<>();
        ObjectBuilder builder = new ObjectBuilder(this);
        for (int i = 0; i < apps.length(); i++) {
            applications.add(builder.buildApplication(apps.getJSONObject(i)));
        }
        return applications;
    }

    //---------------------Internal---------------------


    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setSetting(ClientSetting setting) {
        this.setting = setting;
    }

    public Client addRelationship(Relationship relationship) {
        relationships.add(relationship);
        return this;
    }

    public Client addNote(Note note) {
        notes.add(note);
        return this;
    }

    public Client addGuildSetting(GuildSetting setting) {
        guildSettings.add(setting);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;

        Client client = (Client) o;

        if (!relationships.equals(client.relationships)) return false;
        return guildSettings.equals(client.guildSettings);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + relationships.hashCode();
        result = 31 * result + guildSettings.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "profile=" + profile +
                ", identity=" + identity +
                '}';
    }
}

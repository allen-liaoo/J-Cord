package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.IGroup;
import org.alienideology.jcord.handle.client.INote;
import org.alienideology.jcord.handle.client.IRelationship;
import org.alienideology.jcord.handle.client.app.IApplication;
import org.alienideology.jcord.handle.client.app.IAuthApplication;
import org.alienideology.jcord.handle.client.setting.IGuildSetting;
import org.alienideology.jcord.handle.user.IConnection;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.client.setting.ClientSetting;
import org.alienideology.jcord.internal.object.client.setting.GuildSetting;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlienIdeology
 */
public final class Client extends DiscordObject implements IClient {

    private Profile profile;
    private ClientSetting setting;

    private List<IGroup> groups = new ArrayList<>();
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
    public IGroup getGroup(String id) {
        return groups.stream()
                .filter(g -> g.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public IGroup getGroupByName(String name) {
        return groups.stream()
                .filter(g -> g.getName() != null)
                .filter(g -> g.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public List<IGroup> getGroupsByOwner(String ownerId) {
        return groups.stream().filter(g -> g.getOwner().getId().equals(ownerId)).collect(Collectors.toList());
    }

    @Override
    public List<IGroup> getGroupsByUser(String userId) {
        List<IGroup> groups = new ArrayList<>();
        for (IGroup group : this.groups) {
            for (IUser user : group.getRecipients()) {
                if (user.getId().equals(userId))
                    groups.add(group);
            }
        }
        return groups;
    }

    @Override
    public List<IGroup> getGroups() {
        return groups;
    }

    @Override
    @Nullable
    public IRelationship getRelationship(String userId) {
        return relationships.stream()
            .filter(r -> r.getUser().getId().equals(userId)).findFirst().orElse(null);
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
    public List<IConnection> getConnections() {
        JSONArray cts = new Requester(identity, HttpPath.Client.GET_USER_CONNECTIONS).request().getAsJSONArray();
        List<IConnection> connections = new ArrayList<>();
        ObjectBuilder builder = new ObjectBuilder(this);
        for (int i = 0; i < cts.length(); i++) {
            connections.add(builder.buildConnection(cts.getJSONObject(i), profile));
        }
        return connections;
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

    @Override
    public IAuthApplication getAuthApplication(String id) {
        try {
            JSONObject json = new Requester((IdentityImpl) getIdentity(), HttpPath.Application.GET_AUTHORIZED_APPLICATION)
                    .request(id).getAsJSONObject();
            return new ObjectBuilder(this).buildAuthApplication(json);
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                return null;
            } else {
                throw ex;
            }
        }
    }

    @Override
    public List<IAuthApplication> getAuthApplications() {
        JSONArray apps = new Requester((IdentityImpl) getIdentity(), HttpPath.Application.GET_AUTHORIZED_APPLICATIONS)
                .request().getAsJSONArray();
        List<IAuthApplication> applications = new ArrayList<>();
        ObjectBuilder builder = new ObjectBuilder(this);
        for (int i = 0; i < apps.length(); i++) {
            applications.add(builder.buildAuthApplication(apps.getJSONObject(i)));
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

    public Client addGroup(Group group) {
        groups.add(group);
        return this;
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
    public String toString() {
        return "Client{" +
                "profile=" + profile +
                ", identity=" + identity +
                '}';
    }
}

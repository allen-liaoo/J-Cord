package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.INote;
import org.alienideology.jcord.handle.client.app.IApplication;
import org.alienideology.jcord.handle.client.app.IAuthApplication;
import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.handle.client.setting.IGuildSetting;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.managers.IClientManager;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.user.IConnection;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.channel.Group;
import org.alienideology.jcord.internal.object.client.call.CallUser;
import org.alienideology.jcord.internal.object.client.relation.Relationship;
import org.alienideology.jcord.internal.object.client.setting.ClientSetting;
import org.alienideology.jcord.internal.object.client.setting.GuildSetting;
import org.alienideology.jcord.internal.object.managers.ClientManager;
import org.alienideology.jcord.internal.rest.HttpCode;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlienIdeology
 */
public final class Client extends DiscordObject implements IClient {

    private final ClientManager manager;
    private Profile profile;
    private ClientSetting setting;

    private List<IGroup> groups = new ArrayList<>();
    private List<IRelationship> relationships = new ArrayList<>();
    private List<INote> notes = new ArrayList<>();
    private List<IGuildSetting> guildSettings = new ArrayList<>();

    private HashMap<String, CallUser> callUsers = new HashMap<>(); // ID, CallUser

    public Client(IdentityImpl identity) {
        super(identity);
        manager = new ClientManager(this);
    }

    @Override
    public IClientManager getManager() {
        return manager;
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
                .filter(g -> g.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public IGroup getGroupByName(String name) {
        return groups.stream()
                .filter(g -> g.getName() != null)
                .filter(g -> g.getName().equals(name)).findAny().orElse(null);
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
            .filter(r -> r.getUser().getId().equals(userId)).findAny().orElse(null);
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
                .filter(n -> n.getUser().getId().equals(userId)).findAny().orElse(null);
    }

    @Override
    public List<INote> getNotes() {
        return notes;
    }

    @Override
    public IGuildSetting getGuildSetting(String guildId) {
        return guildSettings.stream().filter(gs -> gs.getGuild().getId().equals(guildId)).findAny().orElse(null);
    }

    @Override
    public List<IGuildSetting> getGuildSettings() {
        return guildSettings;
    }

    // Available query params:
    // "limit" for message limit
    // "before" a certain message id
    // "guild_id" for a certain guild
    // "role" boolean for role mentions
    // "everyone" boolean for everyone mentions
    @Override
    public List<IMessage> getRecentMentions(IGuild guild) {
        JSONArray msgs = new Requester(identity, HttpPath.Client.GET_RECENT_MENTIONS)
                .updateRequest(request -> request.header("guild_id", guild == null ? null : guild.getId()))
                .request().getAsJSONArray();
        List<IMessage> messages = new ArrayList<>();
        ObjectBuilder builder = new ObjectBuilder(identity);
        for (int i = 0; i< msgs.length(); i++) {
            messages.add(builder.buildMessage(msgs.getJSONObject(i)));
        }
        return messages;
    }

    @Override
    public List<IMessage> getRecentMentions() {
        return getRecentMentions(null);
    }

    @Override
    public IApplication getApplication(String id) {
        try {
            JSONObject json = new Requester(identity, HttpPath.Application.GET_APPLICATION)
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
        JSONArray apps = new Requester(identity, HttpPath.Application.GET_APPLICATIONS)
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
            JSONObject json = new Requester(identity, HttpPath.Application.GET_AUTHORIZED_APPLICATION)
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
        JSONArray apps = new Requester(getIdentity(), HttpPath.Application.GET_AUTHORIZED_APPLICATIONS)
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

    public void addGroup(Group group) {
        if (groups.contains(group)) return;
        groups.add(group);
    }

    public void addRelationship(Relationship relationship) {
        if (relationships.contains(relationship)) return;
        relationships.add(relationship);
    }

    public void updateRelationship(Relationship relationship) {
        for (IRelationship relationship1 : relationships) {
            if (relationship1.getUser().equals(relationship.getUser())) {
                relationship1 = relationship;
                return;
            }
        }
    }

    public void addNote(Note note) {
        if (notes.contains(note)) return;
        notes.add(note);
    }

    public void updateNote(Note note) {
        for (INote note1 : notes) {
            if (note1.getUser() != null && note1.getUser().equals(note.getUser())) {
                note1 = note;
                return;
            }
        }
    }

    public Group removeGroup(String groupId) {
        Group group = (Group) getGroup(groupId);
        this.groups.remove(group);
        return group;
    }

    public Note removeNote(String userId) {
        Note note = (Note) getNote(userId);
        this.notes.remove(note);
        return note;
    }

    public void addGuildSetting(GuildSetting setting) {
        if (guildSettings.contains(setting)) return;
        guildSettings.add(setting);
    }

    public HashMap<String, CallUser> getCallUsers() {
        return callUsers;
    }

    @Override
    public String toString() {
        return "Client{" +
                "profile=" + profile +
                ", identity=" + identity +
                '}';
    }
}

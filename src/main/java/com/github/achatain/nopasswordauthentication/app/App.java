package com.github.achatain.nopasswordauthentication.app;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.apache.commons.codec.binary.Hex;

@Cache
@Entity
public class App {

    @Id
    private String id;

    @Index
    private String owner;

    private String name;

    @Index
    private byte apiToken[];

    private String callbackUrl;

    private String emailTemplate;

    public App() {
        // required by Objectify
    }

    public App(String id, String owner, String name, byte apiToken[], String callbackUrl, String emailTemplate) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.apiToken = apiToken;
        this.callbackUrl = callbackUrl;
        this.emailTemplate = emailTemplate;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public byte[] getApiToken() {
        return apiToken;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    @Override
    public String toString() {
        return "App{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", apiToken='" + Hex.encodeHexString(apiToken) + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", emailTemplate='" + emailTemplate + '\'' +
                '}';
    }
}

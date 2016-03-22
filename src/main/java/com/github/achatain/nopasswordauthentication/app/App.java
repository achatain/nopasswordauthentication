package com.github.achatain.nopasswordauthentication.app;

import com.github.achatain.nopasswordauthentication.utils.TokenUtils;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.apache.commons.codec.binary.Hex;

@Cache
@Entity
public class App {

    @Id
    private Long id;

    @Index
    private String ownerEmail;

    private String name;

    @Index // FIXME blob can not be indexed... Consider storing as a hex String
    private byte apiToken[];

    private String callbackUrl;

    private String emailTemplate;

    public App() {
        // required by Objectify
    }

    public App(Builder builder) {
        this.ownerEmail = builder.ownerEmail;
        this.name = builder.name;
        this.apiToken = builder.apiToken;
        this.callbackUrl = builder.callbackUrl;
        this.emailTemplate = builder.emailTemplate;
    }

    public static Builder create() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getOwnerEmail() {
        return ownerEmail;
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
                "id=" + id +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", name='" + name + '\'' +
                ", apiToken=" + Hex.encodeHexString(apiToken).substring(0, 4).concat("*****") +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", emailTemplate='" + emailTemplate + '\'' +
                '}';
    }

    static class Builder {

        private String ownerEmail;
        private String name;
        private byte apiToken[];
        private String callbackUrl;
        private String emailTemplate;

        private Builder() {
        }

        Builder withOwnerEmail(String ownerEmail) {
            this.ownerEmail = ownerEmail;
            return this;
        }

        Builder withName(String name) {
            this.name = name;
            return this;
        }

        Builder withApiToken(String apiToken) {
            this.apiToken = TokenUtils.hash(apiToken);
            return this;
        }

        Builder withCallbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        Builder withEmailTemplate(String emailTemplate) {
            this.emailTemplate = emailTemplate;
            return this;
        }

        App build() {
            return new App(this);
        }
    }
}

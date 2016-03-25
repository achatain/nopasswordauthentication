package com.github.achatain.nopasswordauthentication.auth;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Cache
@Entity
public class Auth {

    @Id
    private String id;

    @Index
    private String userId;

    @Index
    private Long appId;

    private Long timestamp;
    private String token;

    public Auth() {
        // required by Objectify
    }

    public Auth(Builder builder) {
        this.userId = builder.userId;
        this.appId = builder.appId;
        this.timestamp = builder.timestamp;
        this.token = builder.token;
    }

    public static Builder create() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Long getAppId() {
        return appId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", appId=" + appId +
                ", timestamp=" + timestamp +
                ", token='" + token + '\'' +
                '}';
    }

    static class Builder {
        private String id;
        private String userId;
        private Long appId;
        private Long timestamp;
        private String token;

        private Builder() {
        }

        Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        Builder withAppId(Long appId) {
            this.appId = appId;
            return this;
        }

        Builder withTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        Builder withToken(String token) {
            this.token = token;
            return this;
        }

        Auth build() {
            return new Auth(this);
        }
    }
}

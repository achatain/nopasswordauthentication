/*
 * https://github.com/achatain/nopasswordauthentication
 *
 * Copyright (C) 2016 Antoine R. "achatain" (achatain [at] outlook [dot] com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

    private Auth(Builder builder) {
        this.userId = builder.userId;
        this.appId = builder.appId;
        this.timestamp = builder.timestamp;
        this.token = builder.token;
    }

    static Builder create() {
        return new Builder();
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getUserId() {
        return userId;
    }

    Long getAppId() {
        return appId;
    }

    Long getTimestamp() {
        return timestamp;
    }

    String getToken() {
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

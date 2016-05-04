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

package com.github.achatain.nopasswordauthentication.app;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import static com.github.achatain.nopasswordauthentication.utils.ExtendedStringUtils.obfuscate;

@Cache
@Entity
public class App {

    @Id
    private Long id;

    @Index
    private String ownerEmail;

    private String name;

    @Index
    private String apiToken;

    private String callbackUrl;

    private String emailTemplate;

    private Long createdTimestamp;

    public App() {
        // required by Objectify
    }

    public App(Builder builder) {
        this.ownerEmail = builder.ownerEmail;
        this.name = builder.name;
        this.apiToken = builder.apiToken;
        this.callbackUrl = builder.callbackUrl;
        this.emailTemplate = builder.emailTemplate;
        this.createdTimestamp = builder.createdTimestamp;
    }

    public static Builder builder() {
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

    public String getApiToken() {
        return apiToken;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    @Override
    public String toString() {
        return "App{" +
                "apiToken='" + obfuscate(apiToken) + '\'' +
                ", id=" + id +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", name='" + name + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", emailTemplate='" + emailTemplate + '\'' +
                ", createdTimestamp='" + createdTimestamp + '\'' +
                '}';
    }

    static class Builder {

        private String ownerEmail;
        private String name;
        private String apiToken;
        private String callbackUrl;
        private String emailTemplate;
        private Long createdTimestamp;

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
            this.apiToken = apiToken;
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
        Builder withCreatedTimestamp(Long createdTimestamp) {
            this.createdTimestamp = createdTimestamp;
            return this;
        }

        App build() {
            return new App(this);
        }
    }
}

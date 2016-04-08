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

public class AppDto {

    private String ownerEmail;
    private String name;
    private String callbackUrl;
    private String emailTemplate;

    public AppDto(Builder builder) {
        this.ownerEmail = builder.ownerEmail;
        this.name = builder.name;
        this.callbackUrl = builder.callbackUrl;
        this.emailTemplate = builder.emailTemplate;
    }

    public AppDto(App app) {
        this.ownerEmail = app.getOwnerEmail();
        this.name = app.getName();
        this.callbackUrl = app.getCallbackUrl();
        this.emailTemplate = app.getEmailTemplate();
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getName() {
        return name;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    @Override
    public String toString() {
        return "AppDto{" +
                "ownerEmail='" + ownerEmail + '\'' +
                ", name='" + name + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", emailTemplate='" + emailTemplate + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String ownerEmail;
        private String name;
        private String callbackUrl;
        private String emailTemplate;

        private Builder() {
        }

        public Builder withOwnerEmail(String ownerEmail) {
            this.ownerEmail = ownerEmail;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCallbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        public Builder withEmailTemplate(String emailTemplate) {
            this.emailTemplate = emailTemplate;
            return this;
        }

        public AppDto build() {
            return new AppDto(this);
        }
    }
}

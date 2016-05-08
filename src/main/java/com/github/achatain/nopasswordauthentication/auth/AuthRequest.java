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

import static com.github.achatain.nopasswordauthentication.utils.ExtendedStringUtils.obfuscate;

class AuthRequest {
    private String apiToken;
    private String userId;

    private AuthRequest() {
    }

    public AuthRequest(Builder builder) {
        this.apiToken = builder.apiToken;
        this.userId = builder.userId;
    }

    void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    String getApiToken() {
        return apiToken;
    }

    String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "apiToken='" + obfuscate(apiToken) + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String apiToken;
        private String userId;

        private Builder() {
        }

        public Builder withApiToken(String apiToken) {
            this.apiToken = apiToken;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public AuthRequest build() {
            return new AuthRequest(this);
        }
    }
}

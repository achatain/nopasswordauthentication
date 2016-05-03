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

class AuthVerify {
    private String apiToken;
    private String userId;
    private String token;

    private AuthVerify() {
    }

    void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getApiToken() {
        return apiToken;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "AuthVerify{" +
                "apiToken='" + obfuscate(apiToken) + '\'' +
                ", userId='" + userId + '\'' +
                ", token='" + obfuscate(token) + '\'' +
                '}';
    }
}

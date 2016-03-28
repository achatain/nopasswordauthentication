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

import com.googlecode.objectify.Key;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class AuthRepositoryImpl implements AuthRepository {

    static Key<Auth> buildAuthKey(Long appId, String userId) {
        return Key.create(Auth.class, buildCompoundName(appId, userId));
    }

    static String buildCompoundName(Long appId, String userId) {
        return String.format("%s:%s", appId, userId);
    }

    static Key<Auth> buildAuthKey(Auth auth) {
        return Key.create(Auth.class, buildCompoundName(auth));
    }

    static String buildCompoundName(Auth auth) {
        return buildCompoundName(auth.getAppId(), auth.getUserId());
    }

    @Override
    public Auth find(Long appId, String userId) {
        return ofy().load().key(buildAuthKey(appId, userId)).now();
    }

    @Override
    public void save(Auth auth) {
        auth.setId(buildCompoundName(auth.getAppId(), auth.getUserId()));
        ofy().save().entity(auth).now();
    }

    @Override
    public void delete(Auth auth) {
        ofy().delete().key(buildAuthKey(auth));
    }
}

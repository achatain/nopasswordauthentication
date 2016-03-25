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

import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class AppRepositoryImpl implements AppRepository {

    private static final Logger LOG = Logger.getLogger(AppRepositoryImpl.class.getName());

    public App findById(Long id) {
        return ofy().load().type(App.class).id(id).now();
    }

    public App findByApiToken(String apiToken) {
        return ofy().load().type(App.class).filter(AppProperties.API_TOKEN, apiToken).first().now();
    }

    public void save(App app) {
        ofy().save().entity(app).now();
        LOG.info(String.format("New client application created [%s]", app));
    }
}

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

package com.github.achatain.nopasswordauthentication.di;

import com.github.achatain.nopasswordauthentication.admin.AdminServlet;
import com.github.achatain.nopasswordauthentication.app.AppServlet;
import com.github.achatain.nopasswordauthentication.auth.AuthRequestServlet;
import com.github.achatain.nopasswordauthentication.auth.AuthVerifyServlet;
import com.github.achatain.nopasswordauthentication.di.filters.ExceptionFilter;
import com.github.achatain.nopasswordauthentication.di.filters.JsonFilter;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

class NoPasswordAuthenticationServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        // Filters
        filter("/api/*").through(ObjectifyFilter.class);
        filter("/api/*").through(JsonFilter.class);
        filter("/api/*").through(ExceptionFilter.class);

        // Servlets
        serve("/api/admin").with(AdminServlet.class);
        serve("/api/app").with(AppServlet.class);
        serve("/api/auth/request").with(AuthRequestServlet.class);
        serve("/api/auth/verify").with(AuthVerifyServlet.class);
    }
}

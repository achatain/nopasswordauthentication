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

package com.github.achatain.nopasswordauthentication.utils;

import org.apache.commons.lang3.StringUtils;

public class ExtendedStringUtils {

    private static final String STARS = "****";

    private ExtendedStringUtils() {
    }

    public static String obfuscate(String str) {
        return obfuscate(str, 4);
    }

    public static String obfuscate(String str, int visible) {
        String defaultStr = StringUtils.defaultString(str);
        if (defaultStr.length() > visible)
            return StringUtils.overlay(defaultStr, STARS, 0, defaultStr.length() - visible);
        return STARS;
    }
}

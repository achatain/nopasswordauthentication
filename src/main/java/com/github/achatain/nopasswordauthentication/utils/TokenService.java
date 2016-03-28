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

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenService {

    private static final Logger LOG = Logger.getLogger(TokenService.class.getName());

    public String generate() {
        SecureRandom sr;

        try {
            sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            LOG.log(Level.SEVERE, "Failed to initiate a secure random", e);
            throw new RuntimeException("Unable to generate an API token", e);
        }

        byte bytes[] = new byte[16];
        sr.nextBytes(bytes);

        return RandomStringUtils.random(64, 0, 0, true, true, null, sr);
    }

    public String hash(String str) {
        return Hex.encodeHexString(hash(str.getBytes()));
    }

    private byte[] hash(byte bytes[]) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            LOG.log(Level.SEVERE, "Failed to initiate a message digest", e);
            throw new RuntimeException("Unable to hash the API token", e);
        }

        return md.digest(bytes);
    }
}

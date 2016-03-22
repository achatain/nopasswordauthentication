package com.github.achatain.nopasswordauthentication.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TokenUtils {

    private static final Logger LOG = Logger.getLogger(TokenUtils.class.getName());

    private TokenUtils() {
    }

    public static String generate() {
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

    public static String hash(String str) {
        return Hex.encodeHexString(hash(str.getBytes()));
    }

    private static byte[] hash(byte bytes[]) {
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

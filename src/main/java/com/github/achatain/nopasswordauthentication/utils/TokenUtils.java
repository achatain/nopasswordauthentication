package com.github.achatain.nopasswordauthentication.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public final class TokenUtils {

    private TokenUtils() {
    }

    public static String generate() {
        SecureRandom sr;

        try {
            sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to initiate a secure random", e);
        }

        byte bytes[] = new byte[16];
        sr.nextBytes(bytes);

        return RandomStringUtils.random(32, 0, 0, true, true, null, sr);
    }

    public static byte[] hash(String str) {
        return hash(str.getBytes());
    }

    private static byte[] hash(byte bytes[]) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to initiate a message digest", e);
        }

        return md.digest(bytes);
    }
}

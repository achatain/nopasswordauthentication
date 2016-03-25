package com.github.achatain.nopasswordauthentication.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TokenUtilsTest {

    @Test
    public void shouldGenerate() throws Exception {
        assertNotNull(TokenUtils.generate());
    }

    @Test
    public void shouldHash() throws Exception {
        assertEquals("083de31ac1fa14f95671a6e39cc6c72d8fed1590b2a51759bc3f54a76b4169c4", TokenUtils.hash("Bonjour!"));
    }
}

package com.github.achatain.nopasswordauthentication.auth;

import org.apache.commons.lang3.StringUtils;

class AuthRequest {
    private String apiToken;
    private String userEmail;

    private AuthRequest() {
    }

    public String getApiToken() {
        return apiToken;
    }

    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "apiToken='" + StringUtils.leftPad(StringUtils.right(apiToken, 4), 10, '*') + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}

package com.github.achatain.nopasswordauthentication.auth;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Cache
@Entity
public class Auth {

    @Id
    private String id;


}

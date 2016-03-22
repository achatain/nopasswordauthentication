package com.github.achatain.nopasswordauthentication.app;

public interface AppRepository {

    App find(Long id);

    void save(App app);
}

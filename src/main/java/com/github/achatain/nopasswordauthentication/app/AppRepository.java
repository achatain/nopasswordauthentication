package com.github.achatain.nopasswordauthentication.app;

public interface AppRepository {

    App findById(Long id);

    App findByApiToken(String apiToken);

    void save(App app);
}

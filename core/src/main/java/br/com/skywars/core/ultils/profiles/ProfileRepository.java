package br.com.skywars.core.ultils.profiles;

public interface ProfileRepository {
    public Profile[] findProfilesByNames(String... names);
}

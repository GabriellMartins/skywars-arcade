package br.com.skywars.core.account.prefixtype;

import java.util.Arrays;

public enum PrefixType {

    BRACES,
    BRACES_UPPER,
    BRACKETS,
    BRACKETS_UPPER,
    COLOR,
    DEFAULT,
    DEFAULT_BOLD,
    DEFAULT_GRAY,
    DEFAULT_WHITE;

    public static PrefixType findByName(String name) {
        return Arrays.stream(values()).filter(prefixType -> prefixType.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}

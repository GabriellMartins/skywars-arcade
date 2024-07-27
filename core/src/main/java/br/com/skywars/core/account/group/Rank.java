package br.com.skywars.core.account.group;

import br.com.skywars.core.account.prefixtype.PrefixType;

public enum Rank {

    ADMINISTRATOR("Admin", "§4", true),
    MODERATOR_PLUS("Mod+", "§5", true),
    MODERATOR("Mod", "§5", true),
    TRIAL("Trial", "§5", true),
    HELPER("Helper", "§9", true),
    PARTNER("Partner", "§3", true),
    CREATOR("Creator", "§b", false),
    ELITE("Elite", "§c", false),
    PRO("Pro", "§6", false),
    MVP("Mvp", "§9", false),
    VIP("Vip", "§a", false),
    DEFAULT("Padrão", "§7", false);

    private final String name;
    private final String color;

    private final boolean italic;

    Rank(String name, String color, boolean italic) {
        this.name = name;
        this.color = color;
        this.italic = italic;
    }

    public boolean isItalic() {
        return italic;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getPrefix(PrefixType prefixType) {
        if (this.equals(Rank.DEFAULT)) {
            if (prefixType.equals(PrefixType.DEFAULT_WHITE))
                return "§f";

            return color;
        }
        switch (prefixType) {
            case COLOR:
                return color + (italic ? "§o" : "");
            case BRACES:
                return color + "{" + name + "} ";
            case BRACES_UPPER:
                return color + "{" + name.toUpperCase() + "} ";
            case BRACKETS:
                return color + "[" + name + "] ";
            case BRACKETS_UPPER:
                return color + "[" + name.toUpperCase() + "] ";
            case DEFAULT_BOLD:
                return color + "§l" + name.toUpperCase() + " ";
            case DEFAULT_WHITE:
                return color + "§l" + name.toUpperCase() + (name.length() > 7 ? " §f" : "§r §f");
            case DEFAULT_GRAY:
                return color + "§l" + name.toUpperCase() + (name.length() > 7 ? " §7" : "§r §7");
        }
        return color + "§l" + name.toUpperCase() + (name.length() > 7 ? " " + color : "§r " + color);
    }
}
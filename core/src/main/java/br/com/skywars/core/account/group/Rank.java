package br.com.skywars.core.account.group;

public enum Rank {

    DONO("§4§lDONO§4", br.com.skywars.core.permission.Group.Rank.DONO, false),
    NORMAL("§7", br.com.skywars.core.permission.Group.Rank.NORMAL, false);

    private String prefix;
    private br.com.skywars.core.permission.Group.Rank groupToUse;
    private boolean isExclusive;

    Rank(String prefix, br.com.skywars.core.permission.Group.Rank toUse, boolean exclusive) {
        this.prefix = prefix;
        this.groupToUse = toUse;
        this.isExclusive = exclusive;
    }



    public String getPrefix() {
        return prefix;
    }

    public br.com.skywars.core.permission.Group.Rank getGroupToUse() {
        return groupToUse;
    }

    public boolean isExclusive() {
        return isExclusive;
    }
}

package br.com.skywars.core.permission.Group;

public enum Rank {

    NORMAL(new SimpleGroup()),
    DONO(new OwnerGroup());

    private GroupInterface group;

    Rank() {
        this(new SimpleGroup());
    }

    Rank(GroupInterface group) {
        this.group = group;
    }

    public GroupInterface getGroup() {
        return group;
    }
}

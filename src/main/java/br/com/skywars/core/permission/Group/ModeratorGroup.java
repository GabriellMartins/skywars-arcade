package br.com.skywars.core.permission.Group;

import java.util.ArrayList;
import java.util.List;

public class ModeratorGroup extends GroupInterface {
    @Override
    public List<String> getPermissions() {
        List<String> permissions = new ArrayList<>();
        for (String str : new String[] { "kick" }) {
            permissions.add("minecraft.command." + str);
            permissions.add("bukkit.command." + str);
            permissions.add("matrix.notify");
        }
        return permissions;
    }
}

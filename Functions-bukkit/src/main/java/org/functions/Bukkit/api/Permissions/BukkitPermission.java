package org.functions.Bukkit.api.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionDefault;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class BukkitPermission {
    static Data data;
    static protected LinkedHashMap<String, PermissionAttachment> attachments = new LinkedHashMap<>();
    static protected LinkedHashMap<String, Permission> registeredPermissions = new LinkedHashMap<>();
    public static String noPerms(String perms) {
        return Functions.instance.getAPI().putLanguage("noPermissions", "&4你没有 %permission% 权限,所以无法使用！".replace("%permission%",perms));
    }
    public static List<String> getPermissions(UUID uuid) {
        data = new Data(uuid);
        List<String> perms = new ArrayList<>();
        Data data = new Data(uuid);
        for (String s : data.getPermissionManager().getPermissions()) {
            if (s.startsWith("functions")) {
                continue;
            }
            perms.add(s);
        }
        for (String s : data.getGroup().getPermissions()) {
            if (s.startsWith("functions")) {
                continue;
            }
            perms.add(s);
        }
        Collections.sort(perms);
        return perms;
    }

    private static Field permissions;
    public static boolean have(UUID uuid,String perms) {
        Data data = new Data(uuid);
        if (data.getPermissionManager().getPermissions()==null) {
            for (String s : data.getGroup().getPermissions()) {
                if (perms.endsWith(".*")) {
                    if (perms.replace(".*","").contains(s)) {
                        return true;
                    }
                }
                if (s.contains(perms)) {
                    return true;
                }
                return false;
            }
        } else {
            for (String s : data.getPermissionManager().getTempPermissions()) {
                if (s.contains(perms)) {
                    return true;
                }
            }
            for (String s : data.getPermissionManager().getPermissions()) {
                if (s.contains(perms)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static void updateCommands(Player player) {
        try {
            Method md = Player.class.getDeclaredMethod("updateCommands");
            Object p = Player.class.cast(player);
            md.invoke(p);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    public static boolean has(UUID uuid, String perms) {
        Data data = new Data(uuid);
        return data.getPermissionManager().equals(perms);
    }
    // Setup reflection (Thanks to Codename_B for the reflection source)
    static {
        try {
            permissions = PermissionAttachment.class.getDeclaredField("permissions");
            permissions.setAccessible(true);
        } catch (SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static boolean hasUpdateCommand;

    public static boolean hasUpdateCommand() {

        return hasUpdateCommand;
    }

    static {
    try

    {
        // Method only available post 1.14
        Player.class.getMethod("updateCommands");
        hasUpdateCommand = true;
    } catch(
    Exception ex)

    {
        // Server too old to support updateCommands.
        hasUpdateCommand = false;
    }

}
    private void removeAttachment(String uuid) {

        if (attachments.containsKey(uuid)) {
            attachments.get(uuid).remove();
            attachments.remove(uuid);
        }
    }
    public static void updatePermission(Player player) {
        PermissionAttachment attachment;
        data = new Data(player.getUniqueId());
        String uuid = player.getUniqueId().toString();
        // Find the players current attachment, or add a new one.
        if (attachments.containsKey(uuid)) {
            attachment = attachments.get(uuid);
        } else {
            attachment = player.addAttachment(Functions.instance);
            attachments.put(uuid, attachment);
        }
        List<String> playerPermArray = new ArrayList<>(getPermissions(player.getUniqueId()));
        LinkedHashMap<String, Boolean> newPerms = new LinkedHashMap<>();
        playerPermArray = sort(playerPermArray);
        boolean value;
        for (String permission : playerPermArray) {
            value = (!permission.startsWith("-"));
            newPerms.put((value ? permission : permission.substring(1)), value);
        }
        try { // Codename_B source
            synchronized (attachment.getPermissible()) {

                @SuppressWarnings("unchecked")
                Map<String, Boolean> orig = (Map<String, Boolean>) permissions.get(attachment);
                // Clear the map (faster than removing the attachment and
                // recalculating)
                orig.clear();
                // Then whack our map into there
                orig.putAll(newPerms);
                // That's all folks!
                attachment.getPermissible().recalculatePermissions();

                // Tab complete and command visibility
                // Method only available post 1.14
                if (hasUpdateCommand())
                    updateCommands(player);

            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static Map<String, Boolean> getChildren(String node) {

        Permission perm = registeredPermissions.get(node.toLowerCase());
        if (perm == null)
            return null;

        return perm.getChildren();

    }
    public static Map<String, Boolean> getAllChildren(String node, Set<String> playerPermArray) {

        LinkedList<String> stack = new LinkedList<>();
        Map<String, Boolean> alreadyVisited = new HashMap<>();
        stack.push(node);
        alreadyVisited.put(node, true);

        while (!stack.isEmpty()) {
            String now = stack.pop();

            Map<String, Boolean> children = getChildren(now);

            if ((children != null) && (!playerPermArray.contains("-" + now))) {
                for (String childName : children.keySet()) {
                    if (!alreadyVisited.containsKey(childName)) {
                        stack.push(childName);
                        alreadyVisited.put(childName, children.get(childName));
                    }
                }
            }
        }
        alreadyVisited.remove(node);
        if (!alreadyVisited.isEmpty())
            return alreadyVisited;

        return null;
    }
    private static List<String> sort(List<String> permList) {

        List<String> result = new ArrayList<>();

        for (String key : permList) {
            /*
             * Ignore stupid plugins which add empty permission nodes.
             */
            if (!key.isEmpty()) {
                String a = key.charAt(0) == '-' ? key.substring(1) : key;
                Map<String, Boolean> allchildren = getAllChildren(a, new HashSet<>());
                if (allchildren != null) {

                    ListIterator<String> itr = result.listIterator();

                    while (itr.hasNext()) {
                        String node = itr.next();
                        String b = node.charAt(0) == '-' ? node.substring(1) : node;

                        // Insert the parent node before the child
                        if (allchildren.containsKey(b)) {
                            itr.set(key);
                            itr.add(node);
                            break;
                        }
                    }
                }
                if (!result.contains(key))
                    result.add(key);
            }
        }

        return result;
    }
    public static void run() {
        registerPermissions();
        for (Player p : Bukkit.getOnlinePlayers()) {
            updatePermission(p);
        }
    }
    public static void registerPermissions() {
        registeredPermissions.clear();

        for (Permission perm : Bukkit.getPluginManager().getPermissions()) {
            registeredPermissions.put(perm.getName().toLowerCase(), perm);
        }
    }
}

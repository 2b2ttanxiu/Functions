package org.functions.Bukkit.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Functions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tab {
    public void Tab() {

    }
    public static String colorMsg(String msg) {
        if (msg == null) {
            return "";
        } else {
            if (Version.isCurrentEqualOrHigher(Version.v1_16_R1) && msg.contains("#")) {
                msg = matchColorRegex(msg);
            }

            return ChatColor.translateAlternateColorCodes('&', msg);
        }
    }

    public static String matchColorRegex(String s) {
        String regex = "#[a-fA-F0-9]{6}";
        Matcher matcher = Pattern.compile(regex).matcher(s);

        while(matcher.find()) {
            String group = matcher.group(0);

            try {
                s = s.replace(group, ChatColor.valueOf(group) + "");
            } catch (Exception var5) {
                Functions.instance.getAPI().sendConsole("The hex is a bad:" + group);
            }
        }

        return s;
    }
    public Tab(Player player, String header, String footer,String listName) {
        if (player != null) {
            if (header == null) {
                header = "";
            }

            if (footer == null) {
                footer = "";
            }

            if (Version.isCurrentEqualOrLower(Version.v1_15_R2)) {
                header = colorMsg(header);
                footer = colorMsg(footer);
            }

            try {
                Class packetPlayOutPlayerListHeaderFooter = getNMSClass("PacketPlayOutPlayerListHeaderFooter");

                try {
                    Object packet = packetPlayOutPlayerListHeaderFooter.getConstructor().newInstance();
                    Object tabHeader = getAsIChatBaseComponent(header);
                    Object tabFooter = getAsIChatBaseComponent(footer);
                    if (Version.isCurrentEqualOrHigher(Version.v1_13_R2)) {
                        setField(packet, "header", tabHeader);
                        setField(packet, "footer", tabFooter);
                    } else {
                        setField(packet, "a", tabHeader);
                        setField(packet, "b", tabFooter);
                    }

                    sendPacket(player, packet);
                } catch (Exception var8) {
                    Constructor<?> titleConstructor = null;
                    if (Version.isCurrentEqualOrHigher(Version.v1_12_R1)) {
                        titleConstructor = packetPlayOutPlayerListHeaderFooter.getConstructor();
                    } else if (Version.isCurrentLower(Version.v1_12_R1)) {
                        titleConstructor = packetPlayOutPlayerListHeaderFooter.getConstructor(getAsIChatBaseComponent(header).getClass());
                    }

                    if (titleConstructor != null) {
                        setField(titleConstructor, "b", getAsIChatBaseComponent(footer));
                        sendPacket(player, titleConstructor);
                    }
                }
            } catch (Throwable var9) {
                var9.printStackTrace();
            }

        }
        if (listName == null) {
            return;
        }
        player.setPlayerListName(Functions.instance.getAPI().replace(player,listName.replace("&","§").replace("none","")));
    }
    public enum Version {
        v1_8_R1,
        v1_8_R2,
        v1_8_R3,
        v1_9_R1,
        v1_9_R2,
        v1_10_R1,
        v1_11_R1,
        v1_12_R1,
        v1_13_R1,
        v1_13_R2,
        v1_14_R1,
        v1_14_R2,
        v1_15_R1,
        v1_15_R2,
        v1_16_R1,
        v1_16_R2,
        v1_16_R3,
        v1_17_R1,
        v1_17_R2,
        v1_18_R1,
        v1_18_R2;

        private Integer value = Integer.valueOf(this.name().replaceAll("[^\\d.]", ""));
        private String shortVersion = this.name().substring(0, this.name().length() - 3);
        private static Version current;

        private Version() {
        }

        public Integer getValue() {
            return this.value;
        }

        public static Version getCurrent() {
            if (current != null) {
                return current;
            } else {
                String[] v = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
                String vv = v[v.length - 1];
                Version[] var2 = values();
                int var3 = var2.length;

                for (int var4 = 0; var4 < var3; ++var4) {
                    Version one = var2[var4];
                    if (one.name().equalsIgnoreCase(vv)) {
                        current = one;
                        break;
                    }
                }

                return current;
            }
        }

        public static boolean isCurrentEqualOrHigher(Version v) {
            return getCurrent().getValue() >= v.getValue();
        }

        public static boolean isCurrentLower(Version v) {
            return getCurrent().getValue() < v.getValue();
        }

        public static boolean isCurrentEqualOrLower(Version v) {
            return getCurrent().getValue() <= v.getValue();
        }

        public static boolean isCurrentEqual(Version v) {
            return getCurrent().getValue().equals(v.getValue());
        }
    }
    public static class JavaAccessibilities {
        public static boolean isAccessible(Field field, Object target) {
            if (getCurrentVersion() >= 9 && target != null) {
                try {
                    return (Boolean)field.getClass().getDeclaredMethod("canAccess", Object.class).invoke(field, target);
                } catch (NoSuchMethodException var3) {
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }

            return field.isAccessible();
        }

        public static boolean isAccessible(Method method, Object target) {
            if (getCurrentVersion() >= 9 && target != null) {
                try {
                    return (Boolean)method.getClass().getDeclaredMethod("canAccess", Object.class).invoke(method, target);
                } catch (NoSuchMethodException var3) {
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }

            return method.isAccessible();
        }

        public static int getCurrentVersion() {
            String currentVersion = System.getProperty("java.version");
            if (currentVersion.contains("_")) {
                currentVersion = currentVersion.split("_")[0];
            }

            currentVersion = currentVersion.replaceAll("[^\\d]|_", "");

            for(int i = 8; i <= 18; ++i) {
                if (currentVersion.contains(Integer.toString(i))) {
                    return i;
                }
            }

            return 0;
        }
    }
    private static final Gson GSON = (new GsonBuilder()).create();
    private static final List<JsonObject> JSONLIST = new CopyOnWriteArrayList();
    public static Object getHandle(Object obj) throws Exception {
        return invokeMethod(obj, "getHandle");
    }

    public static synchronized Object getAsIChatBaseComponent(String text) throws Exception {
        Class<?> iChatBaseComponent = getNMSClass("IChatBaseComponent");
        if (!Version.isCurrentEqualOrHigher(Version.v1_16_R1)) {
            if (Version.isCurrentLower(Version.v1_8_R2)) {
                Class<?> chatSerializer = getNMSClass("ChatSerializer");
                return iChatBaseComponent.cast(chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\":\"" + text + "\"}"));
            } else {
                Method m = iChatBaseComponent.getDeclaredClasses()[0].getMethod("a", String.class);
                return m.invoke(iChatBaseComponent, "{\"text\":\"" + text + "\"}");
            }
        } else {
            JSONLIST.clear();
            JsonObject obj = new JsonObject();
            StringBuilder builder = new StringBuilder();
            String res = text;
            String font = "";
            String colorName = "";
            char charBefore = ' ';

            for(int i = 0; i < res.length() && i < res.length(); ++i) {
                if (charBefore == '&') {
                    charBefore = ' ';
                } else {
                    char charAt = res.charAt(i);
                    if (charAt == '{') {
                        int closeIndex = -1;
                        if (res.regionMatches(true, i, "{font=", 0, 6) && (closeIndex = res.indexOf(125, i + 6)) >= 0) {
                            font = NamespacedKey.minecraft(res.substring(i + 6, closeIndex)).toString();
                        } else if (res.regionMatches(true, i, "{/font", 0, 6) && (closeIndex = res.indexOf(125, i + 6)) >= 0) {
                            font = NamespacedKey.minecraft("default").toString();
                        }

                        if (closeIndex >= 0) {
                            if (builder.length() > 0) {
                                obj.addProperty("text", builder.toString());
                                JSONLIST.add(obj);
                                builder = new StringBuilder();
                            }

                            obj = new JsonObject();
                            obj.addProperty("font", font);
                            i += closeIndex - i;
                        }
                    } else if (charAt == '#') {
                        colorName = res.substring(i, i + 7);
                        if (builder.length() > 0) {
                            obj.addProperty("text", builder.toString());
                            JSONLIST.add(obj);
                            builder = new StringBuilder();
                        }

                        obj = new JsonObject();
                        obj.addProperty("color", colorName);
                        i += 6;
                    } else if (charAt == '&') {
                        charBefore = charAt;
                        char colorCode = res.charAt(i + 1);
                        if (Character.isDigit(colorCode) || colorCode >= 'a' && colorCode <= 'f' || colorCode == 'k' || colorCode == 'l' || colorCode == 'm' || colorCode == 'n' || colorCode == 'o' || colorCode == 'r') {
                            obj.addProperty("text", builder.toString());
                            JSONLIST.add(obj);
                            obj = new JsonObject();
                            builder = new StringBuilder();
                            if (!colorName.isEmpty()) {
                                obj.addProperty("color", colorName);
                            }

                            if (!font.isEmpty()) {
                                obj.addProperty("font", font);
                            }

                            switch(colorCode) {
                                case 'k':
                                    obj.addProperty("obfuscated", true);
                                    break;
                                case 'l':
                                    obj.addProperty("bold", true);
                                    break;
                                case 'm':
                                    obj.addProperty("strikethrough", true);
                                    break;
                                case 'n':
                                    obj.addProperty("underlined", true);
                                    break;
                                case 'o':
                                    obj.addProperty("italic", true);
                                    break;
                                case 'p':
                                case 'q':
                                default:
                                    colorName = ChatColor.getByChar(colorCode).name().toLowerCase();
                                    obj.addProperty("color", colorName);
                                    break;
                                case 'r':
                                    colorName = "white";
                                    obj.addProperty("color", colorName);
                            }
                        }
                    } else {
                        builder.append(charAt);
                    }
                }
            }

            obj.addProperty("text", builder.toString());
            JSONLIST.add(obj);
            Method m = iChatBaseComponent.getDeclaredClasses()[0].getMethod("a", String.class);
            return m.invoke(iChatBaseComponent, GSON.toJson(JSONLIST));
        }
    }

    public static Object invokeMethod(Object obj, String name) throws Exception {
        return invokeMethod(obj, name, true, false);
    }

    public static Object invokeMethod(Object obj, String name, boolean declared, boolean superClass) throws Exception {
        Class<?> c = superClass ? obj.getClass().getSuperclass() : obj.getClass();
        Method met = declared ? c.getDeclaredMethod(name) : c.getMethod(name);
        if (!JavaAccessibilities.isAccessible(met, obj)) {
            met.setAccessible(true);
        }

        return met.invoke(obj);
    }

    public static Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + getPackageVersion() + "." + name);
    }

    public static Field getField(Object clazz, String name) throws Exception {
        return getField(clazz, name, true);
    }

    public static Field getField(Object clazz, String name, boolean declared) throws Exception {
        return getField(clazz.getClass(), name, declared);
    }

    public static Field getField(Class<?> clazz, String name, boolean declared) throws Exception {
        Field field = declared ? clazz.getDeclaredField(name) : clazz.getField(name);
        if (!JavaAccessibilities.isAccessible(field, (Object)null)) {
            field.setAccessible(true);
        }

        return field;
    }

    public static Object getFieldObject(Object object, Field field) throws Exception {
        return field.get(object);
    }

    public static void setField(Object object, String fieldName, Object fieldValue) throws Exception {
        getField(object, fieldName).set(object, fieldValue);
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object playerHandle = getHandle(player);
            Object playerConnection = getFieldObject(playerHandle, getField(playerHandle, "playerConnection"));
            playerConnection.getClass().getDeclaredMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception var4) {
        }

    }

    public static String getPackageVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}

package org.functions.Bukkit.Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.functions.Bukkit.api.API;

public class CheckJvm {
    public CheckJvm() {
    }

    private static int getJvmVersion() {
        String javaVersion = System.getProperty("java.version");
        Matcher matcher = Pattern.compile("(?:1\\.)?(\\d+)").matcher(javaVersion);
        if (!matcher.find()) {
            return -1;
        } else {
            String version = matcher.group(1);

            try {
                return Integer.parseInt(version);
            } catch (NumberFormatException var4) {
                return -1;
            }
        }
    }

    public static void checkJvm() {
        if (getJvmVersion() < 11) {
            API p = Functions.getMain().getAPI();
            p.sendConsole( "############################################################");
            p.sendConsole( "# WARNING - YOU ARE RUNNING AN OUTDATED VERSION OF JAVA.");
            p.sendConsole( "# PAPER WILL STOP BEING COMPATIBLE WITH THIS VERSION OF");
            p.sendConsole( "# JAVA WHEN MINECRAFT 1.17 IS RELEASED.");
            p.sendConsole( "#");
            p.sendConsole( "# Please update the version of Java you use to run Paper");
            p.sendConsole( "# to at least Java 11. When Paper for Minecraft 1.17 is");
            p.sendConsole( "# released support for versions of Java before 11 will");
            p.sendConsole( "# be dropped.");
            p.sendConsole( "#");
            p.sendConsole( "# Current Java version: " + System.getProperty("java.version"));
            p.sendConsole( "#");
            p.sendConsole( "# Check this forum post for more information: ");
            p.sendConsole( "#   https://papermc.io/java11");
            p.sendConsole( "############################################################");
        }

    }
}

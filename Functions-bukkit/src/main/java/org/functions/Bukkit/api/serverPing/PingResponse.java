package org.functions.Bukkit.api.serverPing;

import com.google.gson.JsonElement;
import org.functions.Bukkit.Main.Functions;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PingResponse
{
    private boolean isOnline;
    private String motd;
    private int onlinePlayers;
    private int maxPlayers;

    public PingResponse(boolean isOnline, String motd, int onlinePlayers, int maxPlayers) {
        this.isOnline = isOnline;
        this.motd = motd;
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
    }
    public PingResponse(String jsonString,ServerAddress address) {

        if (jsonString == null || jsonString.isEmpty()) {
            motd = "Invalid ping response";return;
        }

        Object jsonObject = JSONValue.parse(jsonString);

        if (!(jsonObject instanceof JSONObject)) {
            motd = "Invalid ping response";return;
        }

        JSONObject json = (JSONObject) jsonObject;
        isOnline = true;

        Object descriptionObject = json.get("description");

        if (descriptionObject != null) {
            if (descriptionObject instanceof JSONObject) {
                String descriptionString = ((JSONObject) descriptionObject).toJSONString();
                try {
                    motd = descriptionString;
                } catch (Exception e) {
                    motd = "Invalid ping response";
                }
            } else {
                motd = descriptionObject.toString();
            }
        } else {
            motd = "Invalid ping response (description not found)";
        }

        Object playersObject = json.get("players");

        if (playersObject instanceof JSONObject) {
            JSONObject playersJson = (JSONObject) playersObject;

            Object onlineObject = playersJson.get("online");
            if (onlineObject instanceof Number) {
                onlinePlayers = ((Number) onlineObject).intValue();
            }

            Object maxObject = playersJson.get("max");
            if (maxObject instanceof Number) {
                maxPlayers = ((Number) maxObject).intValue();
            }
        }
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getMotd() {
        return motd.replace("{\"text\":\"","").replace("\"}","");
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

}

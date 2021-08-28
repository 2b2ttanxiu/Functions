package org.functions.Bukkit.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.functions.Bukkit.Main.Functions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class PlayerSendActionBarEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private String Message;
    public PlayerSendActionBarEvent(String Message) {
        this.Message = Message;
    }
    public boolean isCancelled() {
        return cancel;
    }
    public String getMessage() {
        return Message;
    }
    public void setMessage(String Message) {
        this.Message = Message;
    }
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
	public void schedule(PlayerSendActionBarEvent event) {
        if (cancel) {
            Functions.instance.getServer().getPluginManager().callEvent(event);
            return;
        }
				    for (Player player : Functions.instance.getAPI().getOnlinePlayers()) {
                        String nms = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
                        UUID uuid = player.getUniqueId();
                        String msg = Message;
                        Player p = player;
                        msg = Functions.instance.getAPI().replace(p,msg);
                        Object packet;
                        Functions.instance.getServer().getPluginManager().callEvent(event);
                        boolean high = false;
                        boolean useOldMethods = false;
                        if (nms.startsWith("v_1_1") || nms.contains("v_1_16")) {
                            high = true;
                        }
                        if (nms.equalsIgnoreCase("v1_8_R1") || nms.startsWith("v1_7_")) {
                            useOldMethods = true;
                        }

                        try {
                            Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nms + ".PacketPlayOutChat");
                            Class chatComponentTextClass;
                            Class iChatBaseComponentClass;
                            Object chatCompontentText;
                            if (useOldMethods) {
                                chatComponentTextClass = Class.forName("net.minecraft.server." + nms + ".ChatSerializer");
                                iChatBaseComponentClass = Class.forName("net.minecraft.server." + nms + ".IChatBaseComponent");
                                Method m3 = chatComponentTextClass.getDeclaredMethod("a", String.class);
                                chatCompontentText = iChatBaseComponentClass.cast(m3.invoke(chatComponentTextClass, "{\"text\": \"" + Message + "\"}"));
                                packet = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, Byte.TYPE).newInstance(chatCompontentText, 2);
                            } else {
                                if (high) {
                                    try {
                                        Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nms + ".entity.CraftPlayer");
                                        Object craftPlayer = craftPlayerClass.cast(p);
                                        Class<?> packetClass = Class.forName("net.minecraft.server." + nms + ".Packet");
                                        packetPlayOutChatClass = Class.forName("net.minecraft.server." + nms + ".PacketPlayOutChat");
                                        chatComponentTextClass = Class.forName("net.minecraft.server." + nms + ".ChatComponentText");
                                        iChatBaseComponentClass = Class.forName("net.minecraft.server." + nms + ".IChatBaseComponent");
                                        try {
                                            Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nms + ".ChatMessageType");
                                            Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                                            Object chatMessageType = null;
                                            Object[] var13 = chatMessageTypes;
                                            int var14 = chatMessageTypes.length;

                                            for (int var15 = 0; var15 < var14; ++var15) {
                                                Object obj = var13[var15];
                                                if (obj.toString().equals("GAME_INFO")) {
                                                    chatMessageType = obj;
                                                }
                                            }

                                            chatCompontentText = chatComponentTextClass.getConstructor(String.class).newInstance(msg);
                                            packet = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, chatMessageTypeClass, uuid.getClass()).newInstance(chatCompontentText, chatMessageType, uuid);
                                        } catch (ClassNotFoundException var16) {
                                            chatCompontentText = chatComponentTextClass.getConstructor(String.class).newInstance(msg);
                                            packet = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, Byte.TYPE, uuid.getClass()).newInstance(chatCompontentText, 2, uuid);
                                        }

                                        Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
                                        Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
                                        Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
                                        chatCompontentText = playerConnectionField.get(craftPlayerHandle);
                                        Method sendPacketMethod = chatCompontentText.getClass().getDeclaredMethod("sendPacket", packetClass);
                                        sendPacketMethod.invoke(chatCompontentText, packet);
                                    } catch (NoSuchMethodException | ClassNotFoundException var17) {
                                        var17.printStackTrace();
                                    } catch (IllegalAccessException var18) {
                                        var18.printStackTrace();
                                    } catch (InstantiationException var19) {
                                        var19.printStackTrace();
                                    } catch (InvocationTargetException var20) {
                                        var20.printStackTrace();
                                    } catch (NoSuchFieldException var21) {
                                        var21.printStackTrace();
                                    }
                                }
                                chatComponentTextClass = Class.forName("net.minecraft.server." + nms + ".ChatComponentText");
                                iChatBaseComponentClass = Class.forName("net.minecraft.server." + nms + ".IChatBaseComponent");

                                try {
                                    Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nms + ".ChatMessageType");
                                    Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                                    Object chatMessageType = null;
                                    Object[] var13 = chatMessageTypes;
                                    int var14 = chatMessageTypes.length;

                                    for (int var15 = 0; var15 < var14; ++var15) {
                                        Object obj = var13[var15];
                                        if (obj.toString().equals("GAME_INFO")) {
                                            chatMessageType = obj;
                                        }
                                    }

                                    chatCompontentText = chatComponentTextClass.getConstructor(String.class).newInstance(msg);
                                    packet = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, chatMessageTypeClass).newInstance(chatCompontentText, chatMessageType);
                                } catch (ClassNotFoundException var14) {
                                    chatCompontentText = chatComponentTextClass.getConstructor(String.class).newInstance(msg);
                                    packet = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, Byte.TYPE).newInstance(chatCompontentText, 2);
                                }
                            }
                            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nms + ".entity.CraftPlayer");
                            Object craftPlayer = craftPlayerClass.cast(p);
                            Class<?> packetClass = Class.forName("net.minecraft.server." + nms + ".Packet");
                            Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
                            Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
                            Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
                            chatCompontentText = playerConnectionField.get(craftPlayerHandle);
                            Method sendPacketMethod = chatCompontentText.getClass().getDeclaredMethod("sendPacket", packetClass);
                            sendPacketMethod.invoke(chatCompontentText, packet);
                        } catch (NoSuchMethodException | ClassNotFoundException var15) {
                            var15.printStackTrace();
                        } catch (IllegalAccessException var16) {
                            var16.printStackTrace();
                        } catch (InstantiationException var17) {
                            var17.printStackTrace();
                        } catch (InvocationTargetException var18) {
                            var18.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }

    }
}

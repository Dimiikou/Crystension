package net.CrystalRage.crystension.methods;

import net.CrystalRage.crystension.Crystension;
import net.CrystalRage.crystension.handlers.ConfigHandler;
import net.CrystalRage.crystension.listeners.NametagChanger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;
import java.util.regex.Pattern;

public class FrakListFiller {

    public static long LAST_UPDATED = 0;
    public static HashMap<String, ArrayList<String>> FACTION_MEMBERS = new HashMap<>();
    public static final Pattern FACTION_MEMBERS_PATTERN = Pattern.compile("^ {2}=== Fraktionsmitglieder .+ ===$");
    public static final Pattern FACTION_MEMBERLIST_PATTERN = Pattern.compile("^ \u00BB .+$");
    public static String currentLoading;
    public static boolean loading = false;

    public static Timer t = new Timer();

    public static void loadFaction(String name, long after) {
        if(after <= 10L) {
            currentLoading = name;
            FACTION_MEMBERS.put(currentLoading, new ArrayList<String>());
            if(Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) Minecraft.getMinecraft().player.sendChatMessage("/memberinfo "+name);
        } else {
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    currentLoading = name;
                    FACTION_MEMBERS.put(currentLoading, new ArrayList<String>());
                    if(Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) Minecraft.getMinecraft().player.sendChatMessage("/memberinfo "+name);
                    t.cancel();
                }
            }, after);
        }
    }

    public static void load() {
        if(loading) return;
        LAST_UPDATED = System.currentTimeMillis();
        FACTION_MEMBERS.clear();
        loading = true;

        if (!ConfigHandler.IB_DeineFraktion.equalsIgnoreCase("keine")) {
            loadFaction(ConfigHandler.IB_DeineFraktion, 0L);
        }
        if (!ConfigHandler.IC_BuendnissFraktionEins.equalsIgnoreCase("keine")) {
            loadFaction(ConfigHandler.IC_BuendnissFraktionEins, 300L);
        }
        if (!ConfigHandler.ID_BuendnissFraktionZwei.equalsIgnoreCase("keine")) {
            loadFaction(ConfigHandler.ID_BuendnissFraktionZwei, 600L);
        }

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                loading = false;
                if(Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) {
                    int size = 0;
                    if (!ConfigHandler.IB_DeineFraktion.equalsIgnoreCase("keine")) {
                        size = size+FACTION_MEMBERS.get(ConfigHandler.IB_DeineFraktion).size();
                    }
                    if (!ConfigHandler.IC_BuendnissFraktionEins.equalsIgnoreCase("keine")) {
                        size = size+FACTION_MEMBERS.get(ConfigHandler.IC_BuendnissFraktionEins).size();
                    }
                    if (!ConfigHandler.ID_BuendnissFraktionZwei.equalsIgnoreCase("keine")) {
                        size = size+FACTION_MEMBERS.get(ConfigHandler.ID_BuendnissFraktionZwei).size();
                    }
                    if(size <= 0) {
                        Minecraft.getMinecraft().player.sendMessage(ColorMessage.getMSG(Crystension.prefix + "Es wuden §ekeine§7 Bündnissmitglieder gefunden."));
                    } else {
                        Minecraft.getMinecraft().player.sendMessage(ColorMessage.getMSG(Crystension.prefix + "Es wurden §e"+size+" §7Bündnissmitglieder geladen."));
                        Timer t = new Timer();
                        t.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                NametagChanger.refreshAllDisplayNames();
                            }
                        }, 3*1000);
                    }
                }
                t.cancel();
            }
        }, 900L);
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if(loading) {
            if(Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) {
                String msg = e.getMessage().getUnformattedText();
                EntityPlayerSP p = Minecraft.getMinecraft().player;
                long currenttime = System.currentTimeMillis();
                if (!msg.contains(":")) {
                    if (FACTION_MEMBERS_PATTERN.matcher(msg).find()) {
                        e.setCanceled(true);
                    } else if (FACTION_MEMBERLIST_PATTERN.matcher(msg).find()) {
                        String args[] = msg.split(" ");
                        String member = args[2];
                        if (!member.startsWith("===")) {
                            ArrayList<String> list = FACTION_MEMBERS.get(currentLoading);
                            list.add(member);
                            FACTION_MEMBERS.put(currentLoading, list);
                            e.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    public static void startTimer() {
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ConfigHandler.IA_ActivateFrakNameHighlighter) {
                    if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) {
                        if (!Crystension.AFK) {
                            load();
                        }
                    }
                }
            }
        }, ConfigHandler.IE_UpdateDelay*60*1000, ConfigHandler.IE_UpdateDelay*60*1000);
    }
}

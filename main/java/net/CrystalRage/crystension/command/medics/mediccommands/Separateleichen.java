package net.CrystalRage.crystension.command.medics.mediccommands;

import net.CrystalRage.crystension.Ucmodify;
import net.CrystalRage.crystension.methods.ColorMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemSkull;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class Separateleichen {
    private static long LAST_UPDATED = 0;
    private static HashMap<String, ArrayList<String>> FACTION_MEMBERS = new HashMap<>();
    private static final Pattern FACTION_MEMBERS_PATTERN = Pattern.compile("^ {2}=== Fraktionsmitglieder .+ ===$");
    private static boolean loading = false;
    private static String currentLoading;
    private static String SONDERZEICHEN = "\u00BB";

    private static String getMSG(String msg) {
        return Ucmodify.prefix + msg;
    }

    public static void onCommand() {
        if(!isLoaded()) {
            load();
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) changeLeichen();
                    t.cancel();
                }
            }, 1200L);
        } else {
            changeLeichen();
        }
    }
    private static boolean isLoaded() {
        if(LAST_UPDATED == 0) return false;
        if(LAST_UPDATED+TimeUnit.MINUTES.toMillis(1) <= System.currentTimeMillis()) return false;
        return true;
    }
    private static void loadFaction(String name, long after) {
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
    private static void load() {
        if(loading) return;
        LAST_UPDATED = System.currentTimeMillis();
        FACTION_MEMBERS.clear();
        loading = true;
        loadFaction("Rettungsdienst", 0L);
        loadFaction("Polizei", 300L);
        loadFaction("FBI", 600L);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                loading = false;
                if(Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) {
                    int size = 0;
                    size = size+FACTION_MEMBERS.get("Rettungsdienst").size();
                    size = size+FACTION_MEMBERS.get("Polizei").size();
                    size = size+FACTION_MEMBERS.get("FBI").size();
                    if(size <= 0) {
                        Minecraft.getMinecraft().player.sendMessage(ColorMessage.getMSG(getMSG("Es wuden §ekeine§7 Staatsmitglieder gefunden.")));
                    } else {
                        Minecraft.getMinecraft().player.sendMessage(ColorMessage.getMSG(getMSG("Es wurden §e"+size+" §7Staatsmitglieder geladen.")));

                    }
                }
                t.cancel();
            }
        }, 900L);
    }
    private static String SONDERZEICHEN_STERN = "\u2730";
    private static String SONDERZEICHEN_PFEIL = "\u2192";
    private static String SONDERZEICHEN_KREUZ = "\u271F";
    // private static String SONDERZEICHEN_NULL = "\u0000";
    private static String returnNameWithTag(String replace, String name, String color) {
        String ret;
        ret = replace+SONDERZEICHEN_KREUZ+" "+name+" \247f"+SONDERZEICHEN_PFEIL+" \247"+color+"\247l"+SONDERZEICHEN_STERN;
        return ret;
    }
    private static List<EntityItem> modifeditems = new ArrayList<EntityItem>();
    private static void changeLeichen() {
        EntityPlayerSP p = Minecraft.getMinecraft().player;
        BlockPos pos = p.getPosition();
        int umgebung = 0;
        int gesamt = 0;
        List<EntityItem> items = Minecraft.getMinecraft().world.getEntities(EntityItem.class, (ent) -> ent != null && ent.hasCustomName() && ent.getItem().getItem() instanceof ItemSkull);
        for(EntityItem entityItem : items) {
            if(modifeditems.contains(entityItem)) continue;
            String name = entityItem.getCustomNameTag();
            if(name.contains("\u271F")) name = name.replace("\u271F", "");
            if(name.contains("\u271D")) name = name.replace("\u271D", "");
            if(name.contains("\u1F546")) name = name.replace("\u1F546", "");
            if(name.contains("\u1F547")) name = name.replace("\u1F547", "");
            String args[] = name.split("");
            String replace = args[0]+args[1];
            if(name.startsWith(replace)) name = name.replaceFirst(replace, "");
            boolean staatsmember = false;
            if(FACTION_MEMBERS.get("Rettungsdienst").contains(name)) {
                staatsmember = true;
                entityItem.setCustomNameTag(returnNameWithTag(replace, name, "c"));
                entityItem.setAlwaysRenderNameTag(true);
            } else if(FACTION_MEMBERS.get("Polizei").contains(name)) {
                staatsmember = true;
                entityItem.setCustomNameTag(returnNameWithTag(replace, name, "9"));
                entityItem.setAlwaysRenderNameTag(true);
            } else if(FACTION_MEMBERS.get("FBI").contains(name)) {
                staatsmember = true;
                entityItem.setCustomNameTag(returnNameWithTag(replace, name, "1"));
                entityItem.setAlwaysRenderNameTag(true);
            }
            if(staatsmember) {
                modifeditems.add(entityItem);
                gesamt = gesamt+1;
                double distance = entityItem.getDistanceSq(pos.getX(), pos.getY(), pos.getZ());
                if(distance <= 75) {
                    umgebung = umgebung+1;
                }
            }
        }
        String msg = "";
        if(gesamt <= 0) {
            msg = getMSG("§eKeine §7(neuen) Leichen gefunden.");
        } else {
            msg = getMSG("§e"+gesamt+" §7Leiche/n wurde/n identifiziert.");
            if(umgebung >= 1 && gesamt != umgebung) {
                msg = msg+" §8(§7Davon §e"+gesamt+"§7 im Umkreis von 75m§8)";
            }
        }
        p.sendMessage(ColorMessage.getMSG(msg));
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if(!loading) return;
        if(!Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) return;
        String msg = e.getMessage().getUnformattedText();
        EntityPlayerSP p = Minecraft.getMinecraft().player;
        long currenttime = System.currentTimeMillis();
        if(FACTION_MEMBERS_PATTERN.matcher(msg).find()) {
            e.setCanceled(true);
        }
        if(!msg.startsWith(" "+SONDERZEICHEN+ " ")) return;
        String args[] = msg.split(" ");
        String member = args[2];
        if(member.startsWith("===")) return;
        ArrayList<String> list = FACTION_MEMBERS.get(currentLoading);
        list.add(member);
        FACTION_MEMBERS.put(currentLoading, list);
        if (msg.contains("[") && msg.contains("]")) {
            e.setCanceled(false);
        } else if (msg.contains("f\u00fcgt hinzu:")) {
            e.setCanceled(false);
        } else if (msg.contains(":")) {
            e.setCanceled(false);
        } else {
            e.setCanceled(true);
        }
    }
}

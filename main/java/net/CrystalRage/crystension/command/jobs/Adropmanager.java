package net.CrystalRage.crystension.command.jobs;

import net.CrystalRage.crystension.Crystension;
import net.CrystalRage.crystension.methods.ColorMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class Adropmanager {
    public static boolean dropmoney = false;
    public static final Timer timer = new Timer();
    public static final AtomicBoolean started = new AtomicBoolean();


    public static void adropmoney() {
        EntityPlayerSP p = Minecraft.getMinecraft().player;

        dropmoney = true;
        p.sendChatMessage("/bank abbuchen 15000");
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                p.sendChatMessage("/dropmoney");
                Timer r = new Timer();
                r.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        p.sendChatMessage("/bank einzahlen 15000");
                        Timer d = new Timer();
                        d.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                dropmoney = false;
                                p.sendMessage(ColorMessage.getMSGwithPrefix("Du kannst ab jetzt weitergehen."));
                            }
                        }, 200);
                    }
                }, 1500);
            }
        }, 1500);
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if(!Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) return;
        String msg = e.getMessage().getUnformattedText();
        EntityPlayerSP p = Minecraft.getMinecraft().player;
        if (dropmoney) {
            if (msg.equalsIgnoreCase("Du hast zu wenig Geld!")) {
                dropmoney = false;
            }
            if (msg.contains("Vorheriger Kontostand:")) {
                e.setCanceled(true);
            }
            if (msg.contains("=== Kontoauszug ===")) {
                e.setCanceled(true);
            }
            if (msg.contains("Auszahlung:") || msg.contains("Eingezahlt:")) {
                e.setCanceled(true);
            }
            if (msg.contains("Neuer Kontostand:")) {
                e.setCanceled(true);
            }
            if (!msg.contains("e") && !msg.contains("[") && !msg.toLowerCase().contains("exp")) {
                e.setCanceled(true);
            }


        }
    }
}

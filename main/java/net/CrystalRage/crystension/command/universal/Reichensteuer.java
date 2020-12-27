package net.CrystalRage.crystension.command.universal;

import net.CrystalRage.crystension.methods.ColorMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Timer;
import java.util.TimerTask;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class Reichensteuer {

    private static int i = 0;
    private static boolean start = false;

    public static void onCommand() {
        EntityPlayerSP p = Minecraft.getMinecraft().player;
        start = true;
        p.sendChatMessage("/bank info");
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (i>0) {
                    p.sendChatMessage("/bank abbuchen " + i);
                } else {
                    p.sendMessage(ColorMessage.getMSG("Dein Kontostand ist bereits auf §e99999§7."));
                }

            }
        }, 1500);
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if(!Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) return;
        String msg = e.getMessage().getUnformattedText();
        EntityPlayerSP p = Minecraft.getMinecraft().player;
        if (start) {
            if (msg.equalsIgnoreCase("Fehler: Gib ein Betrag an.")) {
                start = false;
            }
            if (msg.contains("Ihr Bankguthaben betr\u00e4gt:")) {
                String[] s = msg.split(" ");
                String es = s[3];
                es = es.replace("+", "").replace("$", "");
                i = Integer.valueOf(es)-99999;
                start = false;
                e.setCanceled(true);
            }
            if (msg.contains("=== Kontoauszug ===")) {
                e.setCanceled(true);
            }
            if (!msg.contains("e") && !msg.contains("[")) {
                e.setCanceled(true);
            }


        }
    }
}

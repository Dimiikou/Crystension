package net.CrystalRage.ucmodify.listeners.Karma;

import net.CrystalRage.ucmodify.handlers.ConfigHandler;
import net.CrystalRage.ucmodify.methods.ColorMessage;
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
public class ChangeKarmaMessage {

    static int karmaa = 7;
    static int karma = 0;
    static boolean karmacheck = false;

    @SubscribeEvent
    public static void onChat(ClientChatReceivedEvent e) {
        if (ConfigHandler.JA_ChangeKarmaMessage) {
            String msg = e.getMessage().getUnformattedText();
            String changekarma = "";
            String[] s = msg.split(" ");
            EntityPlayerSP p = Minecraft.getMinecraft().player;

            if (msg.contains("[Karma]") && !msg.contains("von")) {
                p.sendChatMessage("/karma");

                karmacheck=true;
                changekarma = msg;
                e.setMessage(null);

                karma = Integer.valueOf(s[1].replace("+", ""));

            }

            if (msg.contains("[Karma]") && msg.contains("von")) {
                if (karmacheck) {
                    karmaa = Integer.valueOf(s[6].replace(".", "").replace("+", ""));
                    if (karmaa >= 0) {
                        e.setMessage(ColorMessage.getMSG("§8[§9Karma§8] §b" + karma + " Karma §8(§b+" + karmaa + "§8/§b100§8)"));
                    } else {
                        e.setMessage(ColorMessage.getMSG("§8[§9Karma§8] §b" + karma + " Karma §8(§b" + karmaa + "§8/§b100§8)"));
                    }
                    karmacheck=false;
                }

            }
        }
    }

}

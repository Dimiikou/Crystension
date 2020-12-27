package net.CrystalRage.crystension.listeners;

import net.CrystalRage.crystension.Crystension;
import net.CrystalRage.crystension.handlers.ConfigHandler;
import net.CrystalRage.crystension.methods.ColorMessage;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class HQNachrichten {

    @SubscribeEvent
    public static void onChat(ClientChatReceivedEvent e) {
        EntityPlayerSP p = Crystension.minecraft.player;
        String msg = e.getMessage().getUnformattedText();
        String[] s = msg.split(" ");

        if (ConfigHandler.LA_HQNachrichten) {
            if (msg.contains("HQ:") && msg.contains("wurde von") && msg.contains("get\u00f6tet")) {
                String killer = s[4];
                String killed = s[1];
                e.setMessage(ColorMessage.getMSG("§cGetötet §7- §9" + killed + " §7- §9" + killer));
            } else if (msg.contains("HQ:") && msg.contains("Fahndungsgrund:") && msg.contains("Fahndungszeit:")) {
                String fahndungsgrund = "";
                String fahndungszeit = "";
                boolean grundende = false;
                boolean zeitstart = false;

                // Fahndungsgrund
                for (int i=2; i <= (s.length-1); i++) {
                    if (!grundende) {
                        if (s[i].contains("|")) {
                            grundende = true;
                        }
                        fahndungsgrund = fahndungsgrund + " " + s[i];
                    }
                }

                for (int i=0; i <= (s.length-1); i++) {
                    if (s[i].contains("Fahndungszeit:")) {
                        zeitstart = true;
                    }

                    if (zeitstart) {
                        fahndungszeit = fahndungszeit + " " + s[i];
                    }
                }

                e.setMessage(ColorMessage.getMSG("§8\u27A5§9" + fahndungsgrund + "§7- §9 " + fahndungszeit.replace(".", "")));
            } else if (msg.contains("HQ:") && msg.contains("hat") && msg.contains("Akten gel\u00f6scht, over.")) {
                String loescher = s[2];
                String geloescht = s[4].replace("'s", "");
                e.setMessage(ColorMessage.getMSG("§cGelöscht §7- §9" + loescher + " §7- §9" + geloescht));
            } else if (msg.contains("HQ:") && msg.contains("Gesuchter:") && msg.contains("Grund:")) {
                String gesucht = s[2].replace(".", "");
                String grund = "";

                for (int i=3; i <= (s.length-1); i++) {
                    grund = grund + " " + s[i];
                }
                e.setMessage(ColorMessage.getMSG("§cGesucht §7- §9" + gesucht + " §7- §9" + grund));
            } else if (msg.contains("HQ:") && msg.contains("momentanes WantedLevel:")) {
                e.setMessage(ColorMessage.getMSG("§8\u27A5§9 " + s[4] + " §7Wanteds."));
            }
        }
    }
}

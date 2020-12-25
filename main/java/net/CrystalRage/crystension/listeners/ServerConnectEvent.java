package net.CrystalRage.crystension.listeners;

import net.CrystalRage.crystension.Ucmodify;
import net.CrystalRage.crystension.handlers.ConfigHandler;
import net.CrystalRage.crystension.methods.FrakListFiller;
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
public class ServerConnectEvent {
    public static boolean startrendering = false;
    @SubscribeEvent
    public static void onServerJoin(ClientChatReceivedEvent e) {
        EntityPlayerSP p = (Minecraft.getMinecraft().player);
        if (e.getMessage().getUnformattedText().contains("FMOTD:")) {
            FrakListFiller.loading = false;
            if (ConfigHandler.IA_ActivateFrakNameHighlighter) {
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) {
                            if (!Ucmodify.AFK) {
                                startrendering = true;
                                FrakListFiller.load();
                            }
                        }
                    }
                }, 30*1000);
            }

        }
    }
}

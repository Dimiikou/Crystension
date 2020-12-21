package net.CrystalRage.ucmodify.listeners;

import net.CrystalRage.ucmodify.Ucmodify;
import net.CrystalRage.ucmodify.handlers.ConfigHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
@SideOnly(Side.CLIENT)
public class Togglenews {

    @SubscribeEvent
    public static void onChat(ClientChatReceivedEvent e) {
        EntityPlayerSP p = Ucmodify.minecraft.player;
        String msg = e.getMessage().getUnformattedText();

        if (ConfigHandler.MB_NewsBlockieren) {
            if (msg.contains("News von:")) {
                e.setCanceled(true);
            }
        }
    }
}

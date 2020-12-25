package net.CrystalRage.crystension.command.medics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class DChatSperre {

    public static boolean dchatsperre = false;

    @SubscribeEvent
    public static void onTick(TickEvent.RenderTickEvent e) {
            Minecraft minecraft = Minecraft.getMinecraft();
            ScaledResolution res = new ScaledResolution(minecraft);
            int width = res.getScaledWidth();
            int heigt = res.getScaledHeight();

            FontRenderer fontRenderer = minecraft.fontRenderer;
            minecraft.entityRenderer.setupOverlayRendering();
            String message = "";
            if (dchatsperre) {
                message = "\2478 \u00BB \2474\247l D-Chat Sperre \2478 \u00AB";
            } else {
                message = "";
            }
            int titleWidth = fontRenderer.getStringWidth(message);
            int x = width / 2;
            fontRenderer.drawStringWithShadow(message, (float) (x - titleWidth / 2), (int) (heigt * 0.07), 0x009999);
    }
}

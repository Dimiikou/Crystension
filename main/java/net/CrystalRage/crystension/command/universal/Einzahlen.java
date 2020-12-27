package net.CrystalRage.crystension.command.universal;

import com.google.common.collect.Lists;
import net.CrystalRage.crystension.Ucmodify;
import net.CrystalRage.crystension.handlers.ConfigHandler;
import net.CrystalRage.crystension.methods.ColorMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class Einzahlen extends CommandBase implements IClientCommand {
    private static boolean start = false;
    private int money = 0;


    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "einzahlen";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) return;
        if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) {
            EntityPlayerSP p = Minecraft.getMinecraft().player;

            start=true;
            p.sendChatMessage("/stats");

            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendChatMessage("/bank einzahlen " + money);
                }
            }, 1500);

            return;
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    /*
    Chat event
     */

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if(!Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) return;
        String msg = e.getMessage().getUnformattedText();
        String[] args = msg.split(" ");
        EntityPlayerSP p = Minecraft.getMinecraft().player;
        if (start) {
            p.sendMessage(ColorMessage.getMSG("1"));
            if (msg.contains("- Geld:")) {
                p.sendMessage(ColorMessage.getMSG("2"));
                for (int i=0;i<args.length;i++) {
                    p.sendMessage(ColorMessage.getMSG("3"));
                    if (args[i].contains("$")) {
                        p.sendMessage(ColorMessage.getMSG("4"));
                        money = Integer.valueOf(args[i].replace("$", ""));
                    }
                }
            }
            if (msg.equalsIgnoreCase("====== " + Ucmodify.minecraft.player.getName() + "'s Statistiken ======")) {
                p.sendMessage(ColorMessage.getMSG("5"));
                e.setCanceled(true);
            }
            if (msg.contains(" - ") && msg.contains(":")) {
                p.sendMessage(ColorMessage.getMSG("6"));
                e.setCanceled(true);
            }
            if (msg.contains("[")) {
                p.sendMessage(ColorMessage.getMSG("7"));
                e.setCanceled(false);
            }
        }

    }
}

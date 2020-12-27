package net.CrystalRage.crystension.command.universal;

import net.CrystalRage.crystension.Crystension;
import net.CrystalRage.crystension.methods.ColorMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Timer;
import java.util.TimerTask;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class Einzahlen extends CommandBase implements IClientCommand {
    private static boolean start = false;
    private static int money = 0;


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
                    if (money>0) {
                        p.sendChatMessage("/bank einzahlen " + money);
                        money=0;
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Crystension.prefix + "Du hast kein Geld auf der Hand."));
                    }
                    start=false;
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
            if (msg.contains("- Geld:")) {
                for (int i=0;i<args.length;i++) {
                    if (args[i].contains("$")) {
                        money = Integer.valueOf(args[i].replace("$", ""));
                    }
                }
            }
            if (msg.equalsIgnoreCase("====== " + Crystension.minecraft.player.getName() + "'s Statistiken ======")) {
                e.setCanceled(true);
            }
            if (msg.contains(" - ") && msg.contains(":")) {
                e.setCanceled(true);
            }
            if (msg.contains("[")) {
                e.setCanceled(false);
            }
        }

    }
}

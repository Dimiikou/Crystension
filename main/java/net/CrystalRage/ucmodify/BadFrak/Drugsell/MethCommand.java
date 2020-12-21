package net.CrystalRage.ucmodify.BadFrak.Drugsell;

import com.google.common.collect.Lists;
import net.CrystalRage.ucmodify.Ucmodify;
import net.CrystalRage.ucmodify.handlers.ConfigHandler;
import net.CrystalRage.ucmodify.methods.ColorMessage;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class MethCommand extends CommandBase implements IClientCommand {

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "meth";
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
            // /drug SPIELER REINHEIT MENGE
            if (args.length == 3) {
                String player = args[0];
                int reinheit = Integer.valueOf(args[1]);
                int menge = Integer.valueOf(args[2]);
                if (reinheit == 0) {
                    if (menge > 0) {
                        p.sendChatMessage("/selldrug " + player + " Meth 0 " + menge + " " + (menge * ConfigHandler.FA_NullerMeth));
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Du kannst erst Drogen ab einem Gramm verkaufen."));
                    }
                } else if (reinheit == 1) {
                    if (menge > 0) {
                        p.sendChatMessage("/selldrug " + player + " Meth 1 " + menge + " " + (menge * ConfigHandler.FB_EinserMeth));
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Du kannst erst Drogen ab einem Gramm verkaufen."));
                    }
                } else if (reinheit == 2) {
                    if (menge > 0) {
                        p.sendChatMessage("/selldrug " + player + " Meth 2 " + menge + " " + (menge * ConfigHandler.FC_ZweierMeth));
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Du kannst erst Drogen ab einem Gramm verkaufen."));
                    }
                } else if (reinheit == 3) {
                    if (menge > 0) {
                        p.sendChatMessage("/selldrug " + player + " Meth 3 " + menge + " " + (menge * ConfigHandler.FD_DreierMeth));
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Du kannst erst Drogen ab einem Gramm verkaufen."));
                    }
                } else {
                    p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "§7Bitte gib einen der folgenen Reinheitsgrade an:"));
                    p.sendMessage(ColorMessage.getMSG(" §8\u25CF §e0 §7- §e" + ConfigHandler.FA_NullerMeth));
                    p.sendMessage(ColorMessage.getMSG(" §8\u25CF §e1 §7- §e" + ConfigHandler.FB_EinserMeth));
                    p.sendMessage(ColorMessage.getMSG(" §8\u25CF §e2 §7- §e" + ConfigHandler.FC_ZweierMeth));
                    p.sendMessage(ColorMessage.getMSG(" §8\u25CF §e3 §7- §e" + ConfigHandler.FD_DreierMeth));
                }
            } else {
                p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "/meth §7<§eSpielername§7> <§eReinheit in Zahl§7> <§eMenge§7>§8."));
            }

            return;
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    public List getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        NetHandlerPlayClient connection = Minecraft.getMinecraft().player.connection;
        List<NetworkPlayerInfo> playerInfo = new ArrayList(connection.getPlayerInfoMap());
        List<String> playerList = Lists.<String>newArrayList();
        if (args.length==1) {
            for (int i = 0; i < playerInfo.size(); ++i) {
                if (i < playerInfo.size()) {
                    playerList.add(playerInfo.get(i).getGameProfile().getName());
                }
            }
            return CommandBase.getListOfStringsMatchingLastWord(args, playerList);
        }
        if (args.length==2) {
            ArrayList<String> list = new ArrayList<>();
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("3");
            return list;
        }
        return new ArrayList();
    }
}
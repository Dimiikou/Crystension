package net.CrystalRage.crystension.command.BadFrak.giftdrug;

import com.google.common.collect.Lists;
import net.CrystalRage.crystension.Crystension;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class Giftlsd extends CommandBase implements IClientCommand {

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "lsd";
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
            // /drug SPIELER MENGE
            if (args.length == 2) {
                String player = args[0];
                int menge = Integer.valueOf(args[1]);

                if (menge > 0) {
                    p.sendChatMessage("/selldrug " + player + " lsd 0 " + menge + " " + (menge * ConfigHandler.GA_LSD));
                } else {
                    p.sendMessage(ColorMessage.getMSG(Crystension.prefix + "Du kannst erst Drogen ab einem Gramm verkaufen."));
                }
            } else {
                p.sendMessage(ColorMessage.getMSG(Crystension.prefix + "/lsd §7<§eSpielername§7> <§eMenge§7>§8."));
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

        return new ArrayList();
    }
}

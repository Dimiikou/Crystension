package net.CrystalRage.crystension.command.BadFrak;

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
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class Gifteigenbedarf extends CommandBase implements IClientCommand {

    private static boolean checkkoks = false;
    private static boolean checkmeth = false;
    private static String target;

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "gifteigenbedarf";
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

            if (args.length == 1) {
                target = args[0];

                if (ConfigHandler.KA_EigenbedarfGras) {
                    if (ConfigHandler.KC_GrasMenge > 0 && (ConfigHandler.KB_GrasReinheit >= 0 && ConfigHandler.KB_GrasReinheit <= 3)) {
                        p.sendChatMessage("/selldrug " + target + " Gras " + ConfigHandler.KB_GrasReinheit + " " + ConfigHandler.KC_GrasMenge + " 1");

                        if (ConfigHandler.KD_EigenbedarfKoks) {
                            checkkoks = true;
                        } else if (ConfigHandler.KG_EigenbedarfMeth) {
                            checkmeth = true;
                        }

                    } else {
                        p.sendMessage(ColorMessage.getMSG(Crystension.prefix + "Deine Menge für §eGras §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                    }
                } else if (ConfigHandler.KD_EigenbedarfKoks) {
                    if (ConfigHandler.KF_KoksMenge > 0 && (ConfigHandler.KE_KoksReinheit >= 0 && ConfigHandler.KE_KoksReinheit <= 3)) {
                        p.sendChatMessage("/selldrug " + target + " Koks " + ConfigHandler.KE_KoksReinheit + " " + ConfigHandler.KF_KoksMenge + " 1");

                        if (ConfigHandler.KG_EigenbedarfMeth) {
                            checkmeth = true;
                        }
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Crystension.prefix + "Deine Menge für §eKoks §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                    }
                    checkkoks = true;
                } else if (ConfigHandler.KG_EigenbedarfMeth) {
                    if (ConfigHandler.KI_MethMenge > 0 && (ConfigHandler.KH_MethReinheit >= 0 && ConfigHandler.KH_MethReinheit <= 3)) {
                        p.sendChatMessage("/selldrug " + target + " Meth " + ConfigHandler.KE_KoksReinheit + " " + ConfigHandler.KF_KoksMenge + " 1");
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Crystension.prefix + "Deine Menge für §eMeth §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                    }
                    checkmeth = true;

                } else {
                    p.sendMessage(ColorMessage.getMSG("Bitte nehm einstellung beim §eEigenbedarf §7vor."));
                }
            } else {
                p.sendMessage(ColorMessage.getMSG(Crystension.prefix + "/gifteigenbedarf §7<§eSpielername§7>."));
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

    /*
    Chat event
     */

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if(!Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) return;
        String msg = e.getMessage().getUnformattedText();
        EntityPlayerSP p = Minecraft.getMinecraft().player;
        if (checkkoks) {
            if (msg.contains("[Deal]") && msg.contains("hat den Deal angenommen.")) {

                if (ConfigHandler.KF_KoksMenge > 0 && (ConfigHandler.KE_KoksReinheit >= 0 && ConfigHandler.KE_KoksReinheit <= 3)) {
                    p.sendChatMessage("/selldrug " + target + " Koks " + ConfigHandler.KE_KoksReinheit + " " + ConfigHandler.KF_KoksMenge + " 1");

                    if (ConfigHandler.KG_EigenbedarfMeth) {
                        checkmeth = true;
                    }
                } else {
                    p.sendMessage(ColorMessage.getMSG(Crystension.prefix + "Deine Menge für §eKoks §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                }

                checkkoks=false;
            } else if (msg.contains("[Deal]") && msg.contains("hat den Deal abgelehnt.")) {
                if (ConfigHandler.KF_KoksMenge > 0 && (ConfigHandler.KE_KoksReinheit >= 0 && ConfigHandler.KE_KoksReinheit <= 3)) {
                    p.sendChatMessage("/selldrug " + target + " Koks " + ConfigHandler.KE_KoksReinheit + " " + ConfigHandler.KF_KoksMenge + " 1");

                    if (ConfigHandler.KG_EigenbedarfMeth) {
                        checkmeth = true;
                    }
                } else {
                    p.sendMessage(ColorMessage.getMSG(Crystension.prefix + "Deine Menge für §eKoks §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                }

                checkkoks=false;
            }
        } else if (checkmeth) {
            if (msg.contains("[Deal]") && msg.contains("hat den Deal angenommen.")) {
                if (ConfigHandler.KI_MethMenge > 0 && (ConfigHandler.KH_MethReinheit >= 0 && ConfigHandler.KH_MethReinheit <= 3)) {
                    p.sendChatMessage("/selldrug " + target + " Meth " + ConfigHandler.KE_KoksReinheit + " " + ConfigHandler.KF_KoksMenge + " 1");
                } else {
                    p.sendMessage(ColorMessage.getMSG(Crystension.prefix + "Deine Menge für §eMeth §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                } checkmeth=false;
            } else if (msg.contains("[Deal]") && msg.contains("hat den Deal abgelehnt.")) {
                checkmeth=false;
            }
        }

    }

}
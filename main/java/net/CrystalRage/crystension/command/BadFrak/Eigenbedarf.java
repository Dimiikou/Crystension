package net.CrystalRage.crystension.command.BadFrak;

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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

@SideOnly(Side.CLIENT)
public class Eigenbedarf extends CommandBase implements IClientCommand {
    private boolean gras = false;
    private boolean koks = false;
    private boolean meth = false;


    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "eigenbedarf";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) return;
        if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) {
            gras = false;
            koks = false;
            meth = false;
            EntityPlayerSP p = Minecraft.getMinecraft().player;
            // /drug SPIELER REINHEIT MENGE
            if (args.length == 0) {
                /*
                    Eigenbedarf Gras 1. Droge
                     */
                if (ConfigHandler.KA_EigenbedarfGras) {
                    if (ConfigHandler.KC_GrasMenge > 0 && (ConfigHandler.KB_GrasReinheit >= 0 && ConfigHandler.KB_GrasReinheit <= 3)) {
                        p.sendChatMessage("/dbank get Gras " + ConfigHandler.KC_GrasMenge + " " + ConfigHandler.KB_GrasReinheit);
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Deine Menge für §eGras §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                    }

                    gras=true;
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //Drogen Rausnahme
                            if (ConfigHandler.KD_EigenbedarfKoks) {
                                if (ConfigHandler.KF_KoksMenge > 0 && (ConfigHandler.KE_KoksReinheit >= 0 && ConfigHandler.KE_KoksReinheit <= 3)) {
                                    p.sendChatMessage("/dbank get Koks " + ConfigHandler.KF_KoksMenge + " " + ConfigHandler.KE_KoksReinheit);
                                } else {
                                    p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Deine Menge für §eKoks §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                                }
                                koks=true;
                            } else if (ConfigHandler.KG_EigenbedarfMeth) {
                                if (ConfigHandler.KI_MethMenge > 0 && (ConfigHandler.KH_MethReinheit >= 0 && ConfigHandler.KH_MethReinheit <= 3)) {
                                    p.sendChatMessage("/dbank get Meth " + ConfigHandler.KI_MethMenge + " " + ConfigHandler.KH_MethReinheit);
                                } else {
                                    p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Deine Menge für §eMeth §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                                }

                                meth=true;
                            }
                            // 3.Drogen Rausnahme
                            if (!meth && ConfigHandler.KG_EigenbedarfMeth) {
                                Timer s = new Timer();
                                s.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        if (ConfigHandler.KI_MethMenge > 0 && (ConfigHandler.KH_MethReinheit >= 0 && ConfigHandler.KH_MethReinheit <= 3)) {
                                            p.sendChatMessage("/dbank get Meth " + ConfigHandler.KI_MethMenge + " " + ConfigHandler.KH_MethReinheit);
                                        } else {
                                            p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Deine Menge für §eMeth §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                                        }
                                    }
                                }, 1500);
                            }

                        }
                    }, 1500);
                    /*
                    Eigenbedarf Koks 1. Droge
                     */
                } else if (ConfigHandler.KD_EigenbedarfKoks) {
                    if (ConfigHandler.KF_KoksMenge > 0 && (ConfigHandler.KE_KoksReinheit >= 0 && ConfigHandler.KE_KoksReinheit <= 3)) {
                        p.sendChatMessage("/dbank get Koks " + ConfigHandler.KF_KoksMenge + " " + ConfigHandler.KE_KoksReinheit);
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Deine Menge für §eKoks §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                    }
                    koks=true;
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (ConfigHandler.KG_EigenbedarfMeth) {
                                if (ConfigHandler.KI_MethMenge > 0 && (ConfigHandler.KH_MethReinheit >= 0 && ConfigHandler.KH_MethReinheit <= 3)) {
                                    p.sendChatMessage("/dbank get Meth " + ConfigHandler.KI_MethMenge + " " + ConfigHandler.KH_MethReinheit);
                                } else {
                                    p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Deine Menge für §eMeth §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                                }
                                meth=true;
                            }

                        }
                    }, 1500);
                    /*
                    Eigenbedarf Meth 1. Droge
                     */
                } else if (ConfigHandler.KG_EigenbedarfMeth) {
                    if (ConfigHandler.KI_MethMenge > 0 && (ConfigHandler.KH_MethReinheit >= 0 && ConfigHandler.KH_MethReinheit <= 3)) {
                        p.sendChatMessage("/dbank get Meth " + ConfigHandler.KI_MethMenge + " " + ConfigHandler.KH_MethReinheit);
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Deine Menge für §eMeth §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                    }
                    meth=true;
                } else {
                    p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Du hast alle Drogen für den Eigenbedarf deaktiviert."));
                }
            } else if (args.length == 1){
                if (args[0].equalsIgnoreCase("Gras") || args[0].equalsIgnoreCase("Weed") || args[0].equalsIgnoreCase("Marihuana")) {
                    if (ConfigHandler.KC_GrasMenge > 0 && (ConfigHandler.KB_GrasReinheit >= 0 && ConfigHandler.KB_GrasReinheit <= 3)) {
                        p.sendChatMessage("/dbank get Gras " + ConfigHandler.KC_GrasMenge + " " + ConfigHandler.KB_GrasReinheit);
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Deine Menge für §eGras §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                    }
                } else if (args[0].equalsIgnoreCase("Koks") || args[0].equalsIgnoreCase("Kokain")) {
                    if (ConfigHandler.KF_KoksMenge > 0 && (ConfigHandler.KE_KoksReinheit >= 0 && ConfigHandler.KE_KoksReinheit <= 3)) {
                        p.sendChatMessage("/dbank get Koks " + ConfigHandler.KF_KoksMenge + " " + ConfigHandler.KE_KoksReinheit);
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Deine Menge für §eKoks §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                    }
                } else if (args[0].equalsIgnoreCase("Meth") || args[0].equalsIgnoreCase("Methamphetamin")) {
                    if (ConfigHandler.KI_MethMenge > 0 && (ConfigHandler.KH_MethReinheit >= 0 && ConfigHandler.KH_MethReinheit <= 3)) {
                        p.sendChatMessage("/dbank get Meth " + ConfigHandler.KI_MethMenge + " " + ConfigHandler.KH_MethReinheit);
                    } else {
                        p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Deine Menge für §eMeth §7muss mindestens §e1 §7betragen und die Reinheit einen wert zwischen §e0 §7und §e3§7."));
                    }
                }
            } else {
                p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "/eigenbedarf"));
                p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "/eigenbedarf §7<§eDroge§7>"));
            }

            return;
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    public List getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        if (args.length==1) {
            ArrayList<String> list = new ArrayList<>();
            list.add("Gras");
            list.add("Weed");
            list.add("Marihuana");
            list.add("Koks");
            list.add("Kokain");
            list.add("Meth");
            list.add("Methamphetamin");
            return list;
        }
        return new ArrayList();
    }

}

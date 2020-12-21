package net.CrystalRage.ucmodify.command.BadFrak;

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

import java.util.*;

@SideOnly(Side.CLIENT)
public class Eigenbedarf extends CommandBase implements IClientCommand {
    public static boolean gras = false;
    public static boolean koks = false;
    public static boolean meth = false;


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


                    p.sendChatMessage("/dbank get Gras " + ConfigHandler.KC_GrasMenge + " " + ConfigHandler.KB_GrasReinheit);
                    gras=true;
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //Drogen Rausnahme
                            if (ConfigHandler.KD_EigenbedarfKoks) {
                                p.sendChatMessage("/dbank get Koks " + ConfigHandler.KF_KoksMenge + " " + ConfigHandler.KE_KoksReinheit);
                                koks=true;
                            } else if (ConfigHandler.KG_EigenbedarfMeth) {
                                p.sendChatMessage("/dbank get Meth " + ConfigHandler.KI_MethMenge + " " + ConfigHandler.KH_MethReinheit);
                                meth=true;
                            }
                            // 3.Drogen Rausnahme
                            Timer s = new Timer();
                            s.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if (ConfigHandler.KD_EigenbedarfKoks && !(koks)) {
                                        p.sendChatMessage("/dbank get Koks " + ConfigHandler.KF_KoksMenge + " " + ConfigHandler.KE_KoksReinheit);
                                    } else if (ConfigHandler.KG_EigenbedarfMeth && !(meth)) {
                                        p.sendChatMessage("/dbank get Meth " + ConfigHandler.KI_MethMenge + " " + ConfigHandler.KH_MethReinheit);
                                    }
                                }
                            }, 1500);

                        }
                    }, 1500);
                    /*
                    Eigenbedarf Koks 1. Droge
                     */
                } else if (ConfigHandler.KD_EigenbedarfKoks) {
                    p.sendChatMessage("/dbank get Koks " + ConfigHandler.KF_KoksMenge + " " + ConfigHandler.KE_KoksReinheit);
                    koks=true;
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //Drogen Rausnahme
                            if (ConfigHandler.KA_EigenbedarfGras) {
                                p.sendChatMessage("/dbank get Gras " + ConfigHandler.KC_GrasMenge + " " + ConfigHandler.KB_GrasReinheit);
                                gras=true;
                            } else if (ConfigHandler.KG_EigenbedarfMeth) {
                                p.sendChatMessage("/dbank get Meth " + ConfigHandler.KI_MethMenge + " " + ConfigHandler.KH_MethReinheit);
                                meth=true;
                            }
                            // 3.Drogen Rausnahme
                            Timer s = new Timer();
                            s.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if (ConfigHandler.KA_EigenbedarfGras && !(gras)) {
                                        p.sendChatMessage("/dbank get Gras " + ConfigHandler.KC_GrasMenge + " " + ConfigHandler.KB_GrasReinheit);
                                    } else if (ConfigHandler.KG_EigenbedarfMeth && !(meth)) {
                                        p.sendChatMessage("/dbank get Meth " + ConfigHandler.KI_MethMenge + " " + ConfigHandler.KH_MethReinheit);
                                    }
                                }
                            }, 1500);

                        }
                    }, 1500);
                    /*
                    Eigenbedarf Meth 1. Droge
                     */
                } else if (ConfigHandler.KG_EigenbedarfMeth) {
                    p.sendChatMessage("/dbank get Meth " + ConfigHandler.KI_MethMenge + " " + ConfigHandler.KH_MethReinheit);
                    meth=true;
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //Drogen Rausnahme
                            if (ConfigHandler.KA_EigenbedarfGras) {
                                p.sendChatMessage("/dbank get Gras " + ConfigHandler.KC_GrasMenge + " " + ConfigHandler.KB_GrasReinheit);
                                gras=true;
                            } else if (ConfigHandler.KD_EigenbedarfKoks) {
                                p.sendChatMessage("/dbank get Koks " + ConfigHandler.KF_KoksMenge + " " + ConfigHandler.KE_KoksReinheit);
                                koks=true;
                            }
                            // 3.Drogen Rausnahme
                            Timer s = new Timer();
                            s.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if (ConfigHandler.KA_EigenbedarfGras && !(gras)) {
                                        p.sendChatMessage("/dbank get Gras " + ConfigHandler.KC_GrasMenge + " " + ConfigHandler.KB_GrasReinheit);
                                    } else if (ConfigHandler.KD_EigenbedarfKoks && !(koks)) {
                                        p.sendChatMessage("/dbank get Koks " + ConfigHandler.KF_KoksMenge + " " + ConfigHandler.KE_KoksReinheit);
                                    }
                                }
                            }, 1500);

                        }
                    }, 1500);
                } else {
                    p.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Du hast alle Drogen für den Eigenbedarf deaktiviert."));
                }
            } else if (args.length == 1){

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

    public List<String> getTabCompletions(EntityPlayerSP p, String[] args) {
        if (args.length == 1) {
            return Collections.emptyList();
        } else if (args.length == 2) {
            List<String> list = new ArrayList<>();
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("3");
            return list;
        }

        return null;

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

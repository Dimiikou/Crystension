package net.CrystalRage.crystension;

import net.CrystalRage.crystension.command.BadFrak.Gifteigenbedarf;
import net.CrystalRage.crystension.command.BadFrak.drugsell.GrasCommand;
import net.CrystalRage.crystension.command.BadFrak.drugsell.KoksCommand;
import net.CrystalRage.crystension.command.BadFrak.drugsell.LSDCommand;
import net.CrystalRage.crystension.command.BadFrak.drugsell.MethCommand;
import net.CrystalRage.crystension.command.BadFrak.giftdrug.Giftgras;
import net.CrystalRage.crystension.command.BadFrak.giftdrug.Giftkoks;
import net.CrystalRage.crystension.command.BadFrak.giftdrug.Giftlsd;
import net.CrystalRage.crystension.command.BadFrak.giftdrug.Giftmeth;
import net.CrystalRage.crystension.command.BadFrak.Eigenbedarf;
import net.CrystalRage.crystension.command.jobs.Adropmanager;
import net.CrystalRage.crystension.command.jobs.jobcommands.AdropmoneyCMD;
import net.CrystalRage.crystension.command.jobs.jobcommands.AdropschwarzpulverCMD;
import net.CrystalRage.crystension.command.medics.DChatSperre;
import net.CrystalRage.crystension.command.medics.mediccommands.DChatSperrecommand;
import net.CrystalRage.crystension.command.medics.mediccommands.Separateleichencmd;
import net.CrystalRage.crystension.command.medics.mediccommands.Separateleichen;
import net.CrystalRage.crystension.command.universal.Einzahlen;
import net.CrystalRage.crystension.command.universal.Reichensteuer;
import net.CrystalRage.crystension.command.universal.universalcommands.Checkafk;
import net.CrystalRage.crystension.command.universal.universalcommands.RefreshFrakList;
import net.CrystalRage.crystension.command.universal.universalcommands.RsCommand;
import net.CrystalRage.crystension.handlers.ConfigHandler;
import net.CrystalRage.crystension.listeners.*;

import net.CrystalRage.crystension.listeners.Karma.ChangeKarmaMessage;
import net.CrystalRage.crystension.methods.FrakListFiller;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Crystension.MODID, name = Crystension.NAME, version = Crystension.VERSION, clientSideOnly = true)
@SideOnly(Side.CLIENT)
public class Crystension {
    public static final String MODID = "crystension";
    public static final String NAME = "Crystension";
    public static final String VERSION = "2.2.1";
    public static String prefix = "\2473Crystension \2478\u25CF\2477 ";
    private static Logger logger;
    public static Minecraft minecraft = Minecraft.getMinecraft();

    public static boolean AFK = false;

    public static File config;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(ChatEvent.class);
        ConfigManager.sync(MODID, Config.Type.INSTANCE);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        ClientCommandHandler.instance.registerCommand(new Separateleichencmd());
        ClientCommandHandler.instance.registerCommand(new RsCommand());
        ClientCommandHandler.instance.registerCommand(new DChatSperrecommand());
        ClientCommandHandler.instance.registerCommand(new AdropmoneyCMD());
        ClientCommandHandler.instance.registerCommand(new AdropschwarzpulverCMD());
        ClientCommandHandler.instance.registerCommand(new Einzahlen());

        ClientCommandHandler.instance.registerCommand(new RefreshFrakList());
        ClientCommandHandler.instance.registerCommand(new Checkafk());

        ClientCommandHandler.instance.registerCommand(new GrasCommand());
        ClientCommandHandler.instance.registerCommand(new KoksCommand());
        ClientCommandHandler.instance.registerCommand(new LSDCommand());
        ClientCommandHandler.instance.registerCommand(new MethCommand());
        ClientCommandHandler.instance.registerCommand(new Eigenbedarf());
        ClientCommandHandler.instance.registerCommand(new Gifteigenbedarf());

        ClientCommandHandler.instance.registerCommand(new Giftgras());
        ClientCommandHandler.instance.registerCommand(new Giftkoks());
        ClientCommandHandler.instance.registerCommand(new Giftlsd());
        ClientCommandHandler.instance.registerCommand(new Giftmeth());

        MinecraftForge.EVENT_BUS.register(new Separateleichen());
        MinecraftForge.EVENT_BUS.register(new Reichensteuer());
        MinecraftForge.EVENT_BUS.register(new DChatSperre());
        MinecraftForge.EVENT_BUS.register(new Adropmanager());
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
        MinecraftForge.EVENT_BUS.register(new ChangeKarmaMessage());
        MinecraftForge.EVENT_BUS.register(new FrakListFiller());
        MinecraftForge.EVENT_BUS.register(new ServerConnectEvent());
        MinecraftForge.EVENT_BUS.register(new NametagChanger());
        MinecraftForge.EVENT_BUS.register(new Toggleadd());
        MinecraftForge.EVENT_BUS.register(new HQNachrichten());
        MinecraftForge.EVENT_BUS.register(new Togglenews());
        MinecraftForge.EVENT_BUS.register(new Gifteigenbedarf());
        MinecraftForge.EVENT_BUS.register(new Einzahlen());

        FrakListFiller.startTimer();
    }
}

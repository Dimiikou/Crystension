package net.CrystalRage.ucmodify.handlers;

import net.CrystalRage.ucmodify.Ucmodify;
import net.CrystalRage.ucmodify.methods.ColorMessage;
import net.CrystalRage.ucmodify.methods.FrakListFiller;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Ucmodify.MODID, name = Ucmodify.NAME)
@Mod.EventBusSubscriber
public class ConfigHandler {

    // Allgemeines
    @Config.Name("AA_Farbenblindenmodus")
    @Config.Comment("Stelle ein, ob du den Farbenblindenmodus aktivieren m\u00f6chtest")
    public static boolean AA_Farbenblindenmodus = false;

    // Medic Shit
    @Config.Name("BA_Notrufnachrichten")
    @Config.Comment("Stelle ein ob du ver\u00e4nderte Notrufnachrichten haben m\u00f6chtest")
    public static boolean BA_Notrufnachrichten = true;

    @Config.Name("BB_Dchatsperre")
    @Config.Comment("Stelle ein, ob du \u00fcber D-Chat Sperren informiert werden m\u00f6chtest")
    public static boolean BB_Dchatsperre = true;

    // Gras
    @Config.Name("DA_NullerGras")
    @Config.Comment("Stelle den Preis f\u00fcr H\u00f6chste Reinheit Gras ein")
    public static int DA_NullerGras = 85;

    @Config.Name("DB_EinserGras")
    @Config.Comment("Stelle den Preis f\u00fcr Gute Reinheit Gras ein")
    public static int DB_EinserGras = 75;

    @Config.Name("DC_ZweierGras")
    @Config.Comment("Stelle den Preis f\u00fcr Mittlere Reinheit Gras ein")
    public static int DC_ZweierGras = 65;

    @Config.Name("DD_DreierGras")
    @Config.Comment("Stelle den Preis f\u00fcr Schlechte Reinheit Gras ein")
    public static int DD_DreierGras = 55;

    // Koks
    @Config.Name("EA_NullerKoks")
    @Config.Comment("Stelle den Preis f\u00fcr H\u00f6chste Reinheit Koks ein")
    public static int EA_NullerKoks = 75;

    @Config.Name("EB_EinserKoks")
    @Config.Comment("Stelle den Preis f\u00fcr Gute Reinheit Koks ein")
    public static int EB_EinserKoks = 65;

    @Config.Name("EC_ZweierKoks")
    @Config.Comment("Stelle den Preis f\u00fcr Mittlere Reinheit Koks ein")
    public static int EC_ZweierKoks = 55;

    @Config.Name("ED_DreierKoks")
    @Config.Comment("Stelle den Preis f\u00fcr Schlechte Reinheit Koks ein")
    public static int ED_DreierKoks = 45;

    // Meth
    @Config.Name("FA_NullerMeth")
    @Config.Comment("Stelle den Preis f\u00fcr H\u00f6chste Reinheit Meth ein")
    public static int FA_NullerMeth = 75;

    @Config.Name("FB_EinserMeth")
    @Config.Comment("Stelle den Preis f\u00fcr Gute Reinheit Meth ein")
    public static int FB_EinserMeth = 65;

    @Config.Name("FC_ZweierMeth")
    @Config.Comment("Stelle den Preis f\u00fcr Mittlere Reinheit Meth ein")
    public static int FC_ZweierMeth = 55;

    @Config.Name("FD_DreierMeth")
    @Config.Comment("Stelle den Preis f\u00fcr Schlechte Reinheit Meth ein")
    public static int FD_DreierMeth = 45;

    // LSD
    @Config.Name("GA_LSD")
    @Config.Comment("Stelle den Preis f\u00fcr LSD ein")
    public static int GA_LSD = 1600;

    // H Inhalt Incomming


    //Fraknames
    @Config.Name("IA_ActivateFrakNameHighlighter")
    @Config.Comment("Aktiviere ob du Fraktions und B\u00fcndnissnamen Farblich gekennzeichnet haben m\u00f6chtest.")
    public static boolean IA_ActivateFrakNameHighlighter = false;

    @Config.Name("IB_DeineFraktion")
    @Config.Comment("Gib den Namen deiner Fraktion ein, welchen du bei Memberinfo verwendest (Zivilsten bitte als 'keine') eintragen")
    public static String IB_DeineFraktion = "keine";

    @Config.Name("IC_BuendnissFraktionEins")
    @Config.Comment("Gib den Namen deiner B\u00fcndnissfraktion ein, welchen du bei Memberinfo verwendest (Kein B\u00fcndniss bitte als 'keine') eintragen")
    public static String IC_BuendnissFraktionEins = "keine";

    @Config.Name("ID_BuendnissFraktionZwei")
    @Config.Comment("Gib den Namen deiner B\u00fcndnissfraktion ein, welchen du bei Memberinfo verwendest (Keine zweite B\u00fcndnissfrak bitte als 'keine') eintragen")
    public static String ID_BuendnissFraktionZwei = "keine";

    @Config.Name("IE_UpdateDelay")
    @Config.Comment("Stelle das Delay zwischen den Updates f\u00fcr das Laden der Fraktionen ein. (Minuten)")
    public static int IE_UpdateDelay = 2;

    @Config.Name("IF_Farbcode")
    @Config.Comment("Stelle die Farbe für die Namen deiner B\u00fcndnisspartner ein.")
    public static String IF_Farbcode = "9";

    // Karmanachricht
    @Config.Name("JA_ChangeKarmaMessage")
    @Config.Comment("Stelle ein, ob die Karma nachricht ver\u00e4ndert werden soll.")
    public static boolean JA_ChangeKarmaMessage = false;

    // Eigenbedarf Gras
    @Config.Name("KA_EigenbedarfGras")
    @Config.Comment("Stelle ein, ob du für deinen Eigenbedarf Gras haben m\u00f6chtest.")
    public static boolean KA_EigenbedarfGras = true;

    @Config.Name("KB_GrasReinheit")
    @Config.Comment("Stelle ein, welche Reinheit Gras du nehmen m\u00f6chtest.")
    public static int KB_GrasReinheit = 1;

    @Config.Name("KC_GrasMenge")
    @Config.Comment("Stelle ein, wie viel Gramm Gras du nehmen m\u00f6chtest.")
    public static int KC_GrasMenge = 15;

    // Eigenbedarf Koks
    @Config.Name("KD_EigenbedarfKoks")
    @Config.Comment("Stelle ein, ob du für deinen Eigenbedarf Koks haben m\u00f6chtest.")
    public static boolean KD_EigenbedarfKoks = true;

    @Config.Name("KE_KoksReinheit")
    @Config.Comment("Stelle ein, welche Reinheit Koks du nehmen m\u00f6chtest.")
    public static int KE_KoksReinheit = 1;

    @Config.Name("KF_KoksMenge")
    @Config.Comment("Stelle ein, wie viel Gramm Koks du nehmen m\u00f6chtest.")
    public static int KF_KoksMenge = 15;

    // Eigenbedarf Meth
    @Config.Name("KG_EigenbedarfMeth")
    @Config.Comment("Stelle ein, ob du f\u00fcr deinen Eigenbedarf Meth haben m\u00f6chtest.")
    public static boolean KG_EigenbedarfMeth = false;

    @Config.Name("KH_MethReinheit")
    @Config.Comment("Stelle ein, welche Reinheit Meth du nehmen m\u00f6chtest.")
    public static int KH_MethReinheit = 1;

    @Config.Name("KI_MethMenge")
    @Config.Comment("Stelle ein, wie viel Gramm Meth du nehmen m\u00f6chtest.")
    public static int KI_MethMenge = 15;

    // HQ Nachrichten
    @Config.Name("LA_HQNachrichten")
    @Config.Comment("Stelle ein, ob du HQ Nachrichten ver\u00e4ndern m\u00f6chtest.")
    public static boolean LA_HQNachrichten = true;

    // Ads
    @Config.Name("MA_AdsBlockieren")
    @Config.Comment("Stelle ein, ob du Ads blockieren m\u00f6chtest.")
    public static boolean MA_AdsBlockieren = false;

    @Config.Name("MB_NewsBlockieren")
    @Config.Comment("Stelle ein, ob du News blockieren m\u00f6chtest.")
    public static boolean MB_NewsBlockieren = false;

    // BLs
    @Config.Name("NA_FrakschaedigungName")
    @Config.Comment("Stelle den BL Name für Fraktionssch\u00e4digung ein.")
    public static String NA_FrakschaedigungName = "Fraktionssch\u00e4digung";

    @Config.Name("NB_FrakschaedigungKills")
    @Config.Comment("Stelle die Kill anzahl für Fraktionssch\u00e4digung ein.")
    public static int NB_FrakschaedigungKills = 40;

    @Config.Name("NC_FrakschaedigungPreis")
    @Config.Comment("Stelle den Preis für Fraktionssch\u00e4digung ein.")
    public static int NC_FrakschaedigungPreis = 4000;

    private static int IE_before = IE_UpdateDelay;

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent e) {
        if (e == null || e.getModID().equals(Ucmodify.MODID)) {
            ConfigManager.sync(Ucmodify.MODID, Config.Type.INSTANCE);

            if (IE_before != IE_UpdateDelay) {
                FrakListFiller.t.cancel();
                FrakListFiller.startTimer();
            }

            if (IE_UpdateDelay < 5) {
                IE_UpdateDelay = 5;
                Ucmodify.minecraft.player.sendMessage(ColorMessage.getMSG(Ucmodify.prefix + "Der Wert für §eIE_UpdateDelay §7muss über §e5 §7liegen."));
            }

        }
    }
}

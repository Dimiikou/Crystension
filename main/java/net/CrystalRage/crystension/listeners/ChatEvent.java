package net.CrystalRage.crystension.listeners;

import net.CrystalRage.crystension.Ucmodify;
import net.CrystalRage.crystension.command.medics.DChatSperre;
import net.CrystalRage.crystension.handlers.ConfigHandler;
import net.CrystalRage.crystension.methods.FrakListFiller;
import net.minecraftforge.fml.common.Mod;
import net.CrystalRage.crystension.methods.ColorMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class ChatEvent {

	public static boolean service = false;
	public static final Pattern BOMB_PLACED = Pattern.compile("^News: ACHTUNG! Es wurde eine Bombe in der .+ von .+ gefunden!$");

	@SubscribeEvent
	public static void onChat(ClientChatReceivedEvent e) {
		EntityPlayerSP p = (Minecraft.getMinecraft().player);
		if (ConfigHandler.BA_Notrufnachrichten) {
			if (ConfigHandler.AA_Farbenblindenmodus) {
				if (e.getMessage().getUnformattedText().contains("Ein Notruf von")) {
					String[] s = e.getMessage().getUnformattedText().split(" ");
					String Name = s[3];
					String grund = "";
					for(int i = 5; i<= (s.length-1); i++) {
						grund = grund+s[i]+" ";
					}
					if(grund.equals("")) {
						grund = "RDE";
					}
					e.setMessage(getComponentText(ColorMessage.getMSG("§bNeuer Notruf §7- §e" + Name + "§7 - §e" + grund), ColorMessage.getMSG("§e*Klick*"), "/acceptservice " + Name.replace("[UC]", "")));
				} else if (e.getMessage().getUnformattedText().contains("hat den Notruf von") && e.getMessage().getUnformattedText().contains("wieder ge\u00f6ffnet.")) {
					String[] s = e.getMessage().getUnformattedText().split(" ");
					String oeffner = s[0];
					String Name = s[5];
					if (oeffner.contains(p.getName())) {
						service = false;
					}
					e.setMessage(getComponentText(ColorMessage.getMSG("§3Neu Geöffnet §7- §e" + oeffner + "§7 - §e" + Name), ColorMessage.getMSG("§e*Klick*"), "/acceptservice " + Name.replace("[UC]", "")));

				} else if (e.getMessage().getUnformattedText().contains("hat den Notruf von") && e.getMessage().getUnformattedText().contains("angenommen.")) {
					String[] s = e.getMessage().getUnformattedText().split(" ");
					String annehmer = s[0];
					String Name = s[5];
					String entfernung = s[7].replace("(", "");
					e.setMessage(ColorMessage.getMSG("§dAngenommen §7- §e" + Name + "§7 - §e" + annehmer + " §7 - §e" + entfernung));
					if (annehmer.contains(p.getName())) {
						service = true;
					}
				} else if (e.getMessage().getUnformattedText().contains("Du hast dein Ziel")) {
					if (service) {
						p.sendChatMessage("/doneservice");
						service = false;
					}
				} else if (e.getMessage().getUnformattedText().contains("Der Service wurde") && service) {
					service = false;
				} else if (e.getMessage().getUnformattedText().contains("Du hast den Service von") && e.getMessage().getUnformattedText().contains("markiert")) {
					service = false;
				}

				if (e.getMessage().getUnformattedText().contains("Der n\u00e4heste Punkt ist")) {
					String[] s = e.getMessage().getUnformattedText().split(" ");
					String ort = "";
					String entfernung = "";
					String naehester = "-";
					String naehesterent = "";
					String naehester2 = "-";
					String naehester2ent = "";
					boolean drunter = false;
					boolean stdistance = false;
					boolean ortende = false;

					for (int i=4; i <= (s.length-1); i++) {
						if (!ortende) {
							if (s[i].contains(".")) {
								ortende = true;
							}
							ort = ort + " " + s[i];
						}
					}
					for (int i=0; i <= (s.length-1); i++) {
						if (!stdistance) {
							if (s[i].contains("(")) {
								naehester = s[i-1] + " §7-";
								naehesterent = s[i].replace("(", "").replace(")", "").replace(".", "").replace(",", "");
								stdistance = true;
							}
						} else {
							if (s[i].contains("(")) {
								naehester2 = s[i-1] + " §7-";
								naehester2ent = s[i].replace("(", "").replace(")", "").replace(".", "").replace(",", "");
							}
						}
					}
					if (e.getMessage().getUnformattedText().contains(p.getName())) {
						drunter=true;
						if (naehester.contains(p.getName())) {
							entfernung = naehesterent;
						} else {
							entfernung = naehester2ent;
						}
					}
					if (drunter) {
						if (naehester.contains(Minecraft.getMinecraft().player.getName())) {
							if (!(naehester2.equalsIgnoreCase("-"))) {
								if (Integer.valueOf(naehester2ent.replace("m", "")) < Integer.valueOf(entfernung.replace("m", ""))) {
									e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Vlt. " + entfernung + " §7(§e" + naehester2ent + "§7)"), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
								} else {
									e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Ja " + entfernung), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
								}
							} else {
								e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Ja " + entfernung), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
							}
						} else if (naehester2.contains(Minecraft.getMinecraft().player.getName())) {
							if (!(naehester.equalsIgnoreCase("-"))) {
								if (Integer.valueOf(naehesterent.replace("m", "")) < Integer.valueOf(entfernung.replace("m", ""))) {
									e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Vlt. " + entfernung + " §7(§e" + naehesterent + "§7)"), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
								} else {
									e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Ja " + entfernung), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
								}
							} else {
								e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Ja " + entfernung), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
							}
						}
					} else {
						if (naehester.equalsIgnoreCase("-")) {
							e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Nein"), ColorMessage.getMSG("§8\u27A5 §e" + naehester +  "§e " + naehesterent), null));
						} else {
							e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Nein"), ColorMessage.getMSG("§8\u27A5 §e" + naehester +  "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 +  "§e " + naehester2ent), null));
						}
					}
				}
			} else {
				if (e.getMessage().getUnformattedText().contains("Ein Notruf von")) {
					String[] s = e.getMessage().getUnformattedText().split(" ");
					String Name = s[3];
					String grund = "";
					for(int i = 5; i<= (s.length-1); i++) {
						grund = grund+s[i]+" ";
					}
					if(grund.equals("")) {
						grund = "RDE";
					}
					e.setMessage(getComponentText(ColorMessage.getMSG("§cNeuer Notruf §7- §e" + Name + "§7 - §e" + grund), ColorMessage.getMSG("§e*Klick*"), "/acceptservice " + Name.replace("[UC]", "")));
				} else if (e.getMessage().getUnformattedText().contains("hat den Notruf von") && e.getMessage().getUnformattedText().contains("wieder ge\u00f6ffnet.")) {
					String[] s = e.getMessage().getUnformattedText().split(" ");
					String oeffner = s[0];
					String Name = s[5];
					if (oeffner.contains(p.getName())) {
						service = false;
					}
					e.setMessage(getComponentText(ColorMessage.getMSG("§4Neu Geöffnet §7- §e" + oeffner + "§7 - §e" + Name), ColorMessage.getMSG("§e*Klick*"), "/acceptservice " + Name.replace("[UC]", "")));

				} else if (e.getMessage().getUnformattedText().contains("hat den Notruf von") && e.getMessage().getUnformattedText().contains("angenommen.")) {
					String[] s = e.getMessage().getUnformattedText().split(" ");
					String annehmer = s[0];
					String Name = s[5];
					String entfernung = s[7].replace("(", "");
					e.setMessage(ColorMessage.getMSG("§aAngenommen §7- §e" + Name + "§7 - §e" + annehmer + " §7 - §e" + entfernung));
					if (annehmer.contains(p.getName())) {
						service = true;
					}
				} else if (e.getMessage().getUnformattedText().contains("Du hast dein Ziel")) {
					if (service) {
						p.sendChatMessage("/doneservice");
						service = false;
					}
				} else if (e.getMessage().getUnformattedText().contains("Der Service wurde") && service) {
					service = false;
				} else if (e.getMessage().getUnformattedText().contains("Du hast den Service von") && e.getMessage().getUnformattedText().contains("markiert")) {
					service = false;
				}

				if (e.getMessage().getUnformattedText().contains("Der n\u00e4heste Punkt ist")) {
					String[] s = e.getMessage().getUnformattedText().split(" ");
					String ort = "";
					String entfernung = "";
					String naehester = "-";
					String naehesterent = "";
					String naehester2 = "-";
					String naehester2ent = "";
					boolean drunter = false;
					boolean stdistance = false;
					boolean ortende = false;

					for (int i=4; i <= (s.length-1); i++) {
						if (!ortende) {
							if (s[i].contains(".")) {
								ortende = true;
							}
							ort = ort + " " + s[i];
						}
					}
					for (int i=0; i <= (s.length-1); i++) {
						if (!stdistance) {
							if (s[i].contains("(")) {
								naehester = s[i-1] + " §7-";
								naehesterent = s[i].replace("(", "").replace(")", "").replace(".", "").replace(",", "");
								stdistance = true;
							}
						} else {
							if (s[i].contains("(")) {
								naehester2 = s[i-1] + " §7-";
								naehester2ent = s[i].replace("(", "").replace(")", "").replace(".", "").replace(",", "");
							}
						}
					}
					if (e.getMessage().getUnformattedText().contains(p.getName())) {
						drunter=true;
						if (naehester.contains(p.getName())) {
							entfernung = naehesterent;
						} else {
							entfernung = naehester2ent;
						}
					}
					if (drunter) {
						if (naehester.contains(Minecraft.getMinecraft().player.getName())) {
							if (!(naehester2.equalsIgnoreCase("-"))) {
								if (Integer.valueOf(naehester2ent.replace("m", "")) < Integer.valueOf(entfernung.replace("m", ""))) {
									e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Vlt. " + entfernung + " §7(§e" + naehester2ent + "§7)"), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
								} else {
									e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Ja " + entfernung), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
								}
							} else {
								e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Ja " + entfernung), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
							}
						} else if (naehester2.contains(Minecraft.getMinecraft().player.getName())) {
							if (!(naehester.equalsIgnoreCase("-"))) {
								if (Integer.valueOf(naehesterent.replace("m", "")) < Integer.valueOf(entfernung.replace("m", ""))) {
									e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Vlt. " + entfernung + " §7(§e" + naehesterent + "§7)"), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
								} else {
									e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Ja " + entfernung), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
								}
							} else {
								e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Ja " + entfernung), ColorMessage.getMSG("§8\u27A5 §e" + naehester + "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 + "§e " + naehester2ent), null));
							}
						}
					} else {
						if (naehester.equalsIgnoreCase("-")) {
							e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Nein"), ColorMessage.getMSG("§8\u27A5 §e" + naehester +  "§e " + naehesterent), null));
						} else {
							e.setMessage(getComponentText(ColorMessage.getMSG("§8\u27A5§e" + ort.replace(".", "") + " §7-§e " + "Nein"), ColorMessage.getMSG("§8\u27A5 §e" + naehester +  "§e " + naehesterent + " \n§8\u27A5§e " + naehester2 +  "§e " + naehester2ent), null));
						}
					}
				}
			}
		}

		if (e.getMessage().getUnformattedText().contains("Du bist nun im AFK")) {
			Ucmodify.AFK = true;
		} else if (e.getMessage().getUnformattedText().contains("Du bist nun nicht mehr im AFK")) {
			Ucmodify.AFK = false;
			FrakListFiller.loading = false;
		}

		if (BOMB_PLACED.matcher(e.getMessage().getUnformattedText().toString()).find()) {
			if (ConfigHandler.BB_Dchatsperre) {
				DChatSperre.dchatsperre = true;
			}
		}

	}

	public static ITextComponent getComponentText(TextComponentString text, TextComponentString hover, String action) {
		ITextComponent comp = text;
		if(hover != null) {
			comp.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
		}
		if(action != null) {
			comp.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, action));
		}
		return comp;
	}

}
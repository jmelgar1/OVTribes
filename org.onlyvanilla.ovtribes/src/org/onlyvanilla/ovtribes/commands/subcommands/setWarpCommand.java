package org.onlyvanilla.ovtribes.commands.subcommands;

import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovtribes.Main;
import org.onlyvanilla.ovtribes.commands.SubCommand;
import org.onlyvanilla.ovtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class setWarpCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "setwarp";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Set a tribe warp";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes setwarp";
	}

	@Override
	public void perform(Player p, String[] args) {
		if (args.length == 2) {
			String playerTribe = tribeManager.getPlayerTribe(p);
			if(tribeManager.CheckForElder(playerTribe, p) == true || tribeManager.CheckForChief(playerTribe, p) == true) {
				if(tribeManager.getNumberOfWarps(playerTribe) < tribeManager.getMaxWarps(playerTribe)) {
					String warpName = args[1];
					if(warpName.matches("[a-zA-Z0-9]*") == true) {
						tribeManager.setWarp(playerTribe, p, warpName);
					} else {
						p.sendMessage(ChatColor.RED + "Warp names must only contain letters and/or numbers!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Tribe's max number of warps is " + tribeManager.getMaxWarps(playerTribe) + "!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Only chiefs and elders can set warps!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: /tribes setwarp [warp_name]");
		}
	}

}

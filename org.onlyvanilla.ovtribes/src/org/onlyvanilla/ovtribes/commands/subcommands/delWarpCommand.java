package org.onlyvanilla.ovtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.onlyvanilla.ovtribes.Main;
import org.onlyvanilla.ovtribes.commands.SubCommand;
import org.onlyvanilla.ovtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class delWarpCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "delwarp";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Delete a tribe warp";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes delwarp";
	}

	@Override
	public void perform(Player p, String[] args) {
		if (args.length == 2) {
			String playerTribe = tribeManager.getPlayerTribe(p);
			if(tribeManager.CheckForElder(playerTribe, p) == true || tribeManager.CheckForChief(playerTribe, p) == true) {
				String warpName = args[1];
				tribeManager.deleteWarp(playerTribe, p, warpName);
			} else {
				p.sendMessage(ChatColor.RED + "Only chiefs and elders can set warps!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: /tribes delwarp [warp_name]");
		}
	}

}

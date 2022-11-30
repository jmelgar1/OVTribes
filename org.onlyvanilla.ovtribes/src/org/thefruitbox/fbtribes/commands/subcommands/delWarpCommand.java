package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;
import org.thefruitbox.fbtribes.managers.WarpManager;

import net.md_5.bungee.api.ChatColor;

public class delWarpCommand extends SubCommand {
	
	TribeManager tribeManager = new TribeManager();
	WarpManager warpManager = new WarpManager();

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
		return "/tribes delwarp [warp]";
	}

	@Override
	public void perform(Player p, String[] args) {
		if (args.length == 2) {
			String playerTribe = tribeManager.getPlayerTribe(p);
			if(tribeManager.CheckForElder(playerTribe, p) == true || tribeManager.CheckForChief(playerTribe, p) == true) {
				String warpName = args[1];
				warpManager.deleteWarp(playerTribe, p, warpName);
			} else {
				p.sendMessage(ChatColor.RED + "Only chiefs and elders can set warps!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}

}

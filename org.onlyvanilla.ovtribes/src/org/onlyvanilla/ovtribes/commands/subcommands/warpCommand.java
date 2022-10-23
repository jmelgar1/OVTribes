package org.onlyvanilla.ovtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.onlyvanilla.ovtribes.Main;
import org.onlyvanilla.ovtribes.commands.SubCommand;
import org.onlyvanilla.ovtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class warpCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "warp";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Go to tribe warp";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes warp";
	}

	@Override
	public void perform(Player p, String[] args) {
		if (args.length == 2) {
			String playerTribe = tribeManager.getPlayerTribe(p);
			String warpName = args[1];
			tribeManager.warpPlayer(playerTribe, p, warpName);
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: /tribes warp [warp_name]");
		}
	}
}

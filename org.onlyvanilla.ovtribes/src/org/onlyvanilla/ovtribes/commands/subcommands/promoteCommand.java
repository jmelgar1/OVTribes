package org.onlyvanilla.ovtribes.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovtribes.Main;
import org.onlyvanilla.ovtribes.commands.SubCommand;
import org.onlyvanilla.ovtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class promoteCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "promote";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Give a player elder status";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes promote";
	}

	@Override
	public void perform(Player p, String[] args) {
		if(args.length == 2) {
			Player promotedPlayer = Bukkit.getServer().getPlayer(args[1]);
			String playerTribe = tribeManager.getPlayerTribe(p);
			if(promotedPlayer != null) {
				if(tribeManager.CheckForChief(playerTribe, p) == true) {
				String promotedPlayerTribe = tribeManager.getPlayerTribe(promotedPlayer);
					if(tribeManager.getElder(playerTribe).equals("")) {
						if(playerTribe.equals(promotedPlayerTribe)) {
							if(!promotedPlayer.getName().equals(p.getName())) {
								tribeManager.setElder(playerTribe, promotedPlayer);
								tribeManager.sendMessageToMembers(playerTribe, ChatColor.GREEN + promotedPlayer.getName() + " has been promoted to elder!");
							} else {
								p.sendMessage(ChatColor.RED + "You can not promote yourself!");
							}
						} else {
							p.sendMessage(ChatColor.RED + promotedPlayer.getName() + " is not in your tribe!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "There can only be 1 other elder!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You need to be a chief to promote players!");
				}
			} else { 
				p.sendMessage(ChatColor.RED + "Player " + args[1] + " not found!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: /tribes promote [player]");
		}
	}

}

package org.thefruitbox.fbtribes.commands.subcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class infoCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "info";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "See tribe info";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes info [tribe]";
	}

	@Override
	public void perform(Player p, String[] args) {
		String playerTribe = tribeManager.getPlayerTribe(p);
		FileConfiguration tribesFile = mainClass.getTribes();
		
		if(args.length == 1) {
			if(!playerTribe.equals("none")) {
				
				ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe);
				tribeManager.getTribeInfo(tribesFile, tribeSection, playerTribe, p, false);
				
			} else {
				p.sendMessage(ChatColor.RED + "You are not in a tribe! To view other tribes use /tribes info [tribe]");
			}
		} else if (args.length == 2) {
			String otherTribe = args[1];
			
			ConfigurationSection tribeSection = tribesFile.getConfigurationSection(otherTribe.toLowerCase());
			tribeManager.getTribeInfo(tribesFile, tribeSection, otherTribe, p, true);
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
}

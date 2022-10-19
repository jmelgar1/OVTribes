package org.onlyvanilla.ovtribes.commands.subcommands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovtribes.Main;
import org.onlyvanilla.ovtribes.commands.SubCommand;
import org.onlyvanilla.ovtribes.managers.TribeManager;

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
		return "OVTribes help guide";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes info";
	}

	@Override
	public void perform(Player p, String[] args) {
		String playerTribe = tribeManager.getPlayerTribe(p);
		FileConfiguration tribesFile = mainClass.getTribes();
		
		if(args.length == 1) {
			if(!playerTribe.equals("none")) {
				
				ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe);
				getTribeInfo(tribesFile, tribeSection, playerTribe, p);
				
			} else {
				p.sendMessage(ChatColor.RED + "You are not in a tribe! To view other tribes use /tribes info [tribe]");
			}
		} else if (args.length == 2) {
			String otherTribe = args[1];
			
			ConfigurationSection tribeSection = tribesFile.getConfigurationSection(otherTribe.toLowerCase());
			getTribeInfo(tribesFile, tribeSection, otherTribe, p);
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: /tribes info [tribe]");
		}
	}
	
	private void getTribeInfo(FileConfiguration tribesFile, ConfigurationSection tribeSection, String tribe, Player p) {
		if(tribesFile.getConfigurationSection(tribe.toLowerCase()) != null) {
			String tribeName = tribeSection.getString("showname");
			int level = tribeSection.getInt("level");
			List<String> members = tribeSection.getStringList("members");
			String tag = tribeSection.getString("tag");
			int vault = tribeSection.getInt("vault");
			OfflinePlayer chief = Bukkit.getServer().getOfflinePlayer(UUID.fromString(tribeSection.getString("chief")));
			
			String elder = "";
			if(!tribeSection.getString("elder").equals("")) {
				elder = Bukkit.getServer().getOfflinePlayer(UUID.fromString(tribeSection.getString("elder"))).getName();
			}
	
			String dateCreated = tribeSection.getString("dateCreated");
			
			
			p.sendMessage(ChatColor.GRAY + "---------[ " + mainClass.tribesColor + tribeName + ChatColor.GRAY + " ]---------");
			p.sendMessage(mainClass.lightGreen + "✎Date Founded: " + mainClass.lighterGreen + dateCreated);
			p.sendMessage(mainClass.lightGreen + "✦Level: " + mainClass.lighterGreen + level);
			p.sendMessage(mainClass.lightGreen + "␠Vault: " + mainClass.lighterGreen + vault + " sponges");
			p.sendMessage(mainClass.lightGreen + "♚Chief: " + mainClass.lighterGreen + chief.getName());
			p.sendMessage(mainClass.lightGreen + "♛Elder: " + mainClass.lighterGreen + elder);
			
			StringBuffer sb = new StringBuffer();
			for(String member : members) {
				sb.append(Bukkit.getServer().getOfflinePlayer(UUID.fromString(member)).getName()).append(", ");
			}
			
			p.sendMessage(mainClass.lightGreen + "♞Members: " + mainClass.lighterGreen + "" + sb);
		} else {
			p.sendMessage(ChatColor.RED + "That tribe name does not exist!");
		}
	}
}

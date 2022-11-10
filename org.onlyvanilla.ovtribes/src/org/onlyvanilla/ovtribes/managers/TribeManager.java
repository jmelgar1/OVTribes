package org.onlyvanilla.ovtribes.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovtribes.Main;

import net.md_5.bungee.api.ChatColor;

public class TribeManager {

	//Main instance
	private Main mainClass = Main.getInstance();
	
	public String getPlayerTribe(Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerUUID = p.getUniqueId().toString();
		
		for(String tribe : tribesFile.getKeys(false)) {
			ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe);
			if(tribeSection.getStringList("members").contains(playerUUID) || tribeSection.get("chief").equals(playerUUID)) {
				return tribe;
			}
		}
		return "none";
	}
	
	public String getOfflinePlayerTribe(OfflinePlayer p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerUUID = p.getUniqueId().toString();
		
		for(String tribe : tribesFile.getKeys(false)) {
			ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe);
			if(tribeSection.getStringList("members").contains(playerUUID) || tribeSection.get("chief").equals(playerUUID)) {
				return tribe;
			}
		}
		return "none";
	}
	
	public void sendMessageToMembers(String tribe, String message) {
		 List<String> members = getTribeMembers(tribe);
		 
		 for(String member : members) {
			 UUID playerUUID = UUID.fromString(member);
			 Player p = Bukkit.getServer().getPlayer(playerUUID);
			 if(p != null) {
				 p.sendMessage(message);
			 }
		 }
	}
	
	public List<String> getTribeMembers(String tribe){
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		List<String> currentMembers = tribeSection.getStringList("members");
		return currentMembers;
	}
	
	public void setTribeMembers(String tribe, List<String> updatedMemberList) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		tribeSection.set("members", updatedMemberList);
		mainClass.saveTribesFile();
	}
	
	public String getTribeShowName(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String storageName = tribeSection.getString("showname");
		return storageName;
	}
	
	public boolean CheckForChief(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String chief = tribeSection.getString("chief");
		
		if(chief.equals(playerUUID)) {
			return true;
		}
		return false;
	}
	
	public boolean CheckForElder(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String elder = tribeSection.getString("elder");
		
		if(elder.equals(playerUUID)) {
			return true;
		}
		return false;
	}
	
	public boolean CheckSameTribe(String tribeOne, String tribeTwo) {
		if(tribeOne.equals(tribeTwo)) {
			return true;
		}
		return false;
	}
	
	public void setElder(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		tribeSection.set("elder", playerUUID);
		mainClass.saveTribesFile();
	}
	
	public void removeElder(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		tribeSection.set("elder", "");
		mainClass.saveTribesFile();
	}
	
	public String getElder(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String elder = tribeSection.getString("elder");
		return elder;
	}
	
	public void setChief(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		tribeSection.set("chief", playerUUID);
		mainClass.saveTribesFile();
	}
	
	public String getChief(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String chief = tribeSection.getString("chief");
		return chief;
	}
	
	public int getVault(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int vault = tribeSection.getInt("vault");
		return vault;
	}
	
	public void addToVault(String tribe, int amount, Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int vault = getVault(tribe);
		int newAmount = vault + amount;
		ChatColor transactionColor0 = net.md_5.bungee.api.ChatColor.of("#E7761E");
		ChatColor transactionColor1 = net.md_5.bungee.api.ChatColor.of("#72C06C");
		ChatColor transactionColor2 = net.md_5.bungee.api.ChatColor.of("#767166");
		ChatColor transactionColor3 = net.md_5.bungee.api.ChatColor.of("#4BD613");
		String transactionMessage = transactionColor0 + "Tribe Vault: " + transactionColor1 + vault + transactionColor2 + " -> " + transactionColor3 + newAmount;
		sendMessageToMembers(tribe, ChatColor.GREEN + "(" + ChatColor.DARK_GREEN + p.getName() + ChatColor.GREEN + ") " + transactionMessage);
		tribeSection.set("vault", newAmount);
		mainClass.saveTribesFile();
	}
	
	public void removeFromVault(String tribe, int amount, Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int vault = getVault(tribe);
		int newAmount = vault - amount;
		ChatColor transactionColor0 = net.md_5.bungee.api.ChatColor.of("#E7761E");
		ChatColor transactionColor1 = net.md_5.bungee.api.ChatColor.of("#D69213");
		ChatColor transactionColor2 = net.md_5.bungee.api.ChatColor.of("#767166");
		ChatColor transactionColor3 = net.md_5.bungee.api.ChatColor.of("#C0A66C");
		String transactionMessage = transactionColor0 + "Tribe Vault: " + transactionColor1 + vault + transactionColor2 + " -> " + transactionColor3 + newAmount;
		sendMessageToMembers(tribe, ChatColor.GOLD + "(" + ChatColor.YELLOW + p.getName() + ChatColor.GOLD + ") " + transactionMessage);
		tribeSection.set("vault", newAmount);
		mainClass.saveTribesFile();
	}
	
	public void setLevel(String tribe, int vault) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		
		//ChatColor levelUpColor = net.md_5.bungee.api.ChatColor.of("#bd9b3e");
		//use later
		
		if(vault >= 4400) {
			tribeSection.set("level", 10);
			tribeSection.set("maxWarps", 4);
			tribeSection.set("maxPlayers", 12);
			tribeSection.set("requiredSponges", -1);
			tribeSection.set("minimumAmount", 4400);
		} else if(vault >= 2200 && vault < 4400) {
			tribeSection.set("level", 9);
			tribeSection.set("maxWarps", 3);
			tribeSection.set("maxPlayers", 11);
			tribeSection.set("requiredSponges", 4400);
			tribeSection.set("minimumAmount", 2200);
		} else if(vault >= 1900 && vault < 2200) {
			tribeSection.set("level", 8);
			tribeSection.set("maxWarps", 3);
			tribeSection.set("maxPlayers", 10);
			tribeSection.set("requiredSponges", 2200);
			tribeSection.set("minimumAmount", 1900);
		} else if(vault >= 1600 && vault < 1900) {
			tribeSection.set("level", 7);
			tribeSection.set("maxWarps", 3);
			tribeSection.set("maxPlayers", 9);
			tribeSection.set("requiredSponges", 1900);
			tribeSection.set("minimumAmount", 1600);
		} else if(vault >= 800 && vault < 1600) {
			tribeSection.set("level", 6);
			tribeSection.set("maxWarps", 2);
			tribeSection.set("maxPlayers", 8);
			tribeSection.set("requiredSponges", 1600);
			tribeSection.set("minimumAmount", 800);
		} else if(vault >= 600 && vault < 800) {
			tribeSection.set("level", 5);
			tribeSection.set("maxWarps", 2);
			tribeSection.set("maxPlayers", 7);
			tribeSection.set("requiredSponges", 800);
			tribeSection.set("minimumAmount", 600);
		} else if(vault >= 400 && vault < 600) {
			tribeSection.set("level", 4);
			tribeSection.set("maxWarps", 2);
			tribeSection.set("maxPlayers", 6);
			tribeSection.set("requiredSponges", 600);
			tribeSection.set("minimumAmount", 400);
		} else if(vault >= 200 && vault < 400) {
			tribeSection.set("level", 3);
			tribeSection.set("maxPlayers", 5);
			tribeSection.set("requiredSponges", 400);
			tribeSection.set("minimumAmount", 200);
		} else if(vault >= 100 && vault < 200) {
			tribeSection.set("level", 2);
			tribeSection.set("maxPlayers", 4);
			tribeSection.set("requiredSponges", 200);
			tribeSection.set("minimumAmount", 100);
		} else if(vault < 100) {
			tribeSection.set("level", 1);
			tribeSection.set("maxPlayers", 3);
			tribeSection.set("requiredSponges", 100);
			tribeSection.set("minimumAmount", 0);
		}
	}
	
	public int getMinimumVaultAmount(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int minAmount = tribeSection.getInt("minimumAmount");
		return minAmount;
	}

	public int getMaxPlayers(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int maxPlayers = tribeSection.getInt("maxPlayers");
		return maxPlayers;
	}
	
	public int getLevel(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int level = tribeSection.getInt("level");
		return level;
	}
	
	public void getTribeInfo(FileConfiguration tribesFile, ConfigurationSection tribeSection, String tribe, Player p, boolean bool) {
		WarpManager warpManager = new WarpManager();
		if(tribesFile.getConfigurationSection(tribe.toLowerCase()) != null) {
			String tribeName = tribeSection.getString("showname");
			int level = tribeSection.getInt("level");
			int vault = tribeSection.getInt("vault");
			int requiredSponges = tribeSection.getInt("requiredSponges");
			OfflinePlayer chief = Bukkit.getServer().getOfflinePlayer(UUID.fromString(tribeSection.getString("chief")));
			
			String elder = "";
			if(!tribeSection.getString("elder").equals("")) {
				elder = Bukkit.getServer().getOfflinePlayer(UUID.fromString(tribeSection.getString("elder"))).getName();
			}
	
			String dateCreated = tribeSection.getString("dateCreated");
			
			
			p.sendMessage(ChatColor.GRAY + "---------[ " + mainClass.tribesColor + tribeName + ChatColor.GRAY + " ]---------");
			p.sendMessage(mainClass.lightGreen + "✎Date Founded: " + mainClass.lighterGreen + dateCreated);
			p.sendMessage(mainClass.lightGreen + "✦Level: " + mainClass.lighterGreen + level);
			
			if(requiredSponges == -1) {
				p.sendMessage(mainClass.lightGreen + "␠Vault: " + mainClass.lighterGreen + vault);
			} else {
				p.sendMessage(mainClass.lightGreen + "␠Vault: " + mainClass.lighterGreen + vault + " / " + requiredSponges + " sponges");
			}
			p.sendMessage(mainClass.lightGreen + "♚Chief: " + mainClass.lighterGreen + chief.getName());
			p.sendMessage(mainClass.lightGreen + "♛Elder: " + mainClass.lighterGreen + elder);
			
			List<String> membersUUID = tribeSection.getStringList("members");
			List<String> membersIGN = new ArrayList<String>();
			for(String member : membersUUID) {
				membersIGN.add(Bukkit.getServer().getOfflinePlayer(UUID.fromString(member)).getName());
			}
			
			String members = membersIGN.stream().collect(Collectors.joining(", "));
			
			p.sendMessage(mainClass.lightGreen + "♞Members (" + membersIGN.size() + "/" + getMaxPlayers(tribe) + "): " + mainClass.lighterGreen + "" + members);
			
			if(bool == false) {
				p.sendMessage(mainClass.lightGreen + "⥮Warps: " + mainClass.lighterGreen + warpManager.getWarpString(tribeName, p));
			}
		} else {
			p.sendMessage(ChatColor.RED + "That tribe name does not exist or the selected player is not in a tribe!");
		}
	}
}

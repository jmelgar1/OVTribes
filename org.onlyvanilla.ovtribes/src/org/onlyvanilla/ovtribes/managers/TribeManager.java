package org.onlyvanilla.ovtribes.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
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
	
	public void setLevel(String tribe, int vault) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		
		//ChatColor levelUpColor = net.md_5.bungee.api.ChatColor.of("#bd9b3e");
		//use later
		
		if(vault >= 8192) {
			tribeSection.set("level", 10);
			tribeSection.set("maxWarps", 4);
			tribeSection.set("maxPlayers", 12);
		} else if(vault >= 4096 && vault < 8192) {
			tribeSection.set("level", 9);
			tribeSection.set("maxPlayers", 11);
		} else if(vault >= 2048 && vault < 4096) {
			tribeSection.set("level", 8);
			tribeSection.set("maxPlayers", 10);
		} else if(vault >= 1024 && vault < 2048) {
			tribeSection.set("level", 7);
			tribeSection.set("maxWarps", 3);
			tribeSection.set("maxPlayers", 9);
		} else if(vault >= 512 && vault < 1024) {
			tribeSection.set("level", 6);
			tribeSection.set("maxPlayers", 8);
		} else if(vault >= 256 && vault < 512) {
			tribeSection.set("level", 5);
			tribeSection.set("maxPlayers", 7);
		} else if(vault >= 128 && vault < 256) {
			tribeSection.set("level", 4);
			tribeSection.set("maxWarps", 2);
			tribeSection.set("maxPlayers", 6);
		} else if(vault >= 64 && vault < 128) {
			tribeSection.set("level", 3);
			tribeSection.set("maxPlayers", 5);
		} else if(vault >= 32 && vault < 64) {
			tribeSection.set("level", 2);
			tribeSection.set("maxPlayers", 4);
		} else if(vault < 32) {
			tribeSection.set("level", 1);
			tribeSection.set("maxPlayers", 3);
		}
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
	
	public int getNumberOfWarps(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int currentWarps = tribeSection.getInt("currentWarps");
		return currentWarps;
	}
	
	public void setNumberOfWarps(String tribe, int num) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		tribeSection.set("currentWarps", num);
	}
	
	public int getMaxWarps(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int maxWarps = tribeSection.getInt("maxWarps");
		return maxWarps;
	}
	
	public void setWarp(String tribe, Player p, String warpName) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerTribe = getPlayerTribe(p);
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe.toLowerCase());
		ConfigurationSection warpSection = tribeSection.getConfigurationSection("warps");
		
		Location loc = p.getLocation();
		ConfigurationSection warp = warpSection.createSection(warpName);
		warp.set("world", loc.getWorld().getName() + "");
		warp.set("x", loc.getX() + "");
		warp.set("y", loc.getY() + "");
		warp.set("z", loc.getZ() + "");
		warp.set("yaw", loc.getYaw() + "");
		warp.set("pitch", loc.getPitch() + "");
		
		sendMessageToMembers(playerTribe, ChatColor.GREEN + "New tribe warp " + warpName + " has been set!");
		int numOfWarps = getNumberOfWarps(playerTribe)+1;
		setNumberOfWarps(playerTribe, numOfWarps);
        
		mainClass.saveTribesFile();
	}
	
	public void deleteWarp(String tribe, Player p, String warpName) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerTribe = getPlayerTribe(p);
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe.toLowerCase());
		ConfigurationSection warpSection = tribeSection.getConfigurationSection("warps");
		
		if(getWarpList(playerTribe, p).contains(warpName)) {
			warpSection.set(warpName, null);
			sendMessageToMembers(playerTribe, ChatColor.RED + "Tribe warp " + warpName + " has been deleted!");
			int numOfWarps = getNumberOfWarps(playerTribe)-1;
			setNumberOfWarps(playerTribe, numOfWarps);
	        
			mainClass.saveTribesFile();
		} else {
			p.sendMessage(ChatColor.RED + warpName + " does not exist!");
		}
	}
	
	public String getWarpString(String tribe, Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerTribe = getPlayerTribe(p);
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe.toLowerCase());
		ConfigurationSection warpSection = tribeSection.getConfigurationSection("warps");
		List<String> warps = new ArrayList<String>();
		for(String warp : warpSection.getKeys(false)) {
			warps.add(warp);
		}
		
		String warpString = warps.stream().collect(Collectors.joining(", "));
		
		return warpString;
	}
	
	public List<String> getWarpList(String tribe, Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerTribe = getPlayerTribe(p);
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe.toLowerCase());
		ConfigurationSection warpSection = tribeSection.getConfigurationSection("warps");
		List<String> warps = new ArrayList<String>();
		for(String warp : warpSection.getKeys(false)) {
			warps.add(warp);
		}
		return warps;
	}
	
	public void warpPlayer(String tribe, Player p, String warpName) {
		p.sendMessage(ChatColor.GREEN + "Warping in 5 seconds...");
		
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerTribe = getPlayerTribe(p);
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe.toLowerCase());
		ConfigurationSection warpSection = tribeSection.getConfigurationSection("warps");
		ConfigurationSection warp = warpSection.getConfigurationSection(warpName);
		
		if(warp != null) {
			
			new BukkitRunnable(){
				
	            @Override
	            public void run() {
	            	//retrieve warp location from config
	    			double x = Double.parseDouble(warp.getString("x"));
	    			double y = Double.parseDouble(warp.getString("y"));
	    			double z = Double.parseDouble(warp.getString("z"));
	    			int yaw = (int)Double.parseDouble(warp.getString("yaw"));
	    			int pitch = (int)Double.parseDouble(warp.getString("pitch"));
	    			String world = warp.getString("world");
	    			Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	    			
	    			//teleport player
	    			p.teleport(loc);
	    			p.sendMessage(ChatColor.GREEN + "Warp successful!");
	            }
	           
	        }.runTaskLater(mainClass, 100);
		} else {
			p.sendMessage(ChatColor.RED + "Warp " + warpName + " does not exist!");
		}
	}
}

package org.onlyvanilla.ovtribes.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovtribes.Main;

import net.md_5.bungee.api.ChatColor;

public class WarpManager {
	
	//Main instance
	private static WarpManager instance;
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();
	
	public List<Player> inWarp = new ArrayList<Player>();
	
	public static WarpManager getInstance() {
		return instance;
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
		String playerTribe = tribeManager.getPlayerTribe(p);
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
		
		tribeManager.sendMessageToMembers(playerTribe, ChatColor.GREEN + "New tribe warp " + warpName + " has been set!");
		int numOfWarps = getNumberOfWarps(playerTribe)+1;
		setNumberOfWarps(playerTribe, numOfWarps);
        
		mainClass.saveTribesFile();
	}
	
	public void deleteWarp(String tribe, Player p, String warpName) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerTribe = tribeManager.getPlayerTribe(p);
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe.toLowerCase());
		ConfigurationSection warpSection = tribeSection.getConfigurationSection("warps");
		
		if(getWarpList(playerTribe, p).contains(warpName)) {
			warpSection.set(warpName, null);
			tribeManager.sendMessageToMembers(playerTribe, ChatColor.RED + "Tribe warp " + warpName + " has been deleted!");
			int numOfWarps = getNumberOfWarps(playerTribe)-1;
			setNumberOfWarps(playerTribe, numOfWarps);
	        
			mainClass.saveTribesFile();
		} else {
			p.sendMessage(ChatColor.RED + warpName + " does not exist!");
		}
	}
	
	public String getWarpString(String tribe, Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerTribe = tribeManager.getPlayerTribe(p);
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
		String playerTribe = tribeManager.getPlayerTribe(p);
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
		inWarp.add(p);
		
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerTribe = tribeManager.getPlayerTribe(p);
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
	    			
	    			int priceToWarp = mainClass.getPrices().getInt("gotowarp");
	    			tribeManager.removeFromVault(playerTribe, priceToWarp, p);
	    			
	    			p.sendMessage(ChatColor.GREEN + "Warp successful!");
	    			inWarp.remove(p);
	            }
	           
	        }.runTaskLater(mainClass, 100);
		} else {
			p.sendMessage(ChatColor.RED + "Warp " + warpName + " does not exist!");
		}
	}
}

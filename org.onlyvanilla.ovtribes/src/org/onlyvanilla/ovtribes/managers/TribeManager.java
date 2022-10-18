package org.onlyvanilla.ovtribes.managers;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovtribes.Main;

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
	}
	
	public String getTribeShowName(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String storageName = tribeSection.getString("showname");
		return storageName;
	}
}

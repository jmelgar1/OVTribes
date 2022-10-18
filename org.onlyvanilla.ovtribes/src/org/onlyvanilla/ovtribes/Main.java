package org.onlyvanilla.ovtribes;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.onlyvanilla.ovtribes.Main;
import org.onlyvanilla.ovtribes.commands.CommandManager;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	//Main instance
	private static Main instance;
	
	//player data file
	private File tribesFile;
	private FileConfiguration tribes;
	
	public ChatColor tribesColor = net.md_5.bungee.api.ChatColor.of("#db9e58");
	public ChatColor lightGreen = net.md_5.bungee.api.ChatColor.of("#95bf56");
	public ChatColor lighterGreen = net.md_5.bungee.api.ChatColor.of("#b4ba82");
	
	@Override
	public void onEnable() {
		System.out.println("OVTribes Enabled!");
		
		instance = this;
		
		getCommand("tribes").setExecutor(new CommandManager());
		
		createTribesFile();

	}
	
	public void onDisable() {
		System.out.println("OVTribes Disabled!");
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	//PLAYER DATA FILE
	public void saveTribesFile() {
		try {
			tribes.save(tribesFile);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("Couldn't save tribes.yml");
		}
	}
	
	public void reloadTribesFile() {
		tribes = YamlConfiguration.loadConfiguration(tribesFile);
		
	}
	
	public FileConfiguration getTribes() {
		return this.tribes;
	}
	
	private void createTribesFile() {
		tribesFile = new File(getDataFolder(), "tribes.yml");
		if(!tribesFile.exists()) {
			tribesFile.getParentFile().mkdirs();
			saveResource("tribes.yml", false);
			System.out.println("(!) tribes.yml created");
		}
		
		tribes = new YamlConfiguration();
		try {
			tribes.load(tribesFile);
			System.out.println("(!) tribes.yml loaded");
		} catch(IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		} 
	}
}
			
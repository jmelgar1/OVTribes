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
import org.onlyvanilla.ovtribes.commands.claimspongesCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.admin.addspongesCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.admin.setspongesCommand;
import org.onlyvanilla.ovtribes.events.BreakAncientDebri;
import org.onlyvanilla.ovtribes.events.BreakDiamondOrEmeraldOre;
import org.onlyvanilla.ovtribes.events.CatchTreasure;
import org.onlyvanilla.ovtribes.events.KillEvent;
import org.onlyvanilla.ovtribes.runnables.CheckForUnclaimed;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	//Main instance
	private static Main instance;
	
	//player data file
	private File tribesFile;
	private FileConfiguration tribes;
	
	//unclaimed rewards file
	private File rewardsFile;
	private FileConfiguration rewards;
	
	//tribe prices file
	private File pricesFile;
	private FileConfiguration prices;
	
	public ChatColor tribesColor = net.md_5.bungee.api.ChatColor.of("#db9e58");
	public ChatColor lightGreen = net.md_5.bungee.api.ChatColor.of("#95bf56");
	public ChatColor lighterGreen = net.md_5.bungee.api.ChatColor.of("#b4ba82");
	public ChatColor spongeColor = net.md_5.bungee.api.ChatColor.of("#dfff00");
	
	@Override
	public void onEnable() {
		System.out.println("OVTribes Enabled!");
		
		instance = this;
		
		getCommand("tribes").setExecutor(new CommandManager());
		getCommand("claimsponges").setExecutor(new claimspongesCommand());
		getCommand("setsponges").setExecutor(new setspongesCommand());
		getCommand("addsponges").setExecutor(new addspongesCommand());
		
		createTribesFile();
		createRewardsFile();
		createPricesFile();
		
		getServer().getPluginManager().registerEvents(new BreakDiamondOrEmeraldOre(), this);
		getServer().getPluginManager().registerEvents(new BreakAncientDebri(), this);
		getServer().getPluginManager().registerEvents(new CatchTreasure(), this);
		getServer().getPluginManager().registerEvents(new KillEvent(), this);
		
		CheckForUnclaimed checkForUnclaimed = new CheckForUnclaimed();
		checkForUnclaimed.runTaskTimer(this, 0L, 12000);
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
	
	//REWARDS FILE
	public void saveRewardsFile() {
		try {
			rewards.save(rewardsFile);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("Couldn't save unclaimedRewards.yml");
		}
	}
	
	public FileConfiguration getRewards() {
		return this.rewards;
	}
	
	private void createRewardsFile() {
		rewardsFile = new File(getDataFolder(), "unclaimedRewards.yml");
		if(!rewardsFile.exists()) {
			rewardsFile.getParentFile().mkdirs();
			saveResource("unclaimedRewards.yml", false);
			System.out.println("(!) unclaimedRewards.yml created");
		}
		
		rewards = new YamlConfiguration();
		try {
			rewards.load(rewardsFile);
			System.out.println("(!) unclaimedRewards.yml loaded");
		} catch(IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		} 
	}
	
	//prices file
	public void savePricesFile() {
		try {
			prices.save(pricesFile);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("Couldn't save prices.yml");
		}
	}
	
	public FileConfiguration getPrices() {
		return this.prices;
	}
	
	private void createPricesFile() {
		pricesFile = new File(getDataFolder(), "prices.yml");
		if(!pricesFile.exists()) {
			pricesFile.getParentFile().mkdirs();
			saveResource("prices.yml", false);
			System.out.println("(!) prices.yml created");
		}
		
		prices = new YamlConfiguration();
		try {
			prices.load(pricesFile);
			System.out.println("(!) prices.yml loaded");
		} catch(IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		} 
	}
}
			
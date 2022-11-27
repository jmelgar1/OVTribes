package org.onlyvanilla.ovtribes.tribalgames.runnables.ctf1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovtribes.Main;

import com.sk89q.worldguard.*;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import net.md_5.bungee.api.ChatColor;

import com.sk89q.worldedit.bukkit.BukkitAdapter;

public class CTF1Countdown extends BukkitRunnable {
    
	//Main instance
	private Main mainClass = Main.getInstance();
	
	//worldguard
	WorldGuard wg = WorldGuard.getInstance();
	RegionContainer container = wg.getPlatform().getRegionContainer();
	World world = Bukkit.getServer().getWorld("world");
	RegionManager regions = container.get(BukkitAdapter.adapt(world));
	ProtectedRegion region = regions.getRegion("ctf1");
	
	List<Player> playersInArena = new ArrayList<Player>();
	
	//DEFAULT 3600
	int seconds = 10;
	
	BossBar bar = Bukkit.createBossBar("countdown", BarColor.YELLOW, BarStyle.SEGMENTED_10);
	
	Map<Player, BossBar> playerBossbar = new HashMap<Player, BossBar>();
	
    DecimalFormat dFormat = new DecimalFormat("00");

	public void run() {
		
		double minutes = seconds/60.0;
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(region.contains(p.getLocation().getBlockX(), 
							   p.getLocation().getBlockY(),
							   p.getLocation().getBlockZ())) {
				addPlayerToArena(p, playersInArena);
			} else {
				removePlayerFromArena(p, playersInArena);
			}	
			
			//only send notification on certain time intervals
			if(minutes == 60 || minutes == 45 || minutes == 30 || minutes == 20 || minutes == 10 ||
					minutes == 5 || minutes == 3 || minutes == 1) {
						
				sendReminder(p, minutes);
			}
		}
		
		if(seconds <= 5) {
			for(Player p : playersInArena) {
				if(p != null) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0F);
				}
			}
		}
		
		if((seconds -= 1) == 0) {
			if(playersInArena.size() > 4) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 0F);
					Bukkit.broadcastMessage(mainClass.tgPrefix + ChatColor.RED + "Not enough players are in the CTF arena. This event will be skipped!");
				}
				
				System.out.println("not enough players");
				bar.removeAll();
				cancel();
				
			} else {
				for(Player p : Bukkit.getOnlinePlayers()) {
					Bukkit.broadcastMessage(mainClass.tgPrefix + ChatColor.GREEN + "CTF has begun at site 1!");
					p.getWorld().playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_2, 1F, 0F);
				}

				//start event here somehow
				bar.removeAll();
				cancel();
			}
			
			} else {
				bar.setProgress(seconds / 3600D);
				String minutesTimer = String.valueOf(seconds/60);
				String secondsTimer = dFormat.format(seconds % 60);
				bar.setTitle("CTF starts in " + minutesTimer + ":" + secondsTimer);
			}
	}
	
	public void addPlayer(Player p) {
		bar.addPlayer(p);
	}
	
	@EventHandler
	void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		playerBossbar.put(p, Bukkit.createBossBar("Event will begin shortly", BarColor.PINK, BarStyle.SEGMENTED_10));
	}
	
	@EventHandler
	void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		BossBar bar = playerBossbar.get(p);
		bar.addPlayer(p);
		
	}
	
	void sendReminder(Player p, double minutes) {
		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 0);
		
		String time;
		
		int newMinutes = (int)minutes;
		
		if(minutes == 60) {
			time = "1 Hour";
		} else if(minutes == 1){
			time = newMinutes + " Minute";
		} else {
			time = newMinutes + " Minutes";
		}
		
		p.sendMessage(ChatColor.GRAY + "-----" +
				mainClass.tribalGames + ChatColor.BOLD.toString() + "TRIBAL GAMES" +
				ChatColor.GRAY + "-----");
		p.sendMessage(ChatColor.GRAY + "Game: ");
		p.sendMessage(ChatColor.YELLOW + "Capture The Flag - Site 1");
		p.sendMessage("");
		p.sendMessage(ChatColor.GRAY + "Location: ");
		p.sendMessage(ChatColor.AQUA + "(Overworld) " + ChatColor.BLUE + "x: 34833, y: 20964");
		p.sendMessage("");
		p.sendMessage(ChatColor.GRAY + "Time Until Event: ");
		p.sendMessage(ChatColor.GREEN + time);
		p.sendMessage("");
		p.sendMessage(ChatColor.GOLD.toString() + ChatColor.ITALIC + "How to participate:");
		p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Click here");
	}
	
	void addPlayerToArena(Player p, List<Player> list) {
		if(!list.contains(p)) {
			list.add(p);
			bar.addPlayer(p);
		}
	}
	
	void removePlayerFromArena(Player p, List<Player> list) {
		if(list.contains(p)) {
			list.remove(p);
			bar.removePlayer(p);
		}
	}
}

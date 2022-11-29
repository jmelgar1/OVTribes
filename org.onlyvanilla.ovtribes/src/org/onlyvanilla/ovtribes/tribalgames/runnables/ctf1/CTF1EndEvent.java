package org.onlyvanilla.ovtribes.tribalgames.runnables.ctf1;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovtribes.Main;

import com.sk89q.worldguard.domains.DefaultDomain;

import net.md_5.bungee.api.ChatColor;

public class CTF1EndEvent extends BukkitRunnable {
	
	CTF1Countdown ctf1 = new CTF1Countdown();
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	FileConfiguration ctf = mainClass.getCTF();
	List<String> participants = ctf.getStringList("participants");

	@Override
	public void run() {
		DefaultDomain regionMembers = ctf1.region.getMembers();
		
		for(String player : participants) {
			regionMembers.removePlayer(player);
			ctf1.region.setMembers(regionMembers);
		}
		
		ctf.set("participants", null);
		ctf.set("playersInArena", null);
		ctf.set("tribes", null);
		
		Bukkit.broadcastMessage(mainClass.tgPrefix + ChatColor.YELLOW + "CTF has ended!");
	}
}

package org.thefruitbox.fbtribes.tribalgames.runnables.ctf1;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.thefruitbox.fbtribes.tribalgames.managers.CTF1Manager;

import com.sk89q.worldguard.domains.DefaultDomain;

import net.md_5.bungee.api.ChatColor;

public class CTF1EndEvent extends BukkitRunnable implements CTF1Manager {
	
	CTF1Countdown ctf1 = new CTF1Countdown();

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

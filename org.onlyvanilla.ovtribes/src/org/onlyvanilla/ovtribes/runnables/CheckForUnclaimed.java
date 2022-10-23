package org.onlyvanilla.ovtribes.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import org.onlyvanilla.ovtribes.Main;
import org.onlyvanilla.ovtribes.managers.SpongeManager;

public class CheckForUnclaimed extends BukkitRunnable {
    
	//Main instance
	private Main mainClass = Main.getInstance();
	
	SpongeManager spongeManager = new SpongeManager();

	@Override
	public void run() {
		
		System.out.println("Checking for unclaimed");
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(spongeManager.checkForUnclaimed(p) != 0) {
				int amount = spongeManager.checkForUnclaimed(p);
				p.sendMessage(mainClass.spongeColor + "You have " + amount + " unclaimed sponges! /claimsponges");
			}
		}
	}
}

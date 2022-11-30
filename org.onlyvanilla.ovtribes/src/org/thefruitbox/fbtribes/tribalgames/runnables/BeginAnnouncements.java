package org.thefruitbox.fbtribes.tribalgames.runnables;

import org.bukkit.scheduler.BukkitRunnable;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.tribalgames.runnables.ctf1.CTF1Countdown;

public class BeginAnnouncements extends BukkitRunnable {
	
	private Main mainClass = Main.getInstance();

	@Override
	public void run() {
		CTF1Countdown ctf1countdown = new CTF1Countdown();
		ctf1countdown.runTaskTimer(mainClass, 0L, 20);
	}

}

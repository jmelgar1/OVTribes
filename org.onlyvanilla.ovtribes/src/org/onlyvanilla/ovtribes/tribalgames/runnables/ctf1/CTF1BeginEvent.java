package org.onlyvanilla.ovtribes.tribalgames.runnables.ctf1;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovtribes.Main;

import com.sk89q.worldedit.math.BlockVector3;

public class CTF1BeginEvent extends BukkitRunnable {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	CTF1Countdown ctf1 = new CTF1Countdown();

	@Override
	public void run() {
		FileConfiguration ctf = mainClass.getCTF();
		List<String> participants = ctf.getStringList("participants");
		List<String> tribes = ctf.getStringList("tribes");
		
		BlockVector3 point1 = ctf1.region.getMinimumPoint();
		BlockVector3 point2 = ctf1.region.getMaximumPoint();
		
		int point1X = point1.getBlockX();
		int point1Z = point1.getBlockZ();
		
		int point2X = point2.getBlockX();
		int point2Z = point2.getBlockZ();
		
		int midpointX = (int)(point1X + point2X)/2;
		int midpointY = (int)(point1Z + point2Z)/2;
		
		
		//get midpoint to teleport players who are not in event around arena
		System.out.println(point1);
		System.out.println(point2);
		System.out.println(midpointX + "  " + midpointY);
	}
}

package org.onlyvanilla.ovtribes.tribalgames.runnables.ctf1;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovtribes.Main;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.md_5.bungee.api.ChatColor;

public class CTF1BeginEvent extends BukkitRunnable {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	CTF1Countdown ctf1 = new CTF1Countdown();

	@Override
	public void run() {
		FileConfiguration ctf = mainClass.getCTF();
		List<String> participants = ctf.getStringList("participants");
		List<String> tribes = ctf.getStringList("tribes");
		
		@SuppressWarnings("unchecked")
		List<Player> playersInArena = (List<Player>) ctf.getList("playersInArena");
		
		ProtectedRegion region = ctf1.regions.getRegion("ctf1spec");
		
		BlockVector3 minPoint = region.getMinimumPoint();
		BlockVector3 maxPoint = region.getMaximumPoint();
		
		World world = Bukkit.getWorld("world");
		
		System.out.println(playersInArena);
		System.out.println(participants);
	
		for(Player p : playersInArena) {
			if(!participants.contains(p.getName())) {
				
				//find a space to teleport players who are not chosen to participate outside ctf arena
				BlockVector3 position;
				do {
					
					Random rand = new Random();
					int randX = rand.nextInt(maxPoint.getBlockX() - minPoint.getBlockX()) + minPoint.getBlockX();
					int randZ = rand.nextInt(maxPoint.getBlockZ() - minPoint.getBlockZ()) + minPoint.getBlockZ();
					position = BlockVector3.at(randX, (world.getHighestBlockYAt(randX, randZ))+1, randZ);
					
				} while(ctf1.regions.getApplicableRegions(position).getRegions().contains(region) &&
						ctf1.regions.getApplicableRegions(position).getRegions().contains(ctf1.region));
				
				p.teleport(BukkitAdapter.adapt(world, position));
				p.sendMessage(mainClass.tgPrefix + ChatColor.RED + "You have not been selected to participate in CTF!");
			} else {
				
				//add players who are selected as members to region 
				DefaultDomain regionMembers = ctf1.region.getMembers();
				regionMembers.addPlayer(p.getName());
				ctf1.region.setMembers(regionMembers);
			}
		}		
		
		ctf1.region.setFlag(Flags.ENTRY, StateFlag.State.DENY);
		ctf1.region.setFlag(Flags.ENTRY.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);

		//find midpoint (could be useful)
		int point1X = minPoint.getBlockX();
		int point1Z = minPoint.getBlockZ();
		
		int point2X = maxPoint.getBlockX();
		int point2Z = maxPoint.getBlockZ();
		
		int midpointX = (int)(point1X + point2X)/2;
		int midpointY = (int)(point1Z + point2Z)/2;
		
		CTF1EndEvent ctf1endevent = new CTF1EndEvent();
		ctf1endevent.runTaskLater(mainClass, 36000);
	}
}

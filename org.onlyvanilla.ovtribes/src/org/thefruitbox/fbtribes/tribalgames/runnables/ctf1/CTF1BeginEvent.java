package org.thefruitbox.fbtribes.tribalgames.runnables.ctf1;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.tribalgames.managers.CTF1Manager;
import org.thefruitbox.fbtribes.tribalgames.runnables.CountdownTimerLong;
import org.thefruitbox.fbtribes.tribalgames.runnables.UpdateScoreboard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.md_5.bungee.api.ChatColor;

public class CTF1BeginEvent  extends BukkitRunnable implements CTF1Manager {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	CTF1Countdown ctf1 = new CTF1Countdown();

	@Override
	public void run() {	
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
		
		//create game scoreboard
		createScoreboard();
		
		//don't allow participants to exit
		ctf1.region.setFlag(Flags.EXIT, StateFlag.State.DENY);
		
		//don't allow non-participants to enter
		ctf1.region.setFlag(Flags.ENTRY, StateFlag.State.DENY);
		ctf1.region.setFlag(Flags.ENTRY.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
		
		//add something to push players back

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
	
	void createScoreboard() {
		//int redKills = mainClass.getCTF().getConfigurationSection("teams").getConfigurationSection("red").getInt("kills");
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		String title = mainClass.tgPrefix + ChatColor.YELLOW.toString() + ChatColor.BOLD + "CTF";
		Objective obj = board.registerNewObjective("FBCTF", "dummy", title);
		
		//empty space
		obj.getScore(ChatColor.RESET.toString()).setScore(11);
		
		//red team section
		obj.getScore(ChatColor.RED.toString() + ChatColor.BOLD + "RED").setScore(10);
		obj.getScore(ChatColor.GRAY + "Captures: " + ChatColor.YELLOW + 0 + ChatColor.GOLD + "/" + ChatColor.YELLOW + 5 + ChatColor.RESET.toString()).setScore(9);
		obj.getScore(ChatColor.GRAY + "Kills: " + ChatColor.YELLOW + 0 + ChatColor.RESET.toString()).setScore(8);
		
		//empty space
		obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(7);
		
		//blue team section
		obj.getScore(ChatColor.BLUE.toString() + ChatColor.BOLD + "BLUE").setScore(6);
		obj.getScore(ChatColor.GRAY + "Captures: " + ChatColor.YELLOW + 0 + ChatColor.GOLD + "/" + ChatColor.YELLOW + 5).setScore(5);
		obj.getScore(ChatColor.GRAY + "Kills: " + ChatColor.YELLOW + 0).setScore(4);
		
		//empty space
		obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(3);
		
		//time left counter
		obj.getScore(ChatColor.GRAY + "Time Left: " + ChatColor.GREEN + ctf.getString("current-countdown")).setScore(2);
		
		//empty space
		obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(1);
		
		//server ip
		obj.getScore(ChatColor.GREEN + "play.thefruitbox.net").setScore(0);
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		for(String s : participants) {
			Player p = Bukkit.getPlayer(s);
			p.setScoreboard(board);
		}
		
		UpdateScoreboard updateScoreboard = new UpdateScoreboard();
		updateScoreboard.setCountdown();
		updateScoreboard.run();
	}
}

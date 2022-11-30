package org.thefruitbox.fbtribes.tribalgames.runnables;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.thefruitbox.fbtribes.tribalgames.managers.CTF1Manager;

import net.md_5.bungee.api.ChatColor;

public class UpdateScoreboard extends BukkitRunnable implements CTF1Manager {

	@Override
	public void run() {
		ConfigurationSection redTeam = ctf.getConfigurationSection("teams").getConfigurationSection("red") ;
		int redCaptures = redTeam.getInt("captures");
		int redKills = redTeam.getInt("kills");
		
		ConfigurationSection blueTeam = ctf.getConfigurationSection("teams").getConfigurationSection("blue") ;
		int blueCaptures = blueTeam.getInt("captures");
		int blueKills = blueTeam.getInt("kills");
		
		for(String s : participants) {
			//create new scoreboard
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();	
			String title = mainClass.tgPrefix + ChatColor.YELLOW.toString() + ChatColor.BOLD + "CTF";
			Objective obj = board.registerNewObjective("FBCTF", "dummy", title);
			
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			Player p = Bukkit.getPlayer(s);
			//empty space
			obj.getScore(ChatColor.RESET.toString()).setScore(11);
		
			if(redCaptures > blueCaptures) {
				redTeamLead(obj, redCaptures, redKills, blueCaptures, blueKills);
			} else if (blueCaptures > redCaptures) {
				blueTeamLead(obj, redCaptures, redKills, blueCaptures, blueKills);
			} else if (redCaptures == blueCaptures && redKills > blueKills){
				redTeamLead(obj, redCaptures, redKills, blueCaptures, blueKills);
			} else if (redCaptures == blueCaptures && redKills < blueKills) {
				blueTeamLead(obj, redCaptures, redKills, blueCaptures, blueKills);
			} else if (redCaptures == blueCaptures && redKills == blueKills) {
				redTeamLead(obj, redCaptures, redKills, blueCaptures, blueKills);
			}
			
			//empty space
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(3);
			
			//time left counter
			obj.getScore(ChatColor.GRAY + "Time Left: " + ChatColor.GREEN + ctf.getString("current-countdown")).setScore(2);
			
			//empty space
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(1);
			
			//server ip
			obj.getScore(ChatColor.GREEN + "play.thefruitbox.net").setScore(0);
			
			p.setScoreboard(board);
		}
	}
	
	void redTeamLead(Objective obj, int redCaptures, int redKills, int blueCaptures, int blueKills) {
		//red team on top
		obj.getScore(ChatColor.RED.toString() + ChatColor.BOLD + "RED").setScore(10);
		obj.getScore(ChatColor.GRAY + "Captures: " + ChatColor.YELLOW + redCaptures + ChatColor.GOLD + "/" + ChatColor.YELLOW + 5 + ChatColor.RESET.toString()).setScore(9);
		obj.getScore(ChatColor.GRAY + "Kills: " + ChatColor.YELLOW + redKills + ChatColor.RESET.toString()).setScore(8);
		
		//empty space
		obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(7);
		
		//blue team below
		obj.getScore(ChatColor.BLUE.toString() + ChatColor.BOLD + "BLUE").setScore(6);
		obj.getScore(ChatColor.GRAY + "Captures: " + ChatColor.YELLOW + blueCaptures + ChatColor.GOLD + "/" + ChatColor.YELLOW + 5).setScore(5);
		obj.getScore(ChatColor.GRAY + "Kills: " + ChatColor.YELLOW + blueKills).setScore(4);
	}
	
	void blueTeamLead(Objective obj, int redCaptures, int redKills, int blueCaptures, int blueKills) {
		//blue team on top
		obj.getScore(ChatColor.BLUE.toString() + ChatColor.BOLD + "BLUE").setScore(10);
		obj.getScore(ChatColor.GRAY + "Captures: " + ChatColor.YELLOW + blueCaptures + ChatColor.GOLD + "/" + ChatColor.YELLOW + 5).setScore(9);
		obj.getScore(ChatColor.GRAY + "Kills: " + ChatColor.YELLOW + blueKills).setScore(8);
		
		//empty space
		obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(7);
		
		//red team below
		obj.getScore(ChatColor.RED.toString() + ChatColor.BOLD + "RED").setScore(6);
		obj.getScore(ChatColor.GRAY + "Captures: " + ChatColor.YELLOW + redCaptures + ChatColor.GOLD + "/" + ChatColor.YELLOW + 5 + ChatColor.RESET.toString()).setScore(5);
		obj.getScore(ChatColor.GRAY + "Kills: " + ChatColor.YELLOW + redKills + ChatColor.RESET.toString()).setScore(4);
	}
	
	public void setCountdown() {
		CountdownTimerLong timer = new CountdownTimerLong(mainClass,
		        30, 0, (t) -> ctf.set("current-countdown", (t.getMinute() + ":" + (t.getSecondsLeft())))
		);
		timer.scheduleTimer();
	}
}

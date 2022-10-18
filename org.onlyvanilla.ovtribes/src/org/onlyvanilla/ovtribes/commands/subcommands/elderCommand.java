package org.onlyvanilla.ovtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.onlyvanilla.ovtribes.Main;
import org.onlyvanilla.ovtribes.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;

public class elderCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "elder";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "OVTribes Elder help guide";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes elder";
	}

	@Override
	public void perform(Player p, String[] args) {
		p.sendMessage(ChatColor.GRAY + "----- " + mainClass.tribesColor + "TRIBES" + ChatColor.GRAY + " -----");
		p.sendMessage(ChatColor.DARK_RED + "These commands are restricted to Elders & Chiefs only!");
		p.sendMessage(mainClass.lightGreen + "1. " + mainClass.lighterGreen + "/tribes kick" +
				ChatColor.GRAY + " (Kick a player from your tribe)");
		p.sendMessage(mainClass.lightGreen + "2. " + mainClass.lighterGreen + "/tribes setwarp" +
				ChatColor.GRAY + " (Set a tribe warp)");
		p.sendMessage(mainClass.lightGreen + "3. " + mainClass.lighterGreen + "/tribes invite" +
				ChatColor.GRAY + " (Invite a player to your tribe)");
		
//		tribes create [name]
//				tribes delete [name]
//				tribes add [name]
//				tribes kick [name]
//				tribes promote [name]
//				tribes demote [name]
//				tribes ownership [name]
//				tribes warp
//				tribes setwarp
//				tribes list
//				tribes info
//				tribes deposit
	}

}

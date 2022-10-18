package org.onlyvanilla.ovtribes.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {
	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract String getSyntax();
	
	public abstract void perform(Player p, String args[]);
}

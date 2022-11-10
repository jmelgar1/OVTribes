package org.onlyvanilla.ovtribes.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovtribes.commands.subcommands.acceptCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.chiefCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.createCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.declineCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.delWarpCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.deleteCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.demoteCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.depositCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.elderCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.helpCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.infoCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.inviteCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.kickCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.listCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.ownershipCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.promoteCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.renameCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.setWarpCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.warpCommand;
import org.onlyvanilla.ovtribes.commands.subcommands.whoCommand;

public class CommandManager implements CommandExecutor {

	private ArrayList<SubCommand> subCommands = new ArrayList<>();
	
	public CommandManager() {
		subCommands.add(new createCommand());
		subCommands.add(new helpCommand());
		subCommands.add(new chiefCommand());
		subCommands.add(new elderCommand());
		subCommands.add(new infoCommand());
		subCommands.add(new depositCommand());
		subCommands.add(new inviteCommand());
		subCommands.add(new acceptCommand());
		subCommands.add(new declineCommand());
		subCommands.add(new kickCommand());
		subCommands.add(new promoteCommand());
		subCommands.add(new demoteCommand());
		subCommands.add(new ownershipCommand());
		subCommands.add(new deleteCommand());
		subCommands.add(new setWarpCommand());
		subCommands.add(new warpCommand());
		subCommands.add(new delWarpCommand());
		subCommands.add(new listCommand());
		subCommands.add(new renameCommand());
		subCommands.add(new whoCommand());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 0) {
				helpCommand help = new helpCommand();
				help.perform(p, args);
			} else if(args.length > 0) {
				for(int i = 0; i < this.getSubCommands().size(); i++) {
					if(args[0].equalsIgnoreCase(this.getSubCommands().get(i).getName())) {
						this.getSubCommands().get(i).perform(p, args);
						
						return true;
					}
				}
			}
		}
		
		return true;
	}
	
	public ArrayList<SubCommand> getSubCommands() {
		return subCommands;
	}
}

package org.thefruitbox.fbtribes.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.commands.subcommands.acceptCommand;
import org.thefruitbox.fbtribes.commands.subcommands.chiefCommand;
import org.thefruitbox.fbtribes.commands.subcommands.createCommand;
import org.thefruitbox.fbtribes.commands.subcommands.declineCommand;
import org.thefruitbox.fbtribes.commands.subcommands.delWarpCommand;
import org.thefruitbox.fbtribes.commands.subcommands.deleteCommand;
import org.thefruitbox.fbtribes.commands.subcommands.demoteCommand;
import org.thefruitbox.fbtribes.commands.subcommands.depositCommand;
import org.thefruitbox.fbtribes.commands.subcommands.elderCommand;
import org.thefruitbox.fbtribes.commands.subcommands.helpCommand;
import org.thefruitbox.fbtribes.commands.subcommands.infoCommand;
import org.thefruitbox.fbtribes.commands.subcommands.inviteCommand;
import org.thefruitbox.fbtribes.commands.subcommands.kickCommand;
import org.thefruitbox.fbtribes.commands.subcommands.listCommand;
import org.thefruitbox.fbtribes.commands.subcommands.ownershipCommand;
import org.thefruitbox.fbtribes.commands.subcommands.promoteCommand;
import org.thefruitbox.fbtribes.commands.subcommands.renameCommand;
import org.thefruitbox.fbtribes.commands.subcommands.setWarpCommand;
import org.thefruitbox.fbtribes.commands.subcommands.warpCommand;
import org.thefruitbox.fbtribes.commands.subcommands.whoCommand;

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

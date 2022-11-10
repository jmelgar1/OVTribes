package org.onlyvanilla.ovtribes.commands.subcommands;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovtribes.Main;
import org.onlyvanilla.ovtribes.commands.SubCommand;
import org.onlyvanilla.ovtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class listCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
			
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "list";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "List all active tribes";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes list";
	}

	@Override
	public void perform(Player p, String[] args) {
		FileConfiguration tribesFile = mainClass.getTribes();
		
		p.sendMessage(ChatColor.DARK_AQUA + "Tribes:");
		
		int count = 1;
		for(String tribe : tribesFile.getKeys(false)) {
			ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe);
			String showName = tribeSection.getString("showname");
			
			TextComponent tribeInfo = new TextComponent(mainClass.lightGreen.toString() + count + ". " + mainClass.lighterGreen.toString() + showName);
			tribeInfo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tribes info " + tribe));
			tribeInfo.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View " + showName)));
			p.spigot().sendMessage(tribeInfo);
			count++;
		}
	}

}

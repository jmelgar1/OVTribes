package org.onlyvanilla.ovtribes.commands.subcommands;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.onlyvanilla.ovtribes.Main;
import org.onlyvanilla.ovtribes.commands.SubCommand;
import org.onlyvanilla.ovtribes.managers.InventoryManager;
import org.onlyvanilla.ovtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class depositCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();

	TribeManager tribeManager = new TribeManager();
	InventoryManager inventoryManager = new InventoryManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "deposit";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "OVTribes deposit sponges into tribe bank";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes deposit [amount]";
	}

	@Override
	public void perform(Player p, String[] args) {
		String playerTribe = tribeManager.getPlayerTribe(p);
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe);
		
		if(!playerTribe.equals("none")) {
			if(args.length == 2) {
				int amount = Integer.valueOf(args[1]);
				ItemStack sponges = new ItemStack(Material.SPONGE, amount);
				
				if(amount > 0) {
					if(p.getInventory().containsAtLeast(sponges, amount)) {
						InventoryManager.removeItems(p.getInventory(), Material.SPONGE, amount);
							
						int vault = tribeSection.getInt("vault") + amount;
						tribeManager.setLevel(playerTribe, vault);
						tribeManager.addToVault(playerTribe, amount, p);
						p.sendMessage(ChatColor.GREEN + "You have deposited " + amount + " sponges into the tribe bank!");
					} else {
						p.sendMessage(ChatColor.RED + "You do not have " + amount + " sponges in your inventory!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Invalid amount!");
				}	
			} else {
				p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
			}
		} else {
			p.sendMessage(ChatColor.RED + "You are not in a tribe!");
		}
	}
}

package org.onlyvanilla.ovtribes.commands.subcommands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.onlyvanilla.ovtribes.Main;
import org.onlyvanilla.ovtribes.commands.SubCommand;
import org.onlyvanilla.ovtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class depositCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager dataManager = new TribeManager();

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
		return "/tribes deposit";
	}

	@Override
	public void perform(Player p, String[] args) {
		String playerTribe = dataManager.getPlayerTribe(p);
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe);
		
		if(!playerTribe.equals("none")) {
			if(args.length == 2) {
				int amount = Integer.valueOf(args[1]);
				ItemStack sponges = new ItemStack(Material.SPONGE, amount);
				
				if(amount > 0) {
					if(p.getInventory().containsAtLeast(sponges, amount)) {
						for(ItemStack invItem : p.getInventory().getContents()){
							if(invItem != null) {
								if(invItem.getType().equals(Material.SPONGE)) {
									int preAmount = invItem.getAmount();
									int newAmount = preAmount - amount;
									invItem.setAmount(newAmount);
									break;
								}
							}
						}
						
					int bank = tribeSection.getInt("bank") + amount;
					tribeSection.set("bank", bank);
					mainClass.saveTribesFile();
					p.sendMessage(ChatColor.GREEN + "You have deposited " + amount + " sponges into the tribe bank!");
				} else {
					p.sendMessage(ChatColor.RED + "You do not have " + amount + " sponges in your inventory!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Invalid amount!");
			}	
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: /tribes deposit [amount]");
		}
	} else {
		p.sendMessage(ChatColor.RED + "You are not in a tribe!");
	}
		
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

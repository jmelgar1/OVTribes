package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.InventoryManager;
import org.thefruitbox.fbtribes.managers.TribeManager;
import org.thefruitbox.fbtribes.managers.WarpManager;

import net.md_5.bungee.api.ChatColor;

public class setWarpCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();
	WarpManager warpManager = new WarpManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "setwarp";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Set a tribe warp";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes setwarp [warp]";
	}

	@Override
	public void perform(Player p, String[] args) {
		int priceToSetWarp = mainClass.getPrices().getInt("setwarp");
		
		if (args.length == 2) {
			String playerTribe = tribeManager.getPlayerTribe(p);
			if(tribeManager.CheckForElder(playerTribe, p) == true || tribeManager.CheckForChief(playerTribe, p) == true) {
				if(warpManager.getNumberOfWarps(playerTribe) < warpManager.getMaxWarps(playerTribe)) {
					String warpName = args[1];
					if(warpName.matches("[a-zA-Z0-9]*") == true) {
						int vault = tribeManager.getVault(playerTribe);
						int minAmount = tribeManager.getMinimumVaultAmount(playerTribe);
						if(vault >= priceToSetWarp) {
							if(vault-priceToSetWarp >= minAmount) {
								tribeManager.removeFromVault(playerTribe, priceToSetWarp, p);
								warpManager.setWarp(playerTribe, p, warpName);
							} else {
								p.sendMessage(ChatColor.RED + "Your tribe vault can not go below the minimum amount of " + minAmount + "!");
							}
						} else {
							p.sendMessage(ChatColor.RED + "You need at least " + priceToSetWarp + " sponges in the tribe vault to set a tribe warp!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Warp names must only contain letters and/or numbers!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "The tribe's max number of warps is " + warpManager.getMaxWarps(playerTribe) + "!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Only chiefs and elders can set warps!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}

}

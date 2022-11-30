package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;
import org.thefruitbox.fbtribes.managers.WarpManager;

import net.md_5.bungee.api.ChatColor;

public class warpCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();
	WarpManager warpManager = new WarpManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "warp";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Go to tribe warp";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes warp [warp]";
	}

	@Override
	public void perform(Player p, String[] args) {
		int priceToWarp = mainClass.getPrices().getInt("gotowarp");
		
		if (args.length == 2) {
			String playerTribe = tribeManager.getPlayerTribe(p);
			String warpName = args[1];
			
			if(!playerTribe.equals("none")) {
				int vault = tribeManager.getVault(playerTribe);
				int minAmount = tribeManager.getMinimumVaultAmount(playerTribe);
				if(vault >= priceToWarp) {
					if(vault-priceToWarp >= minAmount) {
						if(!warpManager.inWarp.contains(p)) {
							warpManager.warpPlayer(playerTribe, p, warpName);
						} else {
							p.sendMessage(ChatColor.RED + "Please wait before doing that again!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Your tribe vault can not go below the minimum amount of " + minAmount + "!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You need at least " + priceToWarp + " sponges in the tribe vault to warp!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "You are not in a tribe!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
}

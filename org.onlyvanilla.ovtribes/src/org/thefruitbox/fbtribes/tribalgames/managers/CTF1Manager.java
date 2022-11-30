package org.thefruitbox.fbtribes.tribalgames.managers;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;

public interface CTF1Manager {
	
	//Main instance
	Main mainClass = Main.getInstance();
	
	FileConfiguration ctf = mainClass.getCTF();
	List<String> participants = ctf.getStringList("participants");
	List<String> tribes = ctf.getStringList("tribes");
	@SuppressWarnings("unchecked")
	List<Player> playersInArena = (List<Player>) ctf.getList("playersInArena");
}

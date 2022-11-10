package org.onlyvanilla.ovtribes.managers;

import org.bukkit.plugin.Plugin;
import org.onlyvanilla.ovtribes.Main;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

public class EventManager {
	
	//Main instance
	protected Main mainClass = Main.getInstance();
	
	protected SpongeManager spongeManager = new SpongeManager();
	
	//FOR MINING EVENTS
	protected CoreProtectAPI getCoreProtect() {
        Plugin plugin = mainClass.getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (plugin == null || !(plugin instanceof CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (CoreProtect.isEnabled() == false) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 9) {
            return null;
        }

        return CoreProtect;
	}
}

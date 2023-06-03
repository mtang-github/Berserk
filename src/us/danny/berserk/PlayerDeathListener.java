package us.danny.berserk;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
	
	private final CooldownDatabase cooldownDatabase;
	
    public PlayerDeathListener(CooldownDatabase cooldownDatabase) {
		this.cooldownDatabase = cooldownDatabase;
	}
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
    	Player player = (Player)event.getEntity();
    	cooldownDatabase.resetCooldown(player);
    }
}

package us.danny.berserk;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DamageListener implements Listener {
	
	private final String lore;
	private final double hpBreakpoint;
	private final Consumer<Player> effectFunction;
	private final CooldownDatabase cooldownDatabase;
	
	public DamageListener(
		String lore, 
		double hpBreakpoint, 
		Consumer<Player> effectFunction,
		CooldownDatabase cooldownDatabase
	) {
		this.lore = lore;
		this.hpBreakpoint = hpBreakpoint;
		this.effectFunction = effectFunction;
		this.cooldownDatabase = cooldownDatabase;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player)event.getEntity();
			if(!cooldownDatabase.isOnCooldown(player) && isPlayerEligible(player)) {
				double currentHp = player.getHealth();
				double finalDamage = event.getFinalDamage();
				double newHp = currentHp - finalDamage;
				if(newHp < hpBreakpoint) {
					if(currentHp > hpBreakpoint) {
						player.setHealth(hpBreakpoint);
					}
					effectFunction.accept(player);
					event.setDamage(0.0);
				}
			}
		}
	}
	
	private boolean isPlayerEligible(Player player) {
		ItemStack helmet = player.getInventory().getHelmet();
		if(helmet == null) {
			return false;
		}
		
		ItemMeta meta = helmet.getItemMeta();
		if(meta == null || !meta.hasLore()) {
			return false;
		}
		
		List<String> loreList = meta.getLore();
		return loreList != null && loreList.contains(lore);
	}
}

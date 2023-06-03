package us.danny.berserk;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CooldownDatabase {

	private final Map<Player, Integer> cooldownMap;
	private final Consumer<Player> onResetFunction;
	
	public CooldownDatabase(Consumer<Player> onResetFunction) {
		cooldownMap = new HashMap<>();
		this.onResetFunction = onResetFunction;
	}
	
	/**
	 * Enters the specified player into this database with the given cooldown.
	 * @param player - the player to put on cooldown
	 * @param cooldown - the cooldown to give the player
	 */
	public void putOnCooldown(Player player, int cooldown) {
		cooldownMap.put(player, cooldown);
	}
	
	/**
	 * Returns true if and only if the specified player is contained within this
	 * database.
	 * @param player - the player to check the cooldown of
	 * @return true if the player is on cooldown
	 */
	public boolean isOnCooldown(Player player) {
		return cooldownMap.containsKey(player);
	}
	
	/**
	 * For every player currently logged on, if that player has a cooldown, tick it
	 * down by 1. If their cooldown reaches 0, remove the player from the database.
	 */
	public void tickCooldowns() {
		for(World world : Bukkit.getWorlds()) {
			for(Player player : world.getPlayers()) {
				Integer mapValue = cooldownMap.get(player);
				if(mapValue != null) {
					if(mapValue > 1) {
						cooldownMap.put(player, mapValue - 1);
					}
					else {
						cooldownMap.remove(player);
						onResetFunction.accept(player);
					}
				}
			}
		}
	}
	
	/**
	 * Removes the specified player from this cooldown database.
	 * @param player - the player to remove from this database
	 * @return true if the player was contained in this database, false otherwise.
	 */
	public boolean resetCooldown(Player player) {
		Integer oldValue = cooldownMap.remove(player);
		if(oldValue != null) {
			onResetFunction.accept(player);
			return true;
		}
		return false;
	}
}

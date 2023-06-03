package us.danny.berserk;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {
	
	private final CooldownDatabase cooldownDatabase;
	
	public Main() {
		Consumer<Player> onResetFunction = new Consumer<Player>() {
			@Override
			public void accept(Player player) {
				Bukkit.broadcastMessage(
					player.getName() + " " +  Config.ON_RESET_MESSAGE
				);
			}
		};
    	cooldownDatabase = new CooldownDatabase(onResetFunction);
    }
    
    @Override
    public void onEnable() {        
    	
    	//register event listeners
    	PluginManager pluginManager = getServer().getPluginManager();
    	
    	Consumer<Player> effectFunction = new Consumer<Player>() {
			@Override
			public void accept(Player player) {
				player.chat(Config.COMMAND);
				Bukkit.broadcastMessage(
					player.getName() + " " + Config.ON_ACTIVATION_MESSAGE
				);
				cooldownDatabase.putOnCooldown(player, Config.COOLDOWN_TICKS);
			}
    	};
    	DamageListener damageListener = new DamageListener(
    		Config.LORE, 
    		Config.HP_BREAKPOINT,
    		effectFunction,
    		cooldownDatabase
    	);
    	pluginManager.registerEvents(damageListener, this);
    	
    	PlayerDeathListener playerDeathListener = new PlayerDeathListener(
    		cooldownDatabase
    	);
    	pluginManager.registerEvents(playerDeathListener, this);
    	
    	//register command executor
    	this.getCommand("berserkResetCD").setExecutor(
        	new org.bukkit.command.CommandExecutor() {
    			@Override
    			public boolean onCommand(
    				CommandSender sender, 
    				Command command, 
    				String label, 
    				String[] args
    			) {
    				if(sender instanceof Player) {
    					Player player = (Player)sender;
    					cooldownDatabase.resetCooldown(player);
    				}
    				return true;
    			}
        	}
        );
    	
    	//register cooldown ticker
    	new BukkitRunnable(){
			@Override
			public void run() {
				cooldownDatabase.tickCooldowns();
			}
		}.runTaskTimer(this, 0, 1);
    	
        System.out.println(Config.NAME + " enabled");
    }
    
    @Override
    public void onDisable() {
    	System.out.println(Config.NAME + " disabled");
    }
}

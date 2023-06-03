package us.danny.berserk;

public class Config {
	public static final String NAME = "[Berserk]";
	public static final String LORE = "Your rage knows no death!";
	public static final double HP_BREAKPOINT = 4.0;
	public static final int POTION_EFFECT_ID = 11;
	public static final int POTION_EFFECT_SECONDS = 5;
	public static final int POTION_EFFECT_AMPLIFIER = 3;
	public static final String COMMAND 
		= "/effect @p " 
		+ POTION_EFFECT_ID + " " 
		+ POTION_EFFECT_SECONDS + " " 
		+ POTION_EFFECT_AMPLIFIER;
	public static final int TICKS_PER_SECOND = 20;
	public static final int COOLDOWN_MINS = 10;
	public static final int COOLDOWN_TICKS = TICKS_PER_SECOND * 60 * COOLDOWN_MINS;
	public static final String ON_ACTIVATION_MESSAGE = "HAS GONE BERSERK";
	public static final String ON_RESET_MESSAGE = "IS NOW OFF COOLDOWN";
}
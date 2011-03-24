package com.judoguys.bukkit.gps.actions;

import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.judoguys.bukkit.gps.GPS;
import com.judoguys.bukkit.gps.GPSAction;
import com.judoguys.bukkit.gps.configuration.GPSConfiguration;

/**
 * /<command> reset
 * 
 * Points to spawn.
 */
public class ResetAction extends GPSAction
{
	public ResetAction (GPS plugin)
	{
		super(plugin, "reset", "/<command> reset - Points to spawn");
	}

	@Override
	public boolean execute (CommandSender sender, Command command,
			String label, String[] args)
	{
		if (!handlesCommand(sender, command, label, args)) {
			return false;
		}
		
		Player player = (Player)sender;
		
//		getPlugin().log.info(getPlugin().getLabel() + " ResetAction: Getting config for player: " + player.getName());
		
		GPSConfiguration config = getPlugin().configurations.get(player.getName());
		config.reset();
		
		return true;
	}

	@Override
	public boolean handlesCommand (CommandSender sender, Command command,
		String label, String[] args)
	{
		if (!super.handlesCommand(sender, command, label, args)) {
			// The first argument was not this action's name.
			return false;
		}
		
		if (!(sender instanceof Player)) {
			// Only a player can use this command.
			return false;
		}
		
		return args.length == 1; // 'reset'
	}
}

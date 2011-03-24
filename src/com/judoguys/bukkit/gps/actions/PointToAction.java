package com.judoguys.bukkit.gps.actions;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.judoguys.bukkit.gps.GPS;
import com.judoguys.bukkit.gps.GPSAction;
import com.judoguys.bukkit.gps.configuration.GPSConfiguration;

/**
 * /<command> pointto x y z
 * # /<command> pointto x y z <name>
 * # /<command> pointto <name>
 * 
 * Points to a specific set of coordinates.
 */
public class PointToAction extends GPSAction
{
	public PointToAction (GPS plugin)
	{
		super(plugin, "pointto", "/<command> pointto x y z [<name>] - Points to a specific set of coordinates");
	}

	@Override
	public boolean execute (CommandSender sender, Command command,
			String label, String[] args)
	{
		if (!handlesCommand(sender, command, label, args)) {
			return false;
		}
		
		// Get the player that executed this command.
		Player player = (Player)sender;
		
		// Make the location object with the given coordinates.
		String xString = args[1];
		String yString = args[2];
		String zString = args[3];
		Double x = Double.parseDouble(xString);
		Double y = Double.parseDouble(yString);
		Double z = Double.parseDouble(zString);
		Location newLocation = new Location(player.getWorld(), x, y, z);
		
		// Grab this player's GPS configuration object and update it.
		GPSConfiguration config = getPlugin().configurations.get(player.getName());
		config.setLocation(newLocation);
		
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

		return args.length == 4; // 'pointto', 'x', 'y', 'z'
	}
}

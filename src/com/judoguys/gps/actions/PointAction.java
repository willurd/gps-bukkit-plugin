package com.judoguys.gps.actions;

/**
 * Copyright (C) 2011  William Bowers <http://williambowers.net/>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.judoguys.bukkit.chat.Chat;
import com.judoguys.gps.GPS;
import com.judoguys.gps.config.GPSConfiguration;
import com.judoguys.gps.permissions.GPSPermissions;

import java.lang.NumberFormatException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /<command> point x y z
 * # /<command> point x y z <name>
 * # /<command> point waypoint/w <name>
 * # /<command> point player/p <name>
 * 
 * Points to a specific set of coordinates.
 */
public class PointAction extends GPSAction
{
	public PointAction (GPS plugin)
	{
		super(plugin, "point", "/<command> point x y z - Points to a specific location");
		
		// The GPS super permission.
		addSuperPermission(GPSPermissions.SUPER_PERMISSION);
		
		// Required permissions.
		addRequiredPermission(GPSPermissions.POINT_ACTION);
	}

	@Override
	public boolean execute (CommandSender sender, Command command,
			String label, String[] args)
	{
		if (!handlesCommand(sender, command, label, args)) {
			return false;
		}
		
		Chat chat = getPlugin().getChat();
		
		// Get the player that executed this command.
		Player player = (Player)sender;
		
		// Make the location object with the given coordinates.
		String xString = args[1];
		String yString = args[2];
		String zString = args[3];
		
		Double x;
		Double y;
		Double z;
		
		try {
			x = Double.parseDouble(xString);
		} catch (NumberFormatException e) {
			chat.error(player, "Invalid x coordinate: '" + xString + "' is not a number");
			return true;
		}
		
		try {
			y = Double.parseDouble(yString);
		} catch (NumberFormatException e) {
			chat.error(player, "Invalid y coordinate: '" + yString + "' is not a number");
			return true;
		}
		
		try {
			z = Double.parseDouble(zString);
		} catch (NumberFormatException e) {
			chat.error(player, "Invalid z coordinate: '" + zString + "' is not a number");
			return true;
		}
		
		Location newLocation = new Location(player.getWorld(), x, y, z);
		
		// Grab this player's GPS configuration object and update it.
		GPSConfiguration config = getPlugin().configurations.get(player.getName());
		config.setExactLocation(newLocation);
		
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

		return args.length == 4; // 'point', 'x', 'y', 'z'
	}
}

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

import com.judoguys.gps.GPS;
import com.judoguys.gps.GPSAction;
import com.judoguys.gps.config.GPSConfiguration;
import com.judoguys.gps.permissions.GPSPermissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		
		// The GPS super permission.
		addSuperPermission(GPSPermissions.SUPER_PERMISSION);
		
		// Required permissions.
		addRequiredPermission(GPSPermissions.RESET_ACTION);
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

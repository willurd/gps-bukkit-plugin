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
import com.judoguys.gps.GPSAction;
import com.judoguys.gps.permissions.GPSPermissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /<command> reset
 * 
 * Points to spawn.
 */
public class UsageAction extends GPSAction
{
	public static String HELP_ACTION_NAME = "help";
	
	public UsageAction (GPS plugin)
	{
		super(plugin, "help", "/<command> [help]");
		
		// The GPS super permission.
		addSuperPermission(GPSPermissions.SUPER_PERMISSION);
		
		// Required permissions.
		addRequiredPermission(GPSPermissions.HELP_ACTION);
	}
	
	@Override
	public boolean execute (CommandSender sender, Command command,
			String label, String[] args)
	{
		if (!handlesCommand(sender, command, label, args)) {
			return false;
		}
		
		Chat chat = getPlugin().getChat();
		chat.heading(sender, "GPS Usage");
		
		// TODO: Update this action to use the command handler and display
		//       actions according to the sender's permissions, etc.
		
		String usage = command.getUsage();
		
		// The next couple of lines were unashamedly borrowed with
		// very little modification from the Bukkit source.
		for (String line : usage.replace("<command>", label).split("\n")) {
			chat.info(sender, line);
		}
		
		return true;
	}

	@Override
	public boolean handlesCommand (CommandSender sender, Command command,
		String label, String[] args)
	{
		// NOTE: Don't use super.handlesCommand() because it'll return
		//       false if there are no arguments, but this action is
		//       special in that it specifically handles the case of
		//       /<command> with no arguments.
		
		if (!(sender instanceof Player)) {
			// Only a player can use this command.
			return false;
		}
		
		// If there were no arguments to /<command>, this action will
		// take care of it.
		if (args.length == 0) {
			return true;
		}
		
		// Otherwise, check to see if there was one argument, and it
		// matches HELP_ACTION_NAME.
		if (args.length == 1) {
			return args[0].equalsIgnoreCase(HELP_ACTION_NAME);
		}
		
		return false;
	}
}

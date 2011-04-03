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
import com.judoguys.gps.config.GPSConfiguration;
import com.judoguys.gps.permissions.GPSPermissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /<command> follow <player-name>
 * 
 * Follows a given player as they move.
 */
public class FollowAction extends GPSAction
{
	public FollowAction (GPS plugin)
	{
		super(plugin, "follow", "/<command> follow <player> - Follows a player as they move");
		
		// The GPS super permission.
		addSuperPermission(GPSPermissions.SUPER_PERMISSION);
		
		// Required permissions.
		addRequiredPermission(GPSPermissions.FOLLOW_ACTION);
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
		GPSConfiguration config = getPlugin().configurations.get(player.getName());
		
		// Get the player we'd like to follow.
		String playerName = args[1];
		Player playerToFollow = getPlugin().getServer().getPlayer(playerName);
		
		// Notify the player if they are already following this player.
		if (config.isFollowing(playerToFollow)) {
			chat.error(player, "You are already following " + playerName);
			return true;
		}
		
		// Check to make sure their logged in.
		if (playerToFollow == null) {
			chat.error(player, playerName + " is not logged in");
			return true;
		}
		
		// Make sure the player isn't trying to follow themself.
		if (player == playerToFollow) {
			chat.error(player, "You can't follow yourself");
			return true;
		}
		
		// Make sure the player they are trying to follow isn't hidden.
		if (!config.canLocate(playerToFollow)) {
			chat.error(player, "You are unable to find " + playerName);
			return true;
		}
		
		// Make sure both players are in the same world.
		if (player.getWorld() != playerToFollow.getWorld()) {
			chat.error(player, playerName + " is not in this world");
			return true;
		}
		
		// Update this player's GPS configuration object.
		config.follow(playerToFollow);
		
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

		return args.length == 2; // 'follow', '<player-name>'
	}
}

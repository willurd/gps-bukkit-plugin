package com.judoguys.bukkit.gps.actions;

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

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.judoguys.bukkit.gps.GPS;
import com.judoguys.bukkit.gps.GPSAction;
import com.judoguys.bukkit.gps.configuration.GPSConfiguration;

/**
 * /<command> find <player-name>`
 * 
 * Points to the given players location when used.
 */
public class FindAction extends GPSAction
{
	public FindAction (GPS plugin)
	{
		super(plugin, "find", "/<command> find <player-name> - Points to the given players location when used");
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
		
		// Get the player we'd like to find.
		String playerName = args[1];
		Player playerToFind = getPlugin().getServer().getPlayer(playerName);
		
		if (playerToFind == null) {
			player.sendMessage("Player '" + playerName + "' is not logged in");
			return true;
		}
		
		// Make sure both players are in the same world.
		if (player.getWorld() != playerToFind.getWorld()) {
			player.sendMessage("Player '" + playerName + "' is not in this world");
			return true;
		}
		
		// Grab this player's GPS configuration object and update it.
		GPSConfiguration config = getPlugin().configurations.get(player.getName());
		config.setExactLocation(playerToFind.getLocation());
		
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
		
		return args.length == 2; // 'find', '<player-name>'
	}
}

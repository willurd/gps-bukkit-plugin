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
import com.judoguys.bukkit.utils.MessageUtils;

/**
 * /<command> hide
 *   OR
 * /<command> unhide
 * 
 * Allows a player to hide and unhide themselves. When a player
 * is hidden only ops will be able to locate them (and they will
 * be able to location themselves).
 */
public class SetIsHiddenAction extends GPSAction
{
	private boolean hidesPlayer;
	
	public SetIsHiddenAction (GPS plugin, String name, boolean hidesPlayer,
		String description)
	{
		super(plugin, name, "/<command> " + name + " - " + description);
		
		this.hidesPlayer = hidesPlayer;
	}
	
	public boolean getHidesPlayer ()
	{
		return hidesPlayer;
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
		GPSConfiguration config = getPlugin().configurations.get(player.getName());
		
		// Is this player trying to hide or unhide themselves?
		if (hidesPlayer) {
			// They are trying to hide themselves; make sure they arent
			// already hidden.
			if (config.getIsHidden()) {
				MessageUtils.sendWarning(player, "You are already hidden from GPS");
				return true;
			}
			config.setIsHidden(true);
			MessageUtils.sendSuccess(player, "You are now hidden from GPS");
		} else {
			// They are trying to unhide themselves; make sure they are
			// actually hidden first.
			if (!config.getIsHidden()) {
				MessageUtils.sendWarning(player, "You are already not hidden from GPS");
				return true;
			}
			config.setIsHidden(false);
			MessageUtils.sendSuccess(player, "You are no longer hidden from GPS");
		}
		
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
		
		return args.length == 1; // 'hide' or 'unhide'
	}
}

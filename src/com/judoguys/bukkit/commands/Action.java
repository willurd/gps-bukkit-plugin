package com.judoguys.bukkit.commands;

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

public abstract class Action
{
	private String name;
	private String usage;
	
	public Action (String name, String usage)
	{
		this.name = name;
		this.usage = usage;
	}
	
	public String getName ()
	{
		return name;
	}
	
	public String getUsage ()
	{
		return usage;
	}
	
	public abstract boolean execute (CommandSender sender, Command command,
		String label, String[] args);
	
	/**
	 * Returns true if the given command is formatted correctly for
	 * this action, with all the requisite information.
	 */
	public boolean handlesCommand (CommandSender sender, Command command,
		String label, String[] args)
	{
		if (args.length == 0) {
			return false;
		}
		
		String firstArg = args[0];
		
		// The first argument must match the action name.
		return firstArg.equalsIgnoreCase(name);
	}
}

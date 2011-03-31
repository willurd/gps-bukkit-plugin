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

/**
 * A sub-command of a top-level command in bukkit.
 * 
 * Defined as the second keyword in a command string. If a player
 * typed '/money give player1 100', 'money' would be the command,
 * and 'give' would be the action. This class represents a single
 * action, and can be placed in a collection of actions to make
 * up a command.
 */
public abstract class Action
{
	// ======================================================================
	// PRIVATE PROPERTIES
	// ======================================================================
	
	/**
	 * This name of this action: /command <name> args...
	 */
	private String name;
	
	/**
	 * An example (or multiple examples) of how to use this action.
	 */
	private String usage;
	
	// ======================================================================
	// CONSTRUCTORS
	// ======================================================================
	
	public Action (String name, String usage)
	{
		setName(name);
		setUsage(usage);
	}
	
	// ======================================================================
	// GETTERS AND SETTERS
	// ======================================================================
	
	// ~ name
	
	public String getName ()
	{
		return name;
	}
	
	public void setName (String value)
	{
		name = value;
	}
	
	// ~ usage
	
	public String getUsage ()
	{
		return usage;
	}
	
	public void setUsage (String value)
	{
		usage = value;
	}
	
	// ======================================================================
	// ABSTRACT METHODS
	// ======================================================================
	
	/**
	 * Asks this action to try and execute the given command. If this
	 * action doesn't handle the particular command, return false, if it
	 * does handle this command (whether it succeeds in executing it or
	 * not), return true.
	 */
	public abstract boolean execute (CommandSender sender, Command command,
		String label, String[] args);
	
	// ======================================================================
	// PUBLIC METHODS
	// ======================================================================
	
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

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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 * Manages a group of actions that can handle Bukkit commands. Given a
 * CommandSender, Command, and arguments, it will find the appropriate handler
 * (Action) for that command.
 */
public class CommandHandler
{
	// ======================================================================
	// PRIVATE PROPERTIES
	// ======================================================================
	
	private Plugin plugin;
	private HashMap<String, Action> actions;
	
	// ======================================================================
	// CONSTRUCTORS
	// ======================================================================
	
	public CommandHandler (Plugin plugin)
	{
		actions = new HashMap<String, Action>();
	}
	
	// ======================================================================
	// ACCESSORS
	// ======================================================================
	
	// ~ plugin
	
	public Plugin getPlugin ()
	{
		return plugin;
	}
	
	// ~ actions
	
	public void addAction (Action action)
	{
		String name = action.getName();
		
		if (hasAction(name)) {
			throw new Error("Action '" + name + "' already exists");
		}
		
		actions.put(name, action);
	}
	
	public void removeAction (String name)
	{
		if (!hasAction(name)) {
			throw new Error("No action with the name '" + name
					+ "' is registered");
		}
		
		actions.remove(name);
	}
	
	public boolean hasAction (String name)
	{
		return actions.containsKey(name);
	}
	
	public Action getAction (String name)
	{
		return actions.get(name);
	}
	
	public Collection<Action> getActions ()
	{
		return actions.values();
	}
	
	// ======================================================================
	// PUBLIC METHODS
	// ======================================================================
	
	/**
	 * Tries to handle the given command using the registered actions.
	 * 
	 * @return true if the command was handled, false if it wasn't
	 * @throws InvalidCommandException if the given command was recognized
	 *         but improperly formatted
	 */
	public boolean handleCommand (CommandSender sender, Command command,
		String label, String[] args) throws InvalidCommandException
	{
		for (Action action : getActions()) {
			if (action.execute(sender, command, label, args)) {
				// This action handled the command.
				return true;
			}
		}
		
		// The command wasn't handled.
		return false;
	}
	
	/**
	 * Combines the usage strings of each registered action into one
	 * usage string for the set.
	 */
	public String getUsage (CommandSender sender)
	{
		Iterator<Action> it = getActions().iterator();
		StringBuilder builder = new StringBuilder();
		
		while (it.hasNext()) {
			Action action = it.next();
			String usage = action.getUsage();
			if (usage != null) {
				builder.append(usage);
				if (it.hasNext()) {
					builder.append('\n');
				}
			}
		}
		
		return builder.toString();
	}
}

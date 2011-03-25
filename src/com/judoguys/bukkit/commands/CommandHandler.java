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
import org.bukkit.plugin.java.JavaPlugin;

public class CommandHandler
{
	private JavaPlugin plugin;
	private HashMap<String, Action> actions;
	
	public CommandHandler (JavaPlugin plugin)
	{
		actions = new HashMap<String, Action>();
	}
	
	public boolean handleCommand (CommandSender sender, Command command,
		String label, String[] args) throws InvalidCommandException
	{
		if (args.length == 0) {
			throw new InvalidCommandException("Command must have at least one argument, the action.");
		}
		
		Iterator<Action> it = actions.values().iterator();
		while (it.hasNext()) {
			Action action = it.next();
			if (action.execute(sender, command, label, args)) {
				// This action handled the command.
				return true;
			}
		}
		
		// The command wasn't handled.
		return false;
	}
	
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
			throw new Error("No action with the name '" + name + "' is registered");
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
}

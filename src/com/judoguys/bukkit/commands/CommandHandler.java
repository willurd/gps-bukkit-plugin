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

/**
 * Manages a group of actions that can handle Bukkit commands. Given
 * a CommandSender, Command, and arguments, it will find the appropriate
 * handler (Action) for that command.
 * 
 * @example Basic Usage
 *     CommandHandler timeHandler = new CommandHandler();
 *     timeHandler.addAction(new NowAction()); // will handle /time now
 *     timeHandler.addAction(new DayNightAction()); // will handle /time daynight
 *     
 *     public class Plugin extends JavaPlugin {
 *         @Override
 *         public boolean onCommand(CommandSender sender, Command cmd,
 *             String label, String[] args) {
 *             // Make sure the command is a /time command HERE.
 *             // Then pass off the command to timeHandler which will execute
 *             // the appropriate action, depending on the supplied arguments.
 *             timeHandler.handleCommand(sender, command, label, args);
 *         }
 *     }
 */
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
		Iterator<Action> it = actions.values().iterator();
		
		for (Action action : getActions()) {
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
	
	public String getUsage (CommandSender sender)
	{
		Collection<Action> actions = getActions();
		Iterator<Action> it = actions.iterator();
		
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

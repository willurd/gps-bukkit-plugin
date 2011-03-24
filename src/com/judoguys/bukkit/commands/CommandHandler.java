package com.judoguys.bukkit.commands;

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

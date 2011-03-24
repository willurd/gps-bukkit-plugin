package com.judoguys.bukkit.commands;

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

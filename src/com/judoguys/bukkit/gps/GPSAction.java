package com.judoguys.bukkit.gps;

import com.judoguys.bukkit.commands.Action;

public abstract class GPSAction extends Action
{
	private GPS plugin;
	
	public GPSAction (GPS plugin, String name, String usage)
	{
		super(name, usage);
		
		this.plugin = plugin;
	}
	
	public GPS getPlugin ()
	{
		return plugin;
	}
}

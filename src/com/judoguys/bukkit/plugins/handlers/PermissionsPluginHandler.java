package com.judoguys.bukkit.plugins.handlers;

import com.judoguys.bukkit.plugins.PluginHandler;

import org.bukkit.plugin.Plugin;

public class PermissionsPluginHandler extends PluginHandler
{
	private Plugin mainPlugin;
	
	public PermissionsPluginHandler (Plugin mainPlugin)
	{
		super("Permissions");
		
		this.mainPlugin = mainPlugin;
	}
	
	@Override
	public void onPluginEnabled (Plugin plugin)
	{
		super.onPluginEnabled(plugin);
		
		// Do stuff here.
	}
	
	public void onPluginDisabled (Plugin plugin)
	{
		super.onPluginDisabled(plugin);
		
		// Do stuff here.
	}
}

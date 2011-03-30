package com.judoguys.bukkit.plugins;

import org.bukkit.plugin.Plugin;

/**
 * Makes working with other plugins easier. If you want to implement
 * another plugin's functionality in your own plugin (like say,
 * Permissions, for instance), you can create a Permissions plugin handler
 * (or use the stock PermissionsPluginHandler), give it to your
 * PluginHandlerSet instance, and call handlerSet.onPluginEnabled(plugin)
 * and handlerSet.onPluginDisabled(plugin) respectively when each event
 * happens. PluginHandlerSet will perform the work for you of checking
 * the name of the plugin related to the event, and call your Permissions
 * plugin handler when the time is right, leaving you to focus on what
 * happens then.
 */
public abstract class PluginHandler
{
	private String pluginName;
	private Plugin plugin;
	
	public PluginHandler (String pluginName)
	{
		this.pluginName = pluginName;
	}
	
	public String getPluginName ()
	{
		return pluginName;
	}
	
	public Plugin getPlugin ()
	{
		return plugin;
	}
	
	public void onPluginEnabled (Plugin plugin)
	{
		this.plugin = plugin;
	}
	
	public void onPluginDisabled (Plugin plugin)
	{
		plugin = null;
	}
	
	public boolean isEnabled ()
	{
		return plugin != null && plugin.isEnabled();
	}
}

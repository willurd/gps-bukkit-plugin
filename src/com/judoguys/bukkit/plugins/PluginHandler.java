package com.judoguys.bukkit.plugins;

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

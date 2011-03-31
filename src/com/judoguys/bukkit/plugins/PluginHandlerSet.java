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

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.plugin.Plugin;

public abstract class PluginHandlerSet
{
	private HashMap<String, PluginHandler> handlers;
	
	public void PluginHandlerSet ()
	{
		handlers = new HashMap<String, PluginHandler>();
	}
	
	public void onPluginEnabled (Plugin plugin)
	{
		String pluginName = plugin.getClass().getName();
		
		if (hasHandler(pluginName)) {
			PluginHandler handler = getHandler(pluginName);
			handler.onPluginEnabled(plugin);
		}
	}
	
	public void onPluginDisabled (Plugin plugin)
	{
		String pluginName = plugin.getClass().getName();
		
		if (hasHandler(pluginName)) {
			PluginHandler handler = getHandler(pluginName);
			handler.onPluginDisabled(plugin);
		}
	}
	
	public void addHandler (PluginHandler handler)
	{
		String pluginName = handler.getPluginName();
		
		if (hasHandler(pluginName)) {
			throw new Error("PluginHandler '" + pluginName + "' already exists");
		}
		
		handlers.put(pluginName, handler);
	}
	
	public void removeHandler (String pluginName)
	{
		if (!hasHandler(pluginName)) {
			throw new Error("PluginHandler '" + pluginName + "' does not exist");
		}
		
		handlers.remove(pluginName);
	}
	
	public boolean hasHandler (String pluginName)
	{
		return handlers.containsKey(pluginName);
	}
	
	public PluginHandler getHandler (String pluginName)
	{
		return handlers.get(pluginName);
	}
	
	public Collection<PluginHandler> getHandlers ()
	{
		return handlers.values();
	}
}

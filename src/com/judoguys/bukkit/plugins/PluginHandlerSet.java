package com.judoguys.bukkit.plugins;

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

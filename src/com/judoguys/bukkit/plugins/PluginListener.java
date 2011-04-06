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

import com.judoguys.logging.Logger;
import com.judoguys.logging.PrefixLogger;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.server.ServerListener;
import org.bukkit.event.server.PluginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PluginListener extends ServerListener
{
	// ======================================================================
	// PRIVATE PROPERTIES
	// ======================================================================
	
	private Plugin mainPlugin;
	private Logger logger;
	private HashMap<String, PluginHandler> handlers;
	
	// ======================================================================
	// CONSTRUCTORS
	// ======================================================================
	
	public PluginListener (Plugin mainPlugin)
	{
		this.mainPlugin = mainPlugin;
		logger = new PrefixLogger(mainPlugin.getServer().getLogger(), "PluginListener: ");
		handlers = new HashMap<String, PluginHandler>();
	}
	
	// ======================================================================
	// ACCESSORS
	// ======================================================================
	
	// ~ handlers
	
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
	
	// ======================================================================
	// PUBLIC METHODS
	// ======================================================================
	
	public void register (PluginManager pluginManager)
	{
		pluginManager.registerEvent(Event.Type.PLUGIN_ENABLE, this, Priority.Monitor, mainPlugin);
		pluginManager.registerEvent(Event.Type.PLUGIN_DISABLE, this, Priority.Monitor, mainPlugin);
	}
	
	/**
	 * Called when a plugin is enabled.
	 */
	@Override
	public void onPluginEnable (PluginEvent event)
	{
		Plugin plugin = event.getPlugin();
		String pluginName = plugin.getDescription().getName();
		
		if (hasHandler(pluginName)) {
			PluginHandler handler = getHandler(pluginName);
			handler.onPluginEnable(plugin);
		} else {
			logger.info("(onEnable) Handler not found for plugin '" + pluginName + "'");
		}
	}
	
	/**
	 * Called when a plugin is disabled.
	 */
	@Override
	public void onPluginDisable (PluginEvent event)
	{
		Plugin plugin = event.getPlugin();
		String pluginName = plugin.getDescription().getName();
		
		if (hasHandler(pluginName)) {
			PluginHandler handler = getHandler(pluginName);
			handler.onPluginDisable(plugin);
		} else {
			logger.info("(onDisable) Handler not found for plugin '" + pluginName + "'");
		}
	}
}

package com.judoguys.bukkit.plugins.handlers;

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

import com.judoguys.bukkit.permissions.PermissionManager;
import com.judoguys.bukkit.permissions.adapters.PermissionsPluginAdapter;
import com.judoguys.bukkit.plugins.PluginHandler;

import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.plugin.Plugin;

public class PermissionsPluginHandler extends PluginHandler
{
	private Plugin mainPlugin;
	private PermissionManager manager;
	
	public PermissionsPluginHandler (Plugin mainPlugin, PermissionManager manager)
	{
		super("Permissions");
		
		this.mainPlugin = mainPlugin;
		this.manager = manager;
	}
	
	@Override
	public void onPluginEnable (Plugin plugin)
	{
		super.onPluginEnable(plugin);
		
		Permissions permissions = (Permissions)plugin;
		manager.setAdapter(new PermissionsPluginAdapter(manager, permissions));
	}
	
	public void onPluginDisable (Plugin plugin)
	{
		super.onPluginDisable(plugin);
		
		// TODO: Disable Permissions if we're using it.
	}
}

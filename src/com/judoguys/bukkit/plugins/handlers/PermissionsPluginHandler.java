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

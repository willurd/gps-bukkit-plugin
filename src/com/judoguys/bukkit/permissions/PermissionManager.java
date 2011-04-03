package com.judoguys.bukkit.permissions;

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

import com.judoguys.bukkit.permissions.adapters.DefaultAdapter;

import org.bukkit.command.CommandSender;

public class PermissionManager
{
	private PermissionAdapter defaultAdapter = new DefaultAdapter(this);
	private PermissionAdapter adapter;
	
	public PermissionManager ()
	{
		// Does nothing.
	}
	
	public PermissionManager (PermissionAdapter adapter)
	{
		setAdapter(adapter);
	}
	
	public PermissionAdapter getAdapter ()
	{
		return adapter;
	}
	
	public void setAdapter (PermissionAdapter value)
	{
		// Make sure adapter is never null.
		if (value == null) {
			value = defaultAdapter;
		}
		
		adapter = value;
	}
	
	public void reloadPermissions ()
	{
		adapter.reloadPermissions();
	}
	
	public boolean hasPermission (CommandSender sender, Permission permission)
	{
		return adapter.hasPermission(sender, permission);
	}
}

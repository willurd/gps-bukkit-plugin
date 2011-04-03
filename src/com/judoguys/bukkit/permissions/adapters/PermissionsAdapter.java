package com.judoguys.bukkit.permissions.adapters;

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

import com.judoguys.bukkit.permissions.Permission;
import com.judoguys.bukkit.permissions.PermissionAdapter;
import com.judoguys.bukkit.permissions.PermissionManager;

import org.bukkit.command.CommandSender;

/**
 * This is the adapter for the Permissions plugin:
 * {@link http://forums.bukkit.org/threads/admn-dev-permissions-v2-5-5-phoenix-now-with-real-multiworld-permissions-602.5974/}.
 */
public class PermissionsAdapter extends PermissionAdapter
{
	public PermissionsAdapter (PermissionManager manager)
	{
		super(manager);
	}
	
	public void reloadPermissions ()
	{
		// FIXME: Load the permissions.
	}
	
	public boolean hasPermission (CommandSender sender, Permission permission)
	{
		// FIXME: Use the Permissions plugin to figure out if the given
		//        sender has the given permission.
		return false;
	}
}

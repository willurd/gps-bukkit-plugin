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

import org.bukkit.entity.Player;

public abstract class PermissionAdapter
{
	// ======================================================================
	// PRIVATE PROPERTIES
	// ======================================================================
	
	private PermissionManager manager;
	
	// ======================================================================
	// CONSTRUCTORS
	// ======================================================================
	
	public PermissionAdapter (PermissionManager manager)
	{
		this.manager = manager;
	}
	
	// ======================================================================
	// ACCESSORS
	// ======================================================================
	
	// ~ manager
	
	public PermissionManager getManager ()
	{
		return manager;
	}
	
	// ======================================================================
	// PUBLIC ABSTRACT METHODS
	// ======================================================================
	
	// TODO: Implement the rest of the methods from PermissionsPluginAdapter
	//       here.
	
	public abstract void reloadPermissions ();
	
	// public abstract void reloadPermissions (String world);
	
	public abstract boolean hasPermission (Player player, String permission);
	
	// public abstract boolean groupHasPermission (String group, String permission);
}

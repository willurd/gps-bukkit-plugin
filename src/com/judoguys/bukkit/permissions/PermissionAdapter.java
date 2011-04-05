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

import org.bukkit.command.CommandSender;

public abstract class PermissionAdapter
{
	private PermissionManager manager;
	
	public PermissionAdapter ()
	{
		// Does nothing.
	}
	
	public PermissionAdapter (PermissionManager manager)
	{
		this.manager = manager;
	}
	
	public PermissionManager getManager ()
	{
		return manager;
	}
	
	public abstract void reloadPermissions ();
	
	public abstract boolean hasPermission (CommandSender sender, String permission);
	
	// public abstract boolean hasPermission (CommandSender sender, Permission permission);
}

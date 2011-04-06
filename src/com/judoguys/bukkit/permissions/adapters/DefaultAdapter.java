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

public class DefaultAdapter extends PermissionAdapter
{
	// ======================================================================
	// PRIVATE PROPERTIES
	// ======================================================================
	
	private boolean answer;
	
	// ======================================================================
	// CONSTRUCTORS
	// ======================================================================
	
	public DefaultAdapter (PermissionManager manager)
	{
		super(manager);
		
		// Permissions default to true if no adapter has been specified.
		setAnswer(true);
	}
	
	// ======================================================================
	// ACCESSORS
	// ======================================================================
	
	// ~ answer
	
	public boolean getAnswer ()
	{
		return answer;
	}
	
	public void setAnswer (boolean value)
	{
		answer = value;
	}
	
	// ======================================================================
	// PUBLIC METHODS
	// ======================================================================
	
	public void reloadPermissions ()
	{
		// Does nothing.
	}
	
	public boolean hasPermission (CommandSender sender, String permission)
	{
		return getAnswer();
	}
}

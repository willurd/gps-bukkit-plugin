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

import org.bukkit.entity.Player;

public class DefaultAdapter extends PermissionAdapter
{
	// ======================================================================
	// PRIVATE PROPERTIES
	// ======================================================================
	
	private boolean mustBeOp;
	private boolean answer;
	
	// ======================================================================
	// CONSTRUCTORS
	// ======================================================================
	
	public DefaultAdapter (PermissionManager manager)
	{
		super(manager);
		
		// By default, players must be Op in order to have permissions, when
		// no permissions plugins are specified.
		setMustBeOp(true);
		setAnswer(false);
	}
	
	// ======================================================================
	// ACCESSORS
	// ======================================================================
	
	// ~ mustBeOp
	
	public boolean getMustBeOp ()
	{
		return mustBeOp;
	}
	
	public void setMustBeOp (boolean value)
	{
		mustBeOp = value;
	}
	
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
	
	public boolean hasPermission (Player player, String permission)
	{
		if (getMustBeOp()) {
			return player.isOp();
		} else {
			return getAnswer();
		}
	}
}

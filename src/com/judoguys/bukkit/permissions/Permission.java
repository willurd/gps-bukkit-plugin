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

/**
 * Represents a single permission.
 * 
 * TODO: Check out all of the different permissions systems and see
 *       what information each one needs, to figure out what kind of
 *       information this object needs to hold.
 */
public class Permission
{
	// ======================================================================
	// PRIVATE PROPERTIES
	// ======================================================================
	
	private String node;
	
	// ======================================================================
	// CONSTRUCTORS
	// ======================================================================
	
	public Permission (String node)
	{
		setNode(node);
	}
	
	// ======================================================================
	// ACCESSORS
	// ======================================================================
	
	// ~ node
	
	public String getNode ()
	{
		return node;
	}
	
	public void setNode (String value)
	{
		node = value;
	}
	
	// ======================================================================
	// PUBLIC METHODS
	// ======================================================================
	
	public boolean equals (Object obj)
	{
		// TEST: Will this work instead:
		// if (!(obj instanceof Permission)) {
		if (obj == null || !(obj instanceof Permission)) {
			return false;
		}
		Permission other = (Permission)obj;
		return getNode().equals(other.getNode());
	}
	
	public String toString ()
	{
		return "[Permission node='" + getNode() + "']";
	}
}

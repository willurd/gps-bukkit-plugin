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
 */
public class Permission
{
	private String value;
	
	public Permission (String value)
	{
		setValue(value);
	}
	
	public String getValue ()
	{
		return value;
	}
	
	public void setValue (String value)
	{
		this.value = value;
	}
	
	@Override
	public boolean equals (Object other)
	{
		if (other == null) {
			return false;
		}
		
		if (!(other instanceof Permission)) {
			return false;
		}
		
		Permission otherPermission = (Permission)other;
		return getValue().equals(otherPermission.getValue());
	}
	
	@Override
	public String toString ()
	{
		return "[Permission " + getValue() + "]";
	}
}

package com.judoguys.bukkit.gps.utils;

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

import org.bukkit.Location;

public final class LocationUtils
{
	public static String locationToString (Location location)
	{
		int x = (int)location.getBlockX();
		int y = (int)location.getBlockY();
		int z = (int)location.getBlockZ();
		return "[x=" + x + ", y=" + y + ", z=" + z + "]";
	}
}

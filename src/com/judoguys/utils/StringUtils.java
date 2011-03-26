package com.judoguys.utils;

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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public final class StringUtils
{
	public static String join (String[] strings, String delimiter)
	{
		return join(Arrays.asList(strings), delimiter);
	}
	
	/**
	 * Joins the given collection of string into a single string, each
	 * separated by the given delimiter.
	 */
	public static String join (Collection<String> strings, String delimiter)
	{
		if (strings.isEmpty()) {
			return "";
		}
		
		StringBuffer buffer = new StringBuffer();
		join(strings, delimiter, buffer);
		return buffer.toString();
	}
	
	public static void join (String[] strings, String delimiter, StringBuffer buffer)
	{
		join(Arrays.asList(strings), delimiter, buffer);
	}
	
	public static void join (Collection<String> strings, String delimiter, StringBuffer buffer)
	{
		Iterator<String> it = strings.iterator();
		
		if (strings.isEmpty()) {
			return;
		}
		
		buffer.append(it.next());
		
		while (it.hasNext()) {
			buffer.append(delimiter);
			buffer.append(it.next());
		}
	}
}

package com.judoguys.bukkit.utils;

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

import com.judoguys.utils.StringUtils;

import org.bukkit.command.Command;

public final class CommandUtils
{
	public static String COMMAND_PREFIX = "/";
	public static String ARGUMENT_DELIMITER = " ";
	
	public static String buildCommandString (Command cmd, String[] args)
	{
		StringBuffer buffer = new StringBuffer(COMMAND_PREFIX);
		buffer.append(cmd.getName());
		
		if (args.length > 0) {
			buffer.append(ARGUMENT_DELIMITER);
			StringUtils.join(args, ARGUMENT_DELIMITER, buffer);
		}
		
		return buffer.toString();
	}
}

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

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class MessageUtils
{	
	public static ChatColor INFO_COLOR = ChatColor.WHITE;
	public static ChatColor SUCCESS_COLOR = ChatColor.GREEN;
	public static ChatColor WARNING_COLOR = ChatColor.YELLOW;
	public static ChatColor ERROR_COLOR = ChatColor.RED;
	
	public static void sendMessage (CommandSender sender, String message)
	{
		sender.sendMessage(message);
	}
	
	public static void sendInfo (CommandSender sender, String message)
	{
		sendMessage(sender, INFO_COLOR + message);
	}
	
	public static void sendSuccess (CommandSender sender, String message)
	{
		sendMessage(sender, SUCCESS_COLOR + message);
	}
	
	public static void sendWarning (CommandSender sender, String message)
	{
		sendMessage(sender, WARNING_COLOR + message);
	}
	
	public static void sendError (CommandSender sender, String message)
	{
		sendMessage(sender, ERROR_COLOR + message);
	}
}

package com.judoguys.bukkit.chat;

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
import org.bukkit.plugin.Plugin;

/**
 * @example Basic Usage
 *     Messager messager = new Messager();
 *     messager.info(player, "Your current status is: Alive");
 *     messager.notify(player, "Player1 is behind you!");
 *     messager.success(player, "You are now following Player1");
 *     messager.warning(player, "You are about to run out of diamonds");
 *     messager.error(player, "You have been kicked");
 */
public class Chat
{
	// ======================================================================
	// PUBLIC STATIC PROPERTIES
	// ======================================================================
	
	public static ChatColor DEFAULT_INFO_COLOR = ChatColor.WHITE;
	public static ChatColor DEFAULT_NOTIFICATION_COLOR = ChatColor.BLUE;
	public static ChatColor DEFAULT_SUCCESS_COLOR = ChatColor.GREEN;
	public static ChatColor DEFAULT_WARNING_COLOR = ChatColor.YELLOW;
	public static ChatColor DEFAULT_ERROR_COLOR = ChatColor.RED;
	
	// ======================================================================
	// PRIVATE PROPERTIES
	// ======================================================================
	
	private Plugin plugin;
	
	private ChatColor infoColor = DEFAULT_INFO_COLOR;
	private ChatColor notificationColor = DEFAULT_NOTIFICATION_COLOR;
	private ChatColor successColor = DEFAULT_SUCCESS_COLOR;
	private ChatColor warningColor = DEFAULT_WARNING_COLOR;
	private ChatColor errorColor = DEFAULT_ERROR_COLOR;
	
	// ======================================================================
	// CONSTRUCTOR
	// ======================================================================
	
	public Chat (Plugin plugin)
	{
		// Does nothing.
		this.plugin = plugin;
	}
	
	// ======================================================================
	// GETTERS AND SETTERS
	// ======================================================================
	
	// ~ plugin
	
	public Plugin getPlugin ()
	{
		return plugin;
	}
	
	// ~ infoColor
	
	public ChatColor getInfoColor ()
	{
		return infoColor;
	}
	
	public void setInfoColor (ChatColor value)
	{
		infoColor = value;
	}
	
	// ~ notificationColor
	
	public ChatColor getNotificationColor ()
	{
		return notificationColor;
	}
	
	public void setNotificationColor (ChatColor value)
	{
		notificationColor = value;
	}
	
	// ~ successColor
	
	public ChatColor getSuccessColor ()
	{
		return successColor;
	}
	
	public void setSuccessColor (ChatColor value)
	{
		successColor = value;
	}
	
	// ~ warningColor
	
	public ChatColor getWarningColor ()
	{
		return warningColor;
	}
	
	public void setWarningColor (ChatColor value)
	{
		warningColor = value;
	}
	
	// ~ errorColor
	
	public ChatColor getErrorColor ()
	{
		return errorColor;
	}
	
	public void setErrorColor (ChatColor value)
	{
		errorColor = value;
	}
	
	// ======================================================================
	// PRIVATE METHODS
	// ======================================================================
	
	// ------------------------------
	// Message Factory Methods
	// ------------------------------
	
	private String makeInfoMessage (String message)
	{
		return getInfoColor() + message;
	}
	
	private String makeNotifyMessage (String message)
	{
		return getNotificationColor() + message;
	}
	
	private String makeSuccessMessage (String message)
	{
		return getSuccessColor() + message;
	}
	
	private String makeWarningMessage (String message)
	{
		return getWarningColor() + message;
	}
	
	private String makeErrorMessage (String message)
	{
		return getErrorColor() + message;
	}
	
	// ======================================================================
	// PUBLIC METHODS
	// ======================================================================
	
	// ------------------------------
	// Single Player Messaging
	// ------------------------------
	
	public void message (CommandSender sender, String message)
	{
		sender.sendMessage(message);
	}
	
	public void info (CommandSender sender, String message)
	{
		message(sender, makeInfoMessage(message));
	}
	
	public void notify (CommandSender sender, String message)
	{
		message(sender, makeNotifyMessage(message));
	}
	
	public void success (CommandSender sender, String message)
	{
		message(sender, makeSuccessMessage(message));
	}
	
	public void warning (CommandSender sender, String message)
	{
		message(sender, makeWarningMessage(message));
	}
	
	public void error (CommandSender sender, String message)
	{
		message(sender, makeErrorMessage(message));
	}
	
	// ------------------------------
	// Broadcasting
	// ------------------------------
	
	public void broadcastMessage (String message)
	{
		plugin.getServer().broadcastMessage(message);
	}
	
	public void broadcastInfo (String message)
	{
		broadcastMessage(makeInfoMessage(message));
	}
	
	public void broadcastNotify (String message)
	{
		broadcastMessage(makeNotifyMessage(message));
	}
	
	public void broadcastSuccess (String message)
	{
		broadcastMessage(makeSuccessMessage(message));
	}
	
	public void broadcastWarning (String message)
	{
		broadcastMessage(makeWarningMessage(message));
	}
	
	public void broadcastError (String message)
	{
		broadcastMessage(makeErrorMessage(message));
	}
}

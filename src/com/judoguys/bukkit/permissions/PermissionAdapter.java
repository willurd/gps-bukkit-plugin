package com.judoguys.bukkit.permissions;

import org.bukkit.command.CommandSender;

public abstract class PermissionAdapter
{
	private PermissionHandler handler;
	
	public PermissionAdapter ()
	{
		// Does nothing.
	}
	
	public PermissionAdapter (PermissionHandler handler)
	{
		this.handler = handler;
	}
	
	public PermissionHandler getHandler ()
	{
		return handler;
	}
	
	public abstract void reloadPermissions ();
	
	public abstract boolean hasPermission (CommandSender sender, Permission permission);
}

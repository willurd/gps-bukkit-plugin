package com.judoguys.bukkit.permissions;

import org.bukkit.command.CommandSender;

public abstract class PermissionAdapter
{
	private PermissionManager manager;
	
	public PermissionAdapter ()
	{
		// Does nothing.
	}
	
	public PermissionAdapter (PermissionManager manager)
	{
		this.manager = manager;
	}
	
	public PermissionManager getManager ()
	{
		return manager;
	}
	
	public abstract void reloadPermissions ();
	
	public abstract boolean hasPermission (CommandSender sender, Permission permission);
}

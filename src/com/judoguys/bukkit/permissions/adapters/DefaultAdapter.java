package com.judoguys.bukkit.permissions.adapters;

import com.judoguys.bukkit.permissions.Permission;
import com.judoguys.bukkit.permissions.PermissionAdapter;
import com.judoguys.bukkit.permissions.PermissionHandler;

import org.bukkit.command.CommandSender;

public class DefaultAdapter extends PermissionAdapter
{
	public DefaultAdapter (PermissionHandler handler)
	{
		super(handler);
	}
	
	public void reloadPermissions ()
	{
		// Does nothing.
	}
	
	public boolean hasPermission (CommandSender sender, Permission permission)
	{
		// If no adapter has been specified by the
		return true;
	}
}

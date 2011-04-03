package com.judoguys.bukkit.permissions;

import com.judoguys.bukkit.permissions.adapters.DefaultAdapter;

import org.bukkit.command.CommandSender;

public class PermissionHandler
{
	private PermissionAdapter defaultAdapter = new DefaultAdapter(this);
	private PermissionAdapter adapter;
	
	public PermissionHandler ()
	{
		// Does nothing.
	}
	
	public PermissionHandler (PermissionAdapter adapter)
	{
		setAdapter(adapter);
	}
	
	public PermissionAdapter getAdapter ()
	{
		return adapter;
	}
	
	public void setAdapter (PermissionAdapter value)
	{
		// Make sure adapter is never null.
		if (value == null) {
			value = defaultAdapter;
		}
		
		adapter = value;
	}
	
	public void reloadPermissions ()
	{
		adapter.reloadPermissions();
	}
	
	public boolean hasPermission (CommandSender sender, Permission permission)
	{
		return adapter.hasPermission(sender, permission);
	}
}

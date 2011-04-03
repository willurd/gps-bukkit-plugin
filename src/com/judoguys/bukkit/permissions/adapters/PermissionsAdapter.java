package com.judoguys.bukkit.permissions.adapters;

import com.judoguys.bukkit.permissions.Permission;
import com.judoguys.bukkit.permissions.PermissionAdapter;
import com.judoguys.bukkit.permissions.PermissionHandler;

import org.bukkit.command.CommandSender;

/**
 * This is the adapter for the Permissions plugin:
 * {@link http://forums.bukkit.org/threads/admn-dev-permissions-v2-5-5-phoenix-now-with-real-multiworld-permissions-602.5974/}.
 */
public class PermissionsAdapter extends PermissionAdapter
{
	public PermissionsAdapter (PermissionHandler handler)
	{
		
	}
	
	public void reloadPermissions ()
	{
		// FIXME: Load the permissions.
	}
	
	public boolean hasPermission (CommandSender sender, Permission permission)
	{
		// FIXME: Use the Permissions plugin to figure out if the given
		//        sender has the given permission.
		return false;
	}
}

package com.judoguys.bukkit.permissions.adapters;

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

import com.judoguys.bukkit.permissions.Permission;
import com.judoguys.bukkit.permissions.PermissionAdapter;
import com.judoguys.bukkit.permissions.PermissionManager;
import com.judoguys.logging.Logger;
import com.judoguys.logging.PrefixLogger;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This is the adapter for the Permissions plugin:
 * {@link http://forums.bukkit.org/threads/admn-dev-permissions-v2-5-5-phoenix-now-with-real-multiworld-permissions-602.5974/}.
 */
public class PermissionsPluginAdapter extends PermissionAdapter
{
	private Permissions permissions;
	private PermissionHandler permissionHandler;
	
	public PermissionsPluginAdapter (PermissionManager manager, Permissions permissions)
	{
		super(manager);
		
		this.permissions = permissions;
		permissionHandler = permissions.getHandler();
	}
	
	public void reloadPermissions ()
	{
		// TODO: Figure out how to make the Permissions plugin reload
		//       its permissions.
	}
	
	public boolean hasPermission (CommandSender sender, String permission)
	{
		java.util.logging.Logger ml = java.util.logging.Logger.getLogger("Minecraft");
		Logger logger = new PrefixLogger(ml, "Permissions: ");
		
		Player player = (Player)sender;
		
		logger.info("Checking permission '" + permission + "' for player " + player.getDisplayName());
		
		if (permissionHandler.has(player, permission)) {
			logger.info("  - has permission");
		} else {
			logger.info("  - does not have permission");
		}
		
		return permissionHandler.has(player, permission);
	}
}

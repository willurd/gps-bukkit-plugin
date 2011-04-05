package com.judoguys.bukkit.commands;

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

import java.util.List;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * A sub-command of a top-level command in Bukkit.
 * 
 * Defined as the second keyword in a command string. If a player
 * typed '/money give player1 100', 'money' would be the command,
 * and 'give' would be the action. This class represents a single
 * action, and can be placed in a collection of actions to make
 * up a command.
 */
public abstract class Action
{
	// ======================================================================
	// PRIVATE PROPERTIES
	// ======================================================================
	
	/**
	 * This name of this action: /command <name> args...
	 */
	private String name;
	
	/**
	 * An example (or multiple examples) of how to use this action.
	 */
	private String usage;
	
	/**
	 * Whether the player must be an Op in order to perform this action.
	 */
	private boolean requiresOp = false;
	
	/**
	 * A list of permissions where any one of them will give the player
	 * the permission to perform this action.
	 * 
	 * Having any of these permissions means the player doesn't have to
	 * have the permissions in requiredPermissions and doesn't need
	 * to be an Op.
	 */
	private List<String> superPermissions;
	
	/**
	 * A list of permissions the player must have in order to perform
	 * this action. The player must have *all* of these permissions (or
	 * any of the super permissions).
	 * 
	 * Having these permissions means the player doesn't need to be an
	 * Op.
	 */
	private List<String> requiredPermissions;
	
	// ======================================================================
	// CONSTRUCTORS
	// ======================================================================
	
	public Action (String name, String usage)
	{
		setName(name);
		setUsage(usage);
		setSuperPermissions(new ArrayList<String>());
		setRequiredPermissions(new ArrayList<String>());
	}
	
	// ======================================================================
	// ACCESSORS
	// ======================================================================
	
	// ~ name
	
	public String getName ()
	{
		return name;
	}
	
	public void setName (String value)
	{
		name = value;
	}
	
	// ~ usage
	
	public String getUsage ()
	{
		return usage;
	}
	
	public void setUsage (String value)
	{
		usage = value;
	}
	
	// ------------------------------
	// Permissions
	// ------------------------------
	
	// ~ requiresOp
	
	public boolean getRequiresOp ()
	{
		return requiresOp;
	}
	
	public void setRequiresOp (boolean value)
	{
		requiresOp = value;
	}
	
	// ~ superPermissions
	
	public List<String> getSuperPermissions ()
	{
		return superPermissions;
	}
	
	public void setSuperPermissions (List<String> value)
	{
		superPermissions = value;
	}
	
	public void addSuperPermission (String permission)
	{
		if (hasSuperPermission(permission)) {
			// This permission is already in the list; return.
			return;
		}
		superPermissions.add(permission);
	}
	
	public void removeSuperPermission (String permission)
	{
		superPermissions.remove(permission);
	}
	
	public void removeAllSuperPermissions ()
	{
		superPermissions.clear();
	}
	
	public boolean hasSuperPermission (String permission)
	{
		return superPermissions.contains(permission);
	}
	
	// ~ requiredPermissions
	
	public List<String> getRequiredPermissions ()
	{
		return requiredPermissions;
	}
	
	public void setRequiredPermissions (List<String> value)
	{
		requiredPermissions = value;
	}
	
	public void addRequiredPermission (String permission)
	{
		if (hasRequiredPermission(permission)) {
			// This permission is already in the list; return.
			return;
		}
		requiredPermissions.add(permission);
	}
	
	public void removeRequiredPermission (String permission)
	{
		requiredPermissions.remove(permission);
	}
	
	public void removeAllRequiredPermissions ()
	{
		requiredPermissions.clear();
	}
	
	public boolean hasRequiredPermission (String permission)
	{
		return requiredPermissions.contains(permission);
	}
	
	// ======================================================================
	// ABSTRACT METHODS
	// ======================================================================
	
	/**
	 * Asks this action to try and execute the given command. If this
	 * action doesn't handle the particular command, return false, if it
	 * does handle this command (whether it succeeds in executing it or
	 * not), return true.
	 */
	public abstract boolean execute (CommandSender sender, Command command,
		String label, String[] args);
	
	// ======================================================================
	// PUBLIC METHODS
	// ======================================================================
	
	/**
	 * Checks all of the super permissions, required permissions and Op
	 * requirements against the given player.
	 * 
	 * FIXME: See if this can be generalized to CommandSender.
	 */
	// public boolean canBePerformedBy (Player player)
	// {
	// 	// Check the super permissions. If the player has any of these
	// 	// they are allowed to perform this action.
	// 	for (String permission : getSuperPermissions()) {
	// 		if (plugin.hasPermission(player, permission)) {
	// 			// The player has this super permission.
	// 			return true;
	// 		}
	// 	}
	// 	
	// 	// Check the required permissions. If there are any, all of
	// 	// them must match for this player to be able to perform this
	// 	// action.
	// 	if (getRequiredPermissions().size() > 0) {
	// 		// There are required permissions.
	// 		for (String permission : getRequiredPermissions()) {
	// 			if (!plugin.hasPermission(player, permission)) {
	// 				return false;
	// 			}
	// 		}
	// 		// All required permissions are met.
	// 		return true;
	// 	}
	// 	
	// 	if (getRequiresOp()) {
	// 		// There are no specified permissions, but this action
	// 		// requires Op.
	// 		if (!player.isOp()) {
	// 			// This player is not Op and thus does not have permission
	// 			// to perform this action.
	// 			return false;
	// 		}
	// 	}
	// 	
	// 	// Either there are no specified permissions requirement, or this
	// 	// action does not require Op, or this player meets all
	// 	// requirements. Either way, they have permission to perform this
	// 	// action.
	// 	return true;
	// }
	
	/**
	 * Returns true if the given command is formatted correctly for
	 * this action, with all the requisite information.
	 */
	public boolean handlesCommand (CommandSender sender, Command command,
		String label, String[] args)
	{
		if (args.length == 0) {
			return false;
		}
		
		String firstArg = args[0];
		
		// The first argument must match the action name.
		return firstArg.equalsIgnoreCase(name);
	}
}

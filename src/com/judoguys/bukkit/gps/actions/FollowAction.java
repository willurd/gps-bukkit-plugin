package com.judoguys.bukkit.gps.actions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.judoguys.bukkit.gps.GPS;
import com.judoguys.bukkit.gps.GPSAction;
import com.judoguys.bukkit.gps.configuration.GPSConfiguration;

/**
 * /<command> follow <player-name>
 * 
 * Follows a given player as they move.
 */
public class FollowAction extends GPSAction
{
	public FollowAction (GPS plugin)
	{
		super(plugin, "follow", "/<command> follow <player-name> - Follows a given player as they move");
	}

	@Override
	public boolean execute (CommandSender sender, Command command,
			String label, String[] args)
	{
		if (!handlesCommand(sender, command, label, args)) {
			return false;
		}
		
		// Get the player that executed this command.
		Player player = (Player)sender;
		
		// Get the player we'd like to follow.
		String playerName = args[1];
		Player playerToFollow = getPlugin().getServer().getPlayer(playerName);
		
		if (playerToFollow == null) {
			player.sendMessage("Player '" + playerName + "' is not logged in");
			return true;
		}
		
		// Make sure the player isn't trying to follow themself.
		if (player == playerToFollow) {
			player.sendMessage("You can't follow yourself");
			return true;
		}
		
		// Make sure both players are in the same world.
		if (player.getWorld() != playerToFollow.getWorld()) {
			player.sendMessage("Player '" + playerName + "' is not in this world");
			return true;
		}
		
		// Grab this player's GPS configuration object and update it.
		GPSConfiguration config = getPlugin().configurations.get(player.getName());
		config.follow(playerToFollow);
		
		return true;
	}

	@Override
	public boolean handlesCommand (CommandSender sender, Command command,
		String label, String[] args)
	{
		if (!super.handlesCommand(sender, command, label, args)) {
			// The first argument was not this action's name.
			return false;
		}

		if (!(sender instanceof Player)) {
			// Only a player can use this command.
			return false;
		}

		return args.length == 2; // 'follow', '<player-name>'
	}
}

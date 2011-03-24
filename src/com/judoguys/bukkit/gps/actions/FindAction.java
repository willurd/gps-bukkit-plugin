package com.judoguys.bukkit.gps.actions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.judoguys.bukkit.gps.GPS;
import com.judoguys.bukkit.gps.GPSAction;
import com.judoguys.bukkit.gps.configuration.GPSConfiguration;

/**
 * /<command> find <player-name>`
 * 
 * Points to the given players location when used.
 */
public class FindAction extends GPSAction
{
	public FindAction (GPS plugin)
	{
		super(plugin, "find", "/<command> find <player-name> - Points to the given players location when used");
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
		
		// Get the player we'd like to find.
		String playerName = args[1];
		Player playerToFind = getPlugin().getServer().getPlayer(playerName);
		
		if (playerToFind == null) {
			player.sendMessage("Player '" + playerName + "' is not logged in");
			return true;
		}
		
		// Make sure both players are in the same world.
		if (player.getWorld() != playerToFind.getWorld()) {
			player.sendMessage("Player '" + playerName + "' is not in this world");
			return true;
		}
		
		// Grab this player's GPS configuration object and update it.
		GPSConfiguration config = getPlugin().configurations.get(player.getName());
		config.setLocation(playerToFind.getLocation());
		
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
		
		return args.length == 2; // 'find', '<player-name>'
	}
}

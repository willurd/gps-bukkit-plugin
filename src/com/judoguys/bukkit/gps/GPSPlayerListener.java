package com.judoguys.bukkit.gps;

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

import java.io.File;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.judoguys.bukkit.gps.configuration.GPSConfiguration;
import com.judoguys.bukkit.gps.configuration.GPSConfigurationType;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GPSPlayerListener extends PlayerListener
{
	private Logger log;
	
	private GPS plugin;
	
	public GPSPlayerListener (GPS plugin)
	{
		this.plugin = plugin;
		log = plugin.log;
	}
	
	public GPS getPlugin ()
	{
		return plugin;
	}
	
	@Override
	public void onPlayerJoin (PlayerEvent event)
	{
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		// Iterate over the configurations and update any that are following
		// this player.
		for (GPSConfiguration config : getPlugin().configurations.values()) {
			if (config.getType().equals(GPSConfigurationType.FOLLOWING_PLAYER) &&
				config.getFollowedPlayerName().equalsIgnoreCase(playerName)) {
				// This will update the config object with the new Player instance
				// for this player. This has the added bonus of notifying the owner
				// of the config object that their compass is now following the
				// player (because previously GPS told them the player was logged
				// off and it was simply pointing at their last known location; now
				// it's actually following them again).
				config.follow(player);
			}
		}
		
		GPSConfiguration config;
		
		// See if we already have a config object for this player (we will
		// if they have logged in since the server was started).
		if (getPlugin().configurations.containsKey(playerName)) {
			// The configuration object for this player already exists
			// (there was a save file for it); just return.
			config = getPlugin().configurations.get(playerName);
			config.setPlayer(player); // Pass the new player object to the config.
			config.refreshLocation();
			return;
		}
		
		// There is no config object for this player -- see if there's a
		// config file to load for them.
		log.info(getPlugin().getLabel() + " Searching for player config file");
		
		File configFile = GPSConfiguration.configFileFor(playerName, getPlugin());
		
		if (configFile.exists()) {
			// The config file exists -- try to load it.
			try {
				config = GPSConfiguration.readFrom(configFile, getPlugin());
				getPlugin().configurations.put(playerName, config);
				return;
			} catch (Exception e) {
				log.info(getPlugin().getLabel() + " Error loading config file for " + playerName + ": " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		// Either the config file doesn't exist or there was an error reading
		// it (possibly it was formatted improperly?).
		log.info(getPlugin().getLabel() + " Creating default config for " + playerName);
		
		config = new GPSConfiguration(plugin);
		config.setPlayer(player);
		config.reset();
		getPlugin().configurations.put(playerName, config);
	}
	
	/**
	 * Called when a player attempts to move location in a world.
	 */
	@Override
	public void onPlayerMove (PlayerMoveEvent event)
	{
		// FIXME: Should this return if the event is canceled?
//		if (event.isCancelled()) {
//			return;
//		}
		
		Player movedPlayer = event.getPlayer();
		Location location = movedPlayer.getLocation();
		
		for (GPSConfiguration config : getPlugin().configurations.values()) {
			// Iterate through each configuration object, checking for
			// any that are following the player that just moved.
			if (config.getType().equals(GPSConfigurationType.FOLLOWING_PLAYER) &&
				config.getFollowedPlayer() != null &&
				config.getFollowedPlayer().equals(movedPlayer)) {
				// The player that moved is being followed by the player that
				// owns this configuration object.
				Player player = config.getPlayer();
				if (player.getWorld().equals(movedPlayer.getWorld())) {
					// Both players are in the same world; set the owner's location
					// to be the new location.
					config.updateLocation(location);
				}
			}
		}
	}
	
	/**
	 * Called when a player attempts to teleport to a new location
	 * in a world.
	 */
	@Override
	public void onPlayerTeleport (PlayerMoveEvent event)
	{
		onPlayerMove(event); // They are handled the same.
	}
}

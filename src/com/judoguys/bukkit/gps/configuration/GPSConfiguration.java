package com.judoguys.bukkit.gps.configuration;

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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

import com.judoguys.bukkit.gps.GPS;
import com.judoguys.bukkit.utils.LocationUtils;
import com.judoguys.bukkit.utils.MessageUtils;

public class GPSConfiguration
{
	public static String CONFIG_FILE_EXTENSION = "yml";
	
	private Logger log;
	private GPS plugin;
	private String playerName;
	private Player player;
	private GPSConfigurationType type;
	private HashMap<String, Location> namedLocations;
	
	private String followedPlayerName;
	
	/**
	 * Only used when type is FOLLOWING_PLAYER.
	 */
	private Player followedPlayer;
	
	private Location location;
	
	public GPSConfiguration (GPS plugin)
	{
		this.plugin = plugin;
		log = plugin.log;
		
		namedLocations = new HashMap<String, Location>();
	}
	
	public static File configFileFor (String playerName, GPS plugin)
	{
		File playersFolder = plugin.getPlayersFolder();
		return new File(playersFolder, playerName + "." + CONFIG_FILE_EXTENSION);
	}
	
	/**
	 * FIXME: This function is large and gross; clean it up.
	 * 
	 * @param file
	 * @param plugin
	 * @return A GPSConfiguration object that represents the
	 *         given config file.
	 */
	public static GPSConfiguration readFrom (File file, GPS plugin)
	{
		if (!file.exists()) {
			return null;
		}
		
		Server server = plugin.getServer();
		GPSConfiguration config = new GPSConfiguration(plugin);
		
		Configuration configFile = new Configuration(file);
		configFile.load();
		
		String playerName = configFile.getString("player");
		Player player = server.getPlayer(playerName);
		
		if (player == null) {
			plugin.log.info(plugin.getLabel() + " Tried to load configuration for player that isn't logged in: " + playerName);
			return null;
		}
		
		config.setPlayerName(playerName);
		config.setPlayer(player);
		
		String typeString = configFile.getString("type");
		config.setType(GPSConfigurationType.valueOf(typeString));
		
		switch (config.getType())
		{
		case FOLLOWING_PLAYER:
			String followedPlayerName = configFile.getString("followedPlayer");
			Player followedPlayer = server.getPlayer(followedPlayerName);
			
			if (followedPlayer != null) {
				config.follow(followedPlayer);
			} else {
				config.setFollowedPlayerName(followedPlayerName);
				double x = configFile.getDouble("location.x", 0);
				double y = configFile.getDouble("location.y", 0);
				double z = configFile.getDouble("location.z", 0);
				config.setLocation(x, y, z);
			}
			
			break;
		
		case EXACT_LOCATION:
			double x = configFile.getDouble("location.x", 0);
			double y = configFile.getDouble("location.y", 0);
			double z = configFile.getDouble("location.z", 0);
			config.setExactLocation(x, y, z);
			break;
		
		case SPAWN:
			config.reset();
			break;
		}
		
		return config;
	}
	
	public void save ()
	{
		// TODO: Implement GPS configuration saving.
	}
	
	public GPS getPlugin ()
	{
		return plugin;
	}
	
	public String getPlayerName ()
	{
		return playerName;
	}

	public void setPlayerName (String value)
	{
		playerName = value;
	}
	
	public Player getPlayer ()
	{
		return player;
	}

	public void setPlayer (Player value)
	{
		player = value;
	}
	
	public GPSConfigurationType getType ()
	{
		return type;
	}

	public void setType (GPSConfigurationType value)
	{
		type = value;
		
		if (!type.equals(GPSConfigurationType.FOLLOWING_PLAYER)) {
			followedPlayer = null;
		}
	}
	
	public HashMap<String, Location> getNamedLocations ()
	{
		return namedLocations;
	}
	
	public void saveNamedLocation (String name, Location location)
	{
		namedLocations.put(name, location);
	}
	
	public void removeNamedLocation (String name)
	{
		namedLocations.remove(name);
	}
	
	public boolean hasNamedLocation (String name)
	{
		return namedLocations.containsKey(name);
	}
	
	public Location getNamedLocation (String name)
	{
		return namedLocations.get(name);
	}
	
	public Location getLocation ()
	{
		return location;
	}
	
	public void setLocation (double x, double y, double z)
	{
		setLocation(new Location(player.getWorld(), x, y, z));
	}
	
	public void setLocation (Location location)
	{
		this.location = location;
		refreshLocation();
	}
	
	public String getFollowedPlayerName ()
	{
		return followedPlayerName;
	}
	
	public void setFollowedPlayerName (String value)
	{
		followedPlayerName = value;
	}
	
	public Player getFollowedPlayer ()
	{
		return followedPlayer;
	}
	
	public boolean isFollowing (Player otherPlayer)
	{
		return getType().equals(GPSConfigurationType.FOLLOWING_PLAYER) &&
			   getFollowedPlayer() != null &&
			   getFollowedPlayer().equals(otherPlayer);
	}
	
	public boolean canLocate (Player otherPlayer)
	{
		GPSConfiguration otherPlayerConfig = getPlugin().configurations.get(otherPlayer.getName());
		
		// This player is able to locate the other player if that
		// player is not hidden, or this player is an op.
		return !otherPlayerConfig.isHidden() || getPlayer().isOp();
	}
	
	public boolean isHidden ()
	{
		return true; // FIXME: Always true until hiding is implemented.
	}
	
	public void refreshLocation ()
	{
		applyLocation();
		notifyLocation();
	}
	
	public void applyLocation ()
	{
		player.setCompassTarget(location);
	}
	
	public void notifyLocation ()
	{
		switch (getType())
		{
		case FOLLOWING_PLAYER:
			if (getFollowedPlayer() != null) {
				MessageUtils.sendSuccess(player, "GPS is now following " + getFollowedPlayer().getDisplayName());
			} else {
				MessageUtils.sendWarning(player, getFollowedPlayerName() + " is offline -- GPS is pointing at last known location: " + LocationUtils.locationToString(getLocation()));
			}
			break;
		
		case EXACT_LOCATION:
			MessageUtils.sendSuccess(player, "GPS is now pointing at " + LocationUtils.locationToString(getLocation()));
			break;
		
		case SPAWN:
			MessageUtils.sendSuccess(player, "GPS is now pointed at Spawn");
			break;
		}
	}
	
	public void follow (Player playerToFollow)
	{
		setType(GPSConfigurationType.FOLLOWING_PLAYER);
		followedPlayer = playerToFollow;
		
		setFollowedPlayerName(followedPlayer.getName());
		
		setLocation(followedPlayer.getLocation());
	}
	
	public void updateFollowedPlayerLocation ()
	{
		updateLocation(followedPlayer.getLocation());
	}
	
	public void updateLocation (Location location)
	{
		this.location = location;
		
		// Only apply the new location. Don't notify the user as this
		// is just an update (don't spam their chat).
		applyLocation();
	}
	
	public void setExactLocation (double x, double y, double z)
	{
		setType(GPSConfigurationType.EXACT_LOCATION);
		
		setLocation(x, y, z);
	}
	
	public void setExactLocation (Location location)
	{
		setExactLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
	
	public void reset ()
	{
		setType(GPSConfigurationType.SPAWN);
		
		setLocation(player.getWorld().getSpawnLocation());
	}
}

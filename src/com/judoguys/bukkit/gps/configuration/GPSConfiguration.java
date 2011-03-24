package com.judoguys.bukkit.gps.configuration;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

import com.judoguys.bukkit.gps.GPS;

public class GPSConfiguration
{
	private Logger log;
	
	private GPS plugin;
	private Player player;
	private GPSConfigurationType type;
	private HashMap<String, Location> savedLocations;
	
	/**
	 * Only used when type == GPSConfigurationType.FOLLOWING_PLAYER.
	 */
	private Player followedPlayer;

	/**
	 * Only used when type == GPSConfigurationType.EXACT_LOCATION.
	 */
	private Location exactLocation;
	
	public GPSConfiguration (GPS plugin)
	{
		this.plugin = plugin;
		log = plugin.log;
		
		savedLocations = new HashMap<String, Location>();
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
		GPSConfiguration inst = new GPSConfiguration(plugin);
		
		Configuration config = new Configuration(file);
		config.load();
		
		String playerName = config.getString("player");
		Player player = server.getPlayer(playerName);
		
		if (player == null) {
			plugin.log.info(plugin.getLabel() + " Tried to load config for player that doesn't exist: " + playerName);
			return null;
		}
		
		inst.setPlayer(player);
		
		String typeString = config.getString("type");
		inst.setType(GPSConfigurationType.valueOf(typeString));
		
		switch (inst.getType())
		{
		case FOLLOWING_PLAYER:
			String followedPlayerName = config.getString("followedPlayer");
			Player followedPlayer = server.getPlayer(followedPlayerName);
			
			if (followedPlayer == null) {
				// FIXME: Depending on how server.getPlayer() works this might
				//        be totally wrong. If it returns null if the player is
				//        off-line then we'd want to keep the FOLLOWING_PLAYER
				//        type and update the property when that player logs in.
				inst.reset();
			} else {
				inst.follow(followedPlayer);
			}
			break;
		case EXACT_LOCATION:
			double x = config.getDouble("location.x", 0);
			double y = config.getDouble("location.y", 0);
			double z = config.getDouble("location.z", 0);
			inst.setLocation(x, y, z);
			break;
		case SPAWN:
			inst.reset();
			break;
		}
		
		return null;
	}
	
	public void save ()
	{
		// TODO: Implement this.
	}
	
	public GPS getPlugin ()
	{
		return plugin;
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
		
		if (type != GPSConfigurationType.EXACT_LOCATION) {
			exactLocation = null;
		}
		
		if (type != GPSConfigurationType.FOLLOWING_PLAYER) {
			followedPlayer = null;
		}
	}
	
	public HashMap<String, Location> getLocations ()
	{
		return savedLocations;
	}
	
	public void saveLocation (String name, Location location)
	{
		savedLocations.put(name, location);
	}
	
	public void removeLocation (String name)
	{
		savedLocations.remove(name);
	}
	
	public boolean hasLocation (String name)
	{
		return savedLocations.containsKey(name);
	}
	
	public Location getLocation (String name)
	{
		return savedLocations.get(name);
	}

	public Player getFollowedPlayer ()
	{
		return followedPlayer;
	}
	
	public Location getExactLocation ()
	{
		return exactLocation;
	}
	
	public void follow (Player playerToFollow)
	{
		setType(GPSConfigurationType.FOLLOWING_PLAYER);
		followedPlayer = playerToFollow;
		
		try {
			player.setCompassTarget(followedPlayer.getLocation());
		} catch (Exception ex) {
			log.info(getPlugin().getLabel() + " Error setting " + player.getName() + "'s compass target to PLAYER: " + followedPlayer.getName());
			return;
		}
		
		player.sendMessage("Your GPS is now following player '" + followedPlayer.getDisplayName() + "'");
	}
	
	public void setLocation (double x, double y, double z)
	{
		setType(GPSConfigurationType.EXACT_LOCATION);
		exactLocation = new Location(player.getWorld(), x, y, z);
		
		try {
			player.setCompassTarget(exactLocation);
		} catch (Exception ex) {
			log.info(getPlugin().getLabel() + " Error setting " + player.getName() + "'s compass target to LOCATION: " + exactLocation.toString());
			return;
		}
		
		player.sendMessage("Your GPS is now pointing at (x=" + x + ", y=" + y + ", z=" + z + ")");
	}
	
	public void setLocation (Location location)
	{
		setLocation(location.getX(), location.getY(), location.getZ());
	}
	
	public void reset ()
	{
		setType(GPSConfigurationType.SPAWN);
		
		try {
			log.info(getPlugin().getLabel() + " reset(): Player: " + player.toString());
			log.info(getPlugin().getLabel() + " reset(): World: " + player.getWorld().toString());
			log.info(getPlugin().getLabel() + " reset(): Spawn: " + player.getWorld().getSpawnLocation().toString());
			player.setCompassTarget(player.getWorld().getSpawnLocation());
		} catch (Exception ex) {
			log.info(getPlugin().getLabel() + " Error setting " + player.getName() + "'s compass target to SPAWN");
			log.info(getPlugin().getLabel() + " " + ex.toString());
			ex.printStackTrace();
			return;
		}
		
		player.sendMessage("Your GPS is now pointed at Spawn");
	}
}

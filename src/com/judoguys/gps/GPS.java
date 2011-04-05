package com.judoguys.gps;

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

import com.judoguys.bukkit.chat.Chat;
import com.judoguys.bukkit.commands.CommandHandler;
import com.judoguys.bukkit.commands.InvalidCommandException;
import com.judoguys.bukkit.permissions.PermissionManager;
import com.judoguys.bukkit.plugins.PluginListener;
import com.judoguys.bukkit.plugins.handlers.PermissionsPluginHandler;
import com.judoguys.bukkit.utils.CommandUtils;
import com.judoguys.gps.actions.FindAction;
import com.judoguys.gps.actions.FollowAction;
import com.judoguys.gps.actions.PointAction;
import com.judoguys.gps.actions.ResetAction;
import com.judoguys.gps.actions.SetIsHiddenAction;
import com.judoguys.gps.actions.UsageAction;
import com.judoguys.gps.config.GPSConfiguration;
import com.judoguys.gps.listeners.GPSPlayerListener;
import com.judoguys.logging.Logger;
import com.judoguys.logging.PrefixLogger;

import java.io.File;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * GPS - A plugin that provides location-based information and compass
 * reorienting.
 * 
 * Allows players to:
 *   - Point their compass to another player's current location.
 *   - Make their compass follow another player around, and update as
 *     they move.
 *   - Point their compass to spawn.
 *   - Point their compass at a specific set of coordinates.
 *   - Disallow others from locating them using GPS (and reallow).
 * 
 * In the future, will allow players to:
 *   - Save all of their settings so they persist across server runs. ^_^
 *   - Name coordinates and point to them by their names.
 *   - List all of their named coordinates.
 *   - Find out how far (in blocks) they are away from a location
 *     or player.
 *   - See where their compass is currently pointing.
 *   - See the coordinates of a named location or player.
 *   - See the coordinates of where they are currently standing.
 * 
 * See the TODO file for more information on what's planned for GPS.
 */
public class GPS extends JavaPlugin
{
	// ======================================================================
	// PUBLIC STATIC PROPERTIES
	// ======================================================================
	
	public static String COMMAND_NAME = "gps";
	
	// ======================================================================
	// PUBLIC PROPERTIES
	// ======================================================================
	
	public HashMap<String, GPSConfiguration> configurations;
	
	// ======================================================================
	// PRIVATE PROPERTIES
	// ======================================================================
	
	private Logger logger;
	private PluginManager pluginManager;
	private PermissionManager permissionManager;
	private Chat chat;
	private CommandHandler commandHandler;
	private GPSPlayerListener playerListener;
	private PluginListener pluginListener;
	private PluginDescriptionFile desc;
	private File playersFolder;
	private String version;
	
	// ======================================================================
	// CONSTRUCTORS
	// ======================================================================
	
	public GPS ()
	{
		chat = new Chat(this);
	}
	
	// ======================================================================
	// ACCESSORS
	// ======================================================================
	
	// ~ logger
	
	public Logger getLogger ()
	{
		return logger;
	}
	
	// ~ pluginManager
	
	public PluginManager getPluginManager ()
	{
		return pluginManager;
	}
	
	// ~ permissionManager
	
	public PermissionManager getPermissionManager ()
	{
		return permissionManager;
	}
	
	// ~ chat
	
	public Chat getChat ()
	{
		return chat;
	}
	
	// ~ playersFolder
	
	/**
	 * Returns the players folder, where all of the player-specific
	 * GPS information is stored.
	 */
	public File getPlayersFolder ()
	{
		return playersFolder;
	}
	
	// ======================================================================
	// Plugin INTERFACE
	// ======================================================================
	
	/**
	 * Called when the plugin is enabled.
	 */
	public void onEnable ()
	{
		// Setup the GPS configurations.
		configurations = new HashMap<String, GPSConfiguration>();
		
		// Setup the plugin information for logging.
		desc = getDescription();
		version = desc.getVersion();
		
		// Setup the logger.
		String prefix = "[" + desc.getName() + "] ";
		logger = new PrefixLogger(getServer().getLogger(), prefix);
		logger.info("version " + version + " by willurd");
		
		// Setup the data folders.
		File dataFolder = getDataFolder();
		playersFolder = new File(dataFolder.getAbsolutePath() + "/players/");
		
		// Setup the plugin manager.
		pluginManager = getServer().getPluginManager();
		
		// Setup the permission manager.
		permissionManager = new PermissionManager();
		
		// Setup the player listener.
		playerListener = new GPSPlayerListener(this);
		pluginManager.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
		pluginManager.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
		pluginManager.registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Priority.Normal, this);
		
		// Setup the plugin listener (for hooking).
		pluginListener = new PluginListener(this);
		pluginListener.addHandler(new PermissionsPluginHandler(this, permissionManager));
		pluginListener.register(pluginManager); // Takes care of the event registration.
		
		// Load the plugin settings.
		loadSettings();
		
		// Setup permissions.
		setupPermissions();
		
		// Setup the chat commands/actions.
		setupCommands();
		
		logger.info("Plugin Enabled.");
	}
	
	/**
	 * Called when the plugin is disabled (either disabled explicitely,
	 * or when the server is gracefully shutdown).
	 */
	@Override
	public void onDisable ()
	{
		// TODO: What should be done here?.
		
		logger.info("Plugin Disabled.");
	}
	
	/**
	 * Handles commands from a client (any sentance that starts with "/sometext").
	 */
	@Override
	public boolean onCommand (CommandSender sender, Command cmd,
		String label, String[] args)
	{
		if (isEnabled()) {
			if (label.equalsIgnoreCase(COMMAND_NAME)) {
				Chat chat = getChat();
				
				if (!(sender instanceof Player)) {
					chat.error(sender, "GPS can only be used in game");
					return true;
				}
				
				try {
					boolean handled = commandHandler.handleCommand(sender, cmd, label, args);
					
					if (!handled) {
						throw new InvalidCommandException("Invalid command");
					}
					
					return handled;
				} catch (InvalidCommandException ex) {
					chat.error(sender, "Invalid command: " + CommandUtils.buildCommandString(cmd, args));
					return false;
				}
			}
		}
		
		return super.onCommand(sender, cmd, label, args);
	}
	
	// ======================================================================
	// PRIVATE METHODS
	// ======================================================================
	
	/**
	 * Load in the settings files, which make gps settings persistent
	 * across server runs.
	 */
	private void loadSettings ()
	{
		// Make sure we have all the folders we need for this to work.
		ensureDataDirectoriesExist();
		
		// TODO: Load plugin-wide settings, like the command for example.
	}
	
	/**
	 * Creates the directories this plugin uses to store its data.
	 */
	private void ensureDataDirectoriesExist ()
	{
		// Make sure the data folder exists.
		File dataFolder = getDataFolder();
		
		if (!dataFolder.exists()) {
			logger.info("Data folder does not exist. Creating it now.");
			dataFolder.mkdir();
		}
		
		// Make sure the players folder exists.
		File playersFolder = getPlayersFolder();
		
		if (!playersFolder.exists()) {
			logger.info("Players folder does not exist. Creating it now.");
			playersFolder.mkdir();
		}
	}
	
	/**
	 * NOTE: Permissions is not supported...yet.
	 * 
	 * Thought this does nothing now, once permissions is supported
	 * it will load the permissions information and do the requisite
	 * setup.
	 */
	private void setupPermissions ()
	{
		
	}
	
	/**
	 * This is where Actions are registered with the command handler.
	 * If an action is not registered here, it won't get handled.
	 * 
	 * If you want to add a new sub command to /gps, like '/gps orbit',
	 * create a new Action class, OribitAction, and register an instance
	 * of it here.
	 */
	private void setupCommands ()
	{
		commandHandler = new CommandHandler(this);
		
		commandHandler.addAction(new UsageAction(this));
		commandHandler.addAction(new FindAction(this));
		commandHandler.addAction(new FollowAction(this));
		commandHandler.addAction(new PointAction(this));
		commandHandler.addAction(new ResetAction(this));
		commandHandler.addAction(new SetIsHiddenAction(this, "hide", true, "Prevents you from being located"));
		commandHandler.addAction(new SetIsHiddenAction(this, "unhide", false, "Allows you to be located"));
	}
}

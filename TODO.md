# TODO

## Commands

* /gps status - what your GPS is currently pointed at (always display exact coordinates, even when following a player)
* /gps point x y z <name> - points to the given location and saves it under <name>
* /gps point waypoint/wp <name> - points to the given waypoint
* /gps point player/p <name> - points to the given player's current location
* /gps save x y z <name> - saves the given location under <name> without pointing to it
* /gps remove <name>
* /gps list [<page>] - shows your waypoints
* /gps where <name> - location of <name>
* /gps whereami - where you are now
* /gps notifications - Toggle notifications (when someone points their GPS at you)
  * Player1's GPS is now tracking you
  * Player1's GPS is no longer tracking you
  * Player1's GPS is pointed at your current location
* /gps distance x y z - how far, in blocks, you are away from the given location
* /gps distance waypoint/wp <name>
* /gps distance player/p <name>
* /gps give <player> <waypoint> - gives <player> <waypoint>
* /gps pending [<page>] - lists all waypoints given by another player that are pending review
* /gps accept <waypoint> - accepts a waypoint given by another player
* /gps deny <waypoint> - denies a waypoint given by another player (removes it from your pending list)

## Versions

* Update to 1.4
  * onPlayerTeleport (PlayerMoveEvent event) -> onPlayerTeleport (PlayerTeleportEvent event)
  * onPlayerJoin (PlayerEvent event) -> onPlayerJoin (PlayerJoinEvent event)
  * etc

## Misc

* Implement more API in PermissionAdapter. Use:
  * https://github.com/TheYeti/Permissions/wiki/Abstract-Methods
* Rename `/gps reset` to `/gps spawn` and update all corresponding code (like GPSConfiguration.reset())
* Cleanup and document GPSConfiguration
* Add a GPSConfigurationSet class to replace `HashMap<String, GPSConfiguration> configurations` in GPS
  * getConfig(playerObject) -> GPSConfiguration
  * getConfig("playerName") -> GPSConfiguration
  * getConfigsFollowing(playerObject) -> List<GPSConfiguration>
  * getConfigsFollowing("playerName") -> List<GPSConfiguration>
  * etc
* When a player logs off, remove their config from the set
* Rename GPSPermissions to GPSPermission
* Comment each property on GPSPermission
  * What does it allow?
  * Does it cover multiple commands/actions?
  * etc
* Implement configuration saving
  * Saving on every command that updates the config shouldn't be too taxing; they are small files
* Consider removing the last known location for FOLLOWING_PLAYER type configs
* Move the handlesCommand check into CommandHandler
* When a player teleports to a new world, set their compass target to their settings for that world
* Maybe make GPSConfiguration a PlayerListener, and call its onPlayerMove / onPlayerTeleport methods when those events occur? Maybe?

## Settings

Need to fix up settings a bit. The way settings are currently loaded is a bit weak, and could stand an extra layer of abstraction between the file and the code that uses the information.

* Add a class for each settings file
  * PluginSettings => data/settings.json
  * PlayerSettings => data/players/PlayerName.json
* Design the settings and player settings json files, for example:
  <pre><code>{
      "player": "PlayerName",
      "worlds": {
          "World1": {
              type: "FOLLOWING\_PLAYER",
              followedPlayer: "OtherPlayerName",
              location: {
                  x: 1.2,
                  y: 3.4,
                  z: 5.6
              }
          },
          "World2": {
              type: "SPAWN"
          },
          "World1": {
              type: "EXACT\_LOCATION",
              location: {
                  x: 1.2,
                  y: 3.4,
                  z: 5.6
              }
          }
      }
  }
  </code></pre>
* Use JSON for settings:
  * http://code.google.com/p/google-gson/
  * http://stackoverflow.com/questions/1688099/converting-json-to-java/1688182#1688182

## Testing

Need to get setup with a unit testing framework start writing some tests.

## Ideas

* /gps announce - Announce to the whole world/server what your current GPS settings are ("player1's GPS is following player2")

## Notifications

**For players following another player**

* Notify when that player goes offline (need to find the 'player leave/disconnect' event)
* Notify when that player switches worlds (does there need to be a 'world change' event for Players?)
* Notify when that player hides themselves (`/gps hide`)
* Notify when that player comes back into your world from another world
* Notify when that player comes back online

**For players being followed by another player**

* Notify when they start being followed
* Notify when they stop being followed

## Naming/Terms

* "Named Location" becomes "Waypoint"

## [maybe] Per-World Settings

Track a different set of settings for each user for each world.

## Maybe

* /gps setdefault - Makes your current world's settings the default settings
* /gps usedefault - Makes the default settings this world's settings

## SPAWN_CHANGE Event

* Update compasses, that are configured to point to spawn, with the new location when spawn changes

This requires the following pull requests to be accepted:

* Bukkit: https://github.com/Bukkit/Bukkit/pull/175
* CraftBukkit: https://github.com/Bukkit/CraftBukkit/pull/218

## Plugin Config

* The default config file exists at: <data-folder>/config.yml

## Permissions

* 'gps' - enables everything
* 'gps.follow' - enables following another player
* 'gps.point' - enables pointing to another location
* 'gps.point.location' - fine-grained support for pointing to an exact location
* 'gps.point.player' - fine-grained support for pointing to another player's current location
* 'gps.waypoints' - enables everything having to do with waypoints
* 'gps.waypoints.save' - enables saving waypoints
* 'gps.waypoints.remove' - enables removing waypoints
* 'gps.waypoints.list' - enables listing your waypoints
* 'gps.waypoints.use' - enables using a waypoint (saved-location)
* 'gps.waypoints.give' - enables giving waypoints to other players
* 'gps.waypoints.receive' - enables receiving waypoints from other players
* 'gps.command.where' - enables using the 'where' command (finding out what a waypoint's exact location is)
* 'gps.command.whereami' - enables using the 'whereami' command

And more.

## Check Overall Plugin Performance

* The player listener on-move event handler might need some performance improvements
  * For example: having a HashMap from player to GPSConfiguration objects that are following them, instead of iterating over all configurations

## Clock as Altimeter

See about using the clock as an altimeter. In the middle means the location is at the same level. All the way to the right means the location is 64 or more blocks above you. All the way to the left means the location is 64 or more blocks below you.

The max number of blocks (64 above) does not have to be 64; it can be configurable.

	/gps clockmode -- toggles the clock between checking the time and checking the height of your compass target

## Developer API

Add ways for other developers to disable GPS for a specific player (or world-wide, server-wide). One use case is for HeroBounty:

* When someone has a bounty on them their GPS should be disabled
* When someone accepts a bounty their GPS should be disabled

**GPSListener**

Add a GPSListener for other developers to make decisions on when the gps can be used and by who:

	public class GPSListener {
	    public boolean canPlayerUseGPS (Player gpsUser);
	    public boolean canPlayerFollowPlayer (Player gpsUser, Player other);
	    public boolean canPlayerPointToPlayer (Player gpsUser, Player other);
	    public boolean canPlayerPointToLocation (Player gpsUser, Location location);
	}

Developers would have their own GPSListener subclass and register it with the GPS plugin. When GPS goes to do something it will first check with all GPSListeners to see if it's OK:

	gpsPlugin.addGPSListener(new MyGPSListener());
	gpsPlugin.canPlayerUseGPS(somePlayer); // checks permissions AND calls canPlayerUseGPS on all registered GPSListeners

## Economy Integreation

GPS can be integrated into an RPG server with an economy with a few additions: batteries and subscriptions

**Battery**

Optional and configurable.

Your battery life on your GPS goes down over time. When it runs out it will set it's target to be your current position and no retargeting commands will work. In order to get it work work again you must "recharge" it, which costs a certain amount, depending on how uncharged it is. The money goes to the bank.

	/gps battery -- shows your current battery life/usage
	/gps recharge -- spends some money to recharge your battery

**GPS Subscriptions/Plans**

Optional and configurable.

Have different "GPS service accounts", each one providing different "signal strength". A lower signal will cause retargeting commands to have randomness in where they are pointed -- the lower the signal the higher the radius of randomness. At the highest signal, there is no randomness; the block you choose is the block you get. You have to pay for each "plan", charged on some configurable interval and for some configurable amount.

There are 5 plans. 1 bar to 5 bars.

	/gps subscriptions -- (or /gps subs) lists all available plans and the payment interval (id, name, bars (signal), cost)
	/gps subscription -- (or /gps sub) your current plan
	/gps subscribe <id/name> -- (or /gps sub <id/name>) changes your plan
	/gps unsubscribe -- (or /gps unsub) drops your plan. gps becomes unusable.

The highest plan(s) could include unlimited batteries/recharges (configurable).

**Payment**

Payment for these things is non-optional. There's no point in having them if you don't have to pay for them (if it's not part of your RPG setting), otherwise it's just a nuisance.

## Far Off Plans

* If Bukkit ever gets a graphics api:
  * Have a command like /gps route <location> which would light up the blocks along the most efficient route from you to the location you specify
  * Highlight the blocks at your waypoints (named locations) with a certain color, and highlight the block that you're currently pointed to with another color
  * Have arrows and/or names in the direction of your waypoints

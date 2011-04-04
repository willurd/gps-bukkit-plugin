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

**Versions**

* Update to 1.4
  * onPlayerTeleport (PlayerMoveEvent event) -> onPlayerTeleport (PlayerTeleportEvent event)
  * onPlayerJoin (PlayerEvent event) -> onPlayerJoin (PlayerJoinEvent event)
  * etc

**Misc**

* Rename `/gps reset` to `/gps spawn` and update all corresponding code (like GPSConfiguration.reset())
* Cleanup and document GPSConfiguration
* Add a GPSConfigurationSet class to replace `HashMap<String, GPSConfiguration> configurations` in GPS
  * getConfig(playerObject) -> GPSConfiguration
  * getConfig("playerName") -> GPSConfiguration
  * getConfigsFollowing(playerObject) -> List<GPSConfiguration>
  * getConfigsFollowing("playerName") -> List<GPSConfiguration>
  * etc
* When a player logs off, remove their config from the set
* Renamed GPSPermissions to GPSPermission
* Comment each property on GPSPermission
  * What does it allow?
  * Does it cover multiple commands/actions?
  * etc
* Implement configuration saving
  * Saving on every command that updates the config shouldn't be too taxing; they are small files
* Consider removing the last known location for FOLLOWING_PLAYER type configs

**Ideas**

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

**Maybe**

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

## Far Off Plans

* If Bukkit ever gets a graphics api:
  * Have a command like /gps route <location> which would light up the blocks along the most efficient route from you to the location you specify
  * Highlight the blocks at your waypoints (named locations) with a certain color, and highlight the block that you're currently pointed to with another color
  * Have arrows and/or names in the direction of your waypoints

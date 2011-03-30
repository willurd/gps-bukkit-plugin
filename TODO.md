# TODO

* Hook into the player leave/disconnect event (whatever it's called) to notify following players that that player is no longer online
* Change all instances of "named location" to "waypoint"
* Change "pointto" to "point"
* Implement per-world, per-user settings
  * Example config:
      player: player1
      default:
        ishidden: false
        type: FOLLOWING_PLAYER
        followedplayer: player2
      worlds:
        world1:
          ishidden: true
          type: SPAWN
        world2:
          ishidden: false
          type: FOLLOWING_PLAYER
          followedplayer: player3
  * Maybe:
    * /gps setdefault - Makes your current world's settings the default settings
    * /gps usedefault - Makes the default settings this world's settings
* The player listener on-move event handler might need some performance improvements
  * For example: having a HashMap from player to GPSConfiguration objects that are following them, instead of iterating over all configurations
* Deal with followed players that are offline or in a different world:
  * If the player comes back online or into the world let the user know and do business as usual
* Update compasses when spawn changes
* Make use of the default config file: <data-folder>/config.yml
* Think about implementing permissions...maybe
  * Should I support it or wait until it's supported natively?
  * Nice tutorial: http://forums.bukkit.org/threads/dont-be-ignorant-the-correct-way-to-hook.7813/

## Commands

### Finished

* find <player>
* follow <player>
* pointto x y z
* reset
* hide
* unhide

### To do

* status
* pointto x y z [<name>]
* pointto waypoint <name>
* pointto player <name>
* save x y z <name>
* remove <name>
* list
* where - where your GPS is currently pointed
* where <name> - location of <name>
* whereami - where you are now
* notify - Turn on notifications (when someone points their GPS at you)
  - Player1's GPS is now tracking you
  - Player1's GPS is no longer tracking you
  - Player1's GPS is pointed at your current location
* unnotify - Turn off notifications
* distance x y z - how far, in blocks, you are away from the given location
* distance waypoint <name>
* distance player <name>
* give <player> <waypoint> - gives <player> <waypoint>
* pending - lists all waypoints given by another player that are pending review
* accept <waypoint> - accepts a waypoint given by another player
* deny <waypoint> - denies a waypoint given by another player (removes it from your pending list)

## Far Off Plans

* If Bukkit ever gets a graphics api:
  * Have a command like /gps route <location> which would light up the blocks along the most efficient route from you to the location you specify
  * Highlight the blocks at your waypoints (named locations) with a certain color, and highlight the block that you're currently pointed to with another color
  * Have arrows and/or names in the direction of your waypoints

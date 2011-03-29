# TODO

* Hook into the player leave/disconnect event (whatever it's called) to notify following players that that player is no longer online
* The player listener on-move event handler might need some performance improvements
  * For example: having a HashMap from player to GPSConfiguration objects that are following them, instead of iterating over all configurations
* Deal with followed players that are offline or in a different world:
  * Probably just display a warning/info message telling the user
  * If the player comes back online or into the world let the user know and do business as usual:
* Load players configurations when they connect
* Update the compass when a player's spawn changes
* Make use of the default config file: <data-folder>/config.yml
* Consider removing the z coordinate from commands, as it doesn't actually do anything for the compass
* Think about implementing permissions...maybe
  * Should I support it or wait until it's supported natively?

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
* pointto <name>
* name x y z <name>
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

## Far Off Plans

* If Bukkit ever gets a graphics api:
  * Have a command like /gps route <location> which would light up the blocks along the most efficient route from you to the location you specify
  * Highlight the blocks at your waypoints (named locations) with a certain color, and highlight the block that you're currently pointed to with another color

# TODO

* Make the action success messages actually get sent to the user
* The player listener on-move event handler might need some performance improvements
* Deal with followed players that are offline or in a different world:
  * Probably just display a warning/info message telling the user
  * If the player comes back online or into the world let the user know and do business as usual:
* Load players configurations when they connect
* Update the compass when a player's spawn changes
* Make use of the default config file: <data-folder>/config.yml
* Consider removing the z coordinate from commands, as it doesn't actually do anything for the compass

## Commands

### Initial Commands

* find <player-name>
* follow <player-name>
* pointto x y z
* reset - Points to spawn

### Future Commands

* pointto x y z [<name>]
* pointto <name>
* name x y z <name>
* remove <name>
* list
* hide
* unhide
* where - where your GPS is currently pointed
* where <name> - location of <name>
* whereami - where you are now

## Features

* Implement permissions...maybe
  * Should I support it or wait until it's supported natively?
* Make the command configurable...maybe

## Far Off Plans

* If Bukkit ever gets a graphics api:
  * Have a command like /gps route <location> which would light up the blocks along the most efficient route from you to the location you specify
  * Highlight the blocks at your waypoints (named locations) with a certain color, and highlight the block that you're currently pointed to with another color

# GPS - Bukkit Plugin

GPS is a bukkit plugin that provides location-based information and compass reorienting.

My aim for GPS, besides being easy and intuitive etc, is to make it the go to tool for anything having to do with location and finding places.

GPS is currently incomplete but in active development. See TODO.md to see what I'm planning.

## Build

GPS uses Apache Ant for its build system. Run `ant` from the project root to build the jar, which will then be located at dist/GPS.jar.

In order to build this project and use the supplied run-server.sh script, you must have `MINECRAFT_SERVER_PATH` in your env (`export MINECRAFT_SERVER_PATH=/path/to/your/server`) with no trailing slash. This is the path to the root of your bukkit server.

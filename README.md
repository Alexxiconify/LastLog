# LastLog

*Show listing of last logged in players.*

## Description

LastLog will list players who last joined your server or joined for the first time.  Furthermore, it can display the first and last login of specific players.  It comes with a rich set of filters to extract precise date ranges of player joins.  Its intended purpose is that server owners and their staff can easily see new players or who joined when.  There is no data storage; LastLog utilizes Paper player information which is already readily available and presents in an accessible manor.  Because said methods are computationally expensive, this plugin is not recommended for larger scale servers.

## Links

- [Source code](https://github.com/StarTux/LastLog) on Github
- [BukkitDev plugin page](https://dev.bukkit.org/projects/lastlog)
- [SpigotMC plugin page](https://www.spigotmc.org/resources/lastlog.60944/)

## Commands

- `/firstlog [pagenumber]` - List the most recent first logins of players
- `/lastlog [pagenumber]` - List the most recent last logins of players
- `/loginfo <playername>` - Display first and last login date of a player

## Permissions

- `lastlog.*` - Get all permission nodes
- `lastlog.lastlog` - Permit use of /lastlog
- `lastlog.firstlog` - Permit use of /firstlog
- `lastlog.loginfo` - Permit use of /loginfo
- `lastlog.notify` - Receive notification when a player joins for the first time

## Installation

Copy the LastLog.jar into the plugins folder. Restart Paper.

## Configuration

Except for permissions, none.

## Building the Plugin

This project uses Maven for building. IntelliJ IDEA has excellent built-in Maven support.

### Using IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. IDEA will automatically detect the `pom.xml` and import it as a Maven project
3. Use the Maven tool window to run build goals

### Using Command Line
```bash
# Clean and compile
mvn clean compile

# Package the plugin
mvn clean package

# Install to local repository
mvn clean install
```

### Build Output
The compiled plugin will be in `target/lastlog-2.0.0.jar`

### Maven Goals
- `mvn compile` - Compile the source code
- `mvn package` - Create the JAR file
- `mvn clean` - Clean build artifacts
- `mvn install` - Install to local Maven repository

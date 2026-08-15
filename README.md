# Plotly

A Minecraft 1.2.5 CraftBukkit plugin for claiming, protecting, managing, and selling plots of land.

## Commands

| Command                    | Description                        | Console            | OP                  | Founder            | Player |
|----------------------------|------------------------------------|--------------------|---------------------|--------------------|--------|
| `/selector`                | Gives player a Selector Tool.      | :x:                | :white_check_mark:  | :white_check_mark: | :x:    |
| `/founder list`            | Lists all Founders.                | :white_check_mark: | :white_check_mark:  | :x:                | :x:    |
| `/founder add <player>`    | Makes a player a Founder.          | :white_check_mark: | :white_check_mark:  | :x:                | :x:    |
| `/founder remove <player>` | Removes a player's Founder status. | :white_check_mark: | :white_check_mark:  | :x:                | :x:    |
| `/plot claim`              | Claims a selected area.            | :x:                | :x:                 | :white_check_mark: | :x:    |

## Development

> **Note:** The CraftBukkit server JAR is **not** included in this repository. You must provide your own copy of `craftbukkit-1.2.5-R5.1-SNAPSHOT.jar` before building the project.

Install the CraftBukkit 1.2.5 R5.1-SNAPSHOT JAR into your local Maven repository:

```bash
mvn install:install-file \
  -Dfile=/full/path/to/craftbukkit-1.2.5-R5.1-SNAPSHOT.jar \
  -DgroupId=org.bukkit \
  -DartifactId=craftbukkit \
  -Dversion=1.2.5-R5.1-SNAPSHOT \
  -Dpackaging=jar \
  -DgeneratePom=true
```

Then build the project:

```bash
mvn package
```

## Code Formatting

Moneta uses the Spotless Maven Plugin with the Eclipse formatter to maintain a consistent code style.

Format all Java source files:

```bash
mvn spotless:apply
```

Verify that all Java source files are correctly formatted:

```bash
mvn spotless:check
```
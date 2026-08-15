# Plotly

<p>
  <img src="https://img.shields.io/badge/Version-1.0.0-blue.svg" alt="Version">
  <img src="https://img.shields.io/badge/CraftBukkit-1.2.5_R5.1--SNAPSHOT-green" alt="CraftBukkit">
  <img src="https://img.shields.io/badge/Java-8-orange.svg" alt="Java">
</p>

A Minecraft 1.2.5 CraftBukkit plugin for claiming, protecting, managing, and selling plots of land.

Plotly is a land-management plugin built specifically around a hierarchical plot system. Founders can claim plots, expand their territory through adjacent plots, assign owners, and protect their land from other players. Plots are stored persistently using YAML configuration files.

The project is currently in development. The current implementation focuses on Founder management, plot claiming, plot validation, and basic plot protection. Subplots, ownership management, and plot selling will be added as development continues.

## Commands

| Command                    | Description                        | Console            | OP                  | Founder            | Player |
|----------------------------|------------------------------------|--------------------|---------------------|--------------------|--------|
| `/selector`                | Gives player a Selector Tool.      | :x:                | :white_check_mark:  | :white_check_mark: | :x:    |
| `/founder list`            | Lists all Founders.                | :white_check_mark: | :white_check_mark:  | :x:                | :x:    |
| `/founder add <player>`    | Makes a player a Founder.          | :white_check_mark: | :white_check_mark:  | :x:                | :x:    |
| `/founder remove <player>` | Removes a player's Founder status. | :white_check_mark: | :white_check_mark:  | :x:                | :x:    |
| `/plot claim`              | Claims a selected area.            | :x:                | :x:                 | :white_check_mark: | :x:    |

## Plot Claiming

Plots are claimed by Founders through the following process:

1. A Founder receives a **Plot Selector** using `/selector`.
2. The Founder uses the selector to select **two corners** of the desired area.
3. The Founder executes `/plot claim`.
4. Plotly validates the selection:
    - The selected area must not overlap an existing plot.
    - If the Founder already owns a plot, the new plot must be adjacent to one of their existing plots.
    - The selected plot must not exceed the maximum plot size.
    - The Founder must not exceed their total allowed plot size for the world.
5. If all checks pass, Plotly creates the plot.
6. The plot is assigned a **UUID** and stored in `plots.yml`.
7. The plot is protected, preventing players other than the Founder and authorized owners from modifying it.

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
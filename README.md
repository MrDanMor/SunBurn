# Sunburn

**Sunburn** — a plugin that makes players vulnerable to sunlight, just like zombies.  

## Key Features
- 🔥 Players ignite in direct sunlight when standing under the open sky.  
- ⛑ Helmets protect from sun damage but gradually lose durability.  
- 🎭 Any other head-slot item may drop to the ground with a configurable chance, leaving the player exposed and burning.  
- 🌑 Burning depends on light level: by default, players are safe at `SunLight < 15`. This threshold is configurable.  
- 🌊 Being in water provides full protection from the sun.  
- ⚙️ Flexible configuration:  
  - adjustable burn duration  
  - multiple shadow detection modes (`SKYLIGHT`, `ANYLIGHT`, `MATERIALCONFIG`)  
  - customizable lists of helmets and transparent blocks  
  - configurable drop chance for non-helmet head-slot items

## Commands
`/sunburn on` — activates sunburn  
`/sunburn off` — deactivates sunburn  
`/sunburn status` — shows the current config parameters in chat  
`/sunburn reload` — reloads the config  
`/sb` — alias for `/sunburn`

> [!WARNING]  
> You must have **OP** to execute these commands.

## Default config
```
#   _____ _    _ _   _ ____  _    _ _____  _   _ 
#  / ____| |  | | \ | |  _ \| |  | |  __ \| \ | |
# | (___ | |  | |  \| | |_) | |  | | |__) |  \| |
#  \___ \| |  | | . ` |  _ <| |  | |  _  /| . ` |
#  ____) | |__| | |\  | |_) | |__| | | \ \| |\  |
# |_____/ \____/|_| \_|____/ \____/|_|  \_\_| \_|

# Main plugin configuration

cooldown-tick-period: 60
# Ticks: 20 - 100
# Defines how frequently the sun strikes
# CAUTION! May reduce performance on lower values!

burning-duration: 100
# Ticks: 0 - 500
# Defines how long the player will burn after each sun strike
# BE AWARE - if player is already burning, this may do nothing

sun-burn-mode: SKYLIGHT
# Mode: SKYLIGHT / ANYLIGHT / MATERIALCONFIG
# Defines how plugin decides if the player is in shadow
# ---
# SKYLIGHT
# Only sky light level matters
# This is set by default
# ---
# ANYLIGHT
# Any light source matters
# This may be too much, be careful
# ---
# MATERIALCONFIG
# Now player is in shadow if there is any opaque block above him
# If all blocks above the player are transparent - he is exposed to the sun strike
# You can change which block is transparent manually (check 'settings' folder)
# In this folder there are 'transparent-materials.yml' and 'transparent-tags.yml'
# ---

min-light-level: 15
# Integer: 0 - 16
# Defines the minimum level of light required to damage the player
# If set to 16 - it never damages; if set to 0 - it damages anywhere
# This parameter does nothing if MATERIALCONFIG mode is set

player-damage: 0
# Integer: 0 - 100
# Defines how much damage player will receive on each sun strike
# BE AWARE - this is direct damage, it does not include potential damage by fire

helmet-damage: 1
# Integer: 0 - 50
# Defines how much damage equipped helmet will receive on each sun strike
# You can manually change list of items that are considered as helmets ('settings' folder)
# In this folder there is "helmet-materials.yml', check its content

non-helmet-drop-chance: 3.5
# Percentage: 0 - 100 (may be decimal, up to 4 digits accuracy)
# Defines how likely equipped non-helmet will drop on each sun strike
# If less than 0 - any non-helmet item (pumkin, player's head, etc.) does not defend from the sun!
# If set to 0 - any non-helmet item defends from the sun unless taken off
# You can manually change list of items that are considered as 'non-protecting' ('settings' folder)
# In this folder there is "helmet-materials.yml', check its content
# Items marked as 'non-protecting' WILL NOT defend from the sun, even if this config is enabled
# Such items will not be dropped from the head slot either (no matter what the drop chance is)
# Items marked as 'perm-protecting' WILL defend from the sun and have no chance to be dropped

water-defence: false
# Boolean: true / false
# Permanently blocks any sun damage if player is in water
```

## Material Config
```
materials:
  - GLASS
  - WHITE_STAINED_GLASS
  - LIGHT_GRAY_STAINED_GLASS
  - GRAY_STAINED_GLASS
  - BLACK_STAINED_GLASS
  - BROWN_STAINED_GLASS
  - RED_STAINED_GLASS
  - ORANGE_STAINED_GLASS
  - YELLOW_STAINED_GLASS
  - LIME_STAINED_GLASS
  - GREEN_STAINED_GLASS
  - CYAN_STAINED_GLASS
  - LIGHT_BLUE_STAINED_GLASS
  - BLUE_STAINED_GLASS
  - PURPLE_STAINED_GLASS
  - MAGENTA_STAINED_GLASS
  - PINK_STAINED_GLASS
  - GLASS_PANE
  - WHITE_STAINED_GLASS_PANE
  - LIGHT_GRAY_STAINED_GLASS_PANE
  - GRAY_STAINED_GLASS_PANE
  - BLACK_STAINED_GLASS_PANE
  - BROWN_STAINED_GLASS_PANE
  - RED_STAINED_GLASS_PANE
  - ORANGE_STAINED_GLASS_PANE
  - YELLOW_STAINED_GLASS_PANE
  - LIME_STAINED_GLASS_PANE
  - GREEN_STAINED_GLASS_PANE
  - CYAN_STAINED_GLASS_PANE
  - LIGHT_BLUE_STAINED_GLASS_PANE
  - BLUE_STAINED_GLASS_PANE
  - PURPLE_STAINED_GLASS_PANE
  - MAGENTA_STAINED_GLASS_PANE
  - PINK_STAINED_GLASS_PANE
  - CHAIN
  - IRON_BARS
  - COPPER_GRATE
  - EXPOSED_COPPER_GRATE
  - WEATHERED_COPPER_GRATE
  - OXIDIZED_COPPER_GRATE
  - WAXED_COPPER_GRATE
  - WAXED_EXPOSED_COPPER_GRATE
  - WAXED_WEATHERED_COPPER_GRATE
  - WAXED_OXIDIZED_COPPER_GRATE
  - LIGHTNING_ROD
  - END_ROD
  - TORCH
  - SOUL_TORCH
  - REDSTONE_TORCH
  - LANTERN
  - SOUL_LANTERN
  - CONDUIT
  - COBWEB
  - STRING
  - TRIPWIRE_HOOK
  - REDSTONE_WIRE
  - SMALL_AMETHYST_BUD
  - MEDIUM_AMETHYST_BUD
  - LARGE_AMETHYST_BUD
```
## Helmet config
```
helmet-materials:
  - LEATHER_HELMET
  - CHAINMAIL_HELMET
  - IRON_HELMET
  - GOLDEN_HELMET
  - DIAMOND_HELMET
  - NETHERITE_HELMET
  - TURTLE_HELMET
non-protecting-materials: copy-from-transparent-materials

perm-protecting-materials:
  - BEDROCK
```

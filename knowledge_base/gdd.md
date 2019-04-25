# Neverless

1. [Overview](#overview)
   1. [Entities](#entities)
2. [Gameplay](#gameplay)
   1. [Location game screen](#location-game-screen)
   2. [Main menu game screen](#main-menu-game-screen)
   3. [Inventory game screen](#inventory-game-screen)
   4. [Market game screen](#market-game-screen)
   5. [Dialog game screen](#market-game-screen)
   6. [Reading game screen](#market-game-screen)
   7. [Global map game screen](#global-map-game-screen)
3. [Interface](#interface)
   1. [Location game screen UI](#location-game-screen-ui)
4. [Test Game](#test-game)   
   
## Overview

Neverless is an offline RPG with 2D isometric graphics.

## Entities
* Player - main hero of the game. Player is controlled by human who is playing the game
* Location - limited space in which the game takes place
* NPC - characters controlled by computer
* Enemy - special NPC hostile to the player
* Respawn point - coordinates at location where enemy appears
* Portal - place where player is able to move to other location
* Event - event happened in game. Events might be displayed on GUI.

## Gameplay

This paragraph includes main features descriptions. Every feature related to one of game screens.

### Location game screen

#### Player movement
* Player is able to move through the area of location
* Player can't move through impossible terrain

#### Player attack
* Player is able to attack enemy
* Attack depends on next factors:
  * Range between player and enemy
  * Weapon speed
  * Weapon power   

#### Enemy movement
* Enemies are able to move through the area of location
* Enemies move randomly

#### Enemy attack
* Enemies are able to attack the player
* Enemy attack player when last one is in enemy's aggressive range
* Attack depends on next factors:
  * Range between player and enemy
  * Weapon speed
  * Weapon power   

#### Enemy respawn
* Enemies respawn at respawn point position

#### Experience points
* Player gains experience points after successful game events:
  * Enemy murder


## Interface

### Location game screen UI

#### Active pause
* Active pause might be toggled by "Pause" button pressing
* In active pause mode:
  * Any game commands could not be processed
  * User is able to order commands

#### Objects high-light
* All game objects high-lighted on mouse over.

#### Destination marker
* A special marker visualizes the point to which the player goes.

#### Event display
* Events might be displayed in GUI
* Displayable events:
  * FightingPlayerHitEvent
  * FightingEnemyKilledEvent
  
#### Profile display
* Player profile displayed on GUI

#### Portal display
* Portal is displaying on GUI as green polygon
* Portal is displaying only if mouse over it
* Click on portal generates a command to player to move to portal

#### Test Game
* Location "Village"
  * Plain map with grass, road and cliffs.
  * One building - something like tavern
  * One portal to "Dungeon" at north-east
* Location "Dungeon"
  * Simple dungeon with two branches
  * Dungeon contains two respawn points
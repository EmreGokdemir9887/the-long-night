[README.md](https://github.com/user-attachments/files/31645358/README.md)
# The Long Night

*A text-based survival adventure written in Java, built on the "World of Zuul" teaching framework.*

## Premise

You're a crew member on a cargo ship in international waters. A loud noise wakes you in the middle of the night — the hull has been breached and the ship is taking on water. With the nearest port hours away, you and the crew have to fix the damage yourselves before it sinks.

## Objective

1. **Find the water pump** (in the new storage room) and bring it to the **damaged room**.
2. Run `pump` in the damaged room to start pumping out the water.
3. **Find the welding machine** (old storage room) and **metal plates** (deck).
4. The old storage room starts **locked** — one of the crew (`gimgor`) is carrying the key. Find them and use `talk` to get it.
5. Bring both repair items to the damaged room and run `repair` to patch the hull and win.

## Commands

| Command | Effect |
|---|---|
| `go <direction>` | Move through an exit, e.g. `go north` |
| `back` | Return to the room you were in before your last move |
| `search` | List the items in the current room |
| `collect <item>` | Pick up an item, if it fits in your inventory |
| `leave <item>` | Drop a carried item in the current room |
| `inventory` | List what you're carrying |
| `talk` | Check whether anyone in the room is holding the key |
| `pump` | Turn on the water pump (must be in the damaged room with the pump in your inventory) |
| `repair` | Attempt to fix the hull (must be in the damaged room with both repair items) |
| `help` | Show a story reminder and the full command list |
| `quit` | Exit the game |

## Features

- 9-room ship layout with directional navigation (`go north`, `go southwest`, etc.), including some rooms with only one or two exits
- A weight-limited inventory (10 units) — some items, like the water pump (9 units), take up nearly all your capacity on their own, so you can't just grab everything
- Collectable and non-collectable items, including several with no functional purpose beyond exploration (documents, scraps, sus-stuff...)
- 5 non-player crew members who wander the ship randomly after every move the player makes
- A locked room and a key mechanic — one crew member is carrying the only key, and their location isn't fixed
- A "transporter room" that randomly teleports the player to a different room on entry
- A move-history list, so `back` lets you retrace your last step

## Architecture

| Class | Responsibility |
|---|---|
| `Game` | Owns the game loop, builds the ship (rooms, exits, items), interprets commands, and holds the win/lose logic |
| `Room` | A single location: its exits, the items and people currently in it, and its lock state |
| `Item` | A collectable object *or* an NPC (`isPerson`) — NPCs can hold another `Item` as a possession, which is how the key mechanic works |
| `Inventory` | The player's carried items and weight capacity |
| `Parser` | Reads a line of console input and splits it into up to two words |
| `Command` | A parsed (word, word) pair, with a null command word if the input wasn't recognised |
| `CommandWords` | The fixed list of valid command words |

## Tech Stack

- Java (standard library only — `java.util.HashMap`, `HashSet`, `ArrayList`, `Iterator`, `Random`, `Scanner`)
- Console I/O, no external dependencies or build tool
- Originally structured for BlueJ (see "Running the Game" below)

## Running the Game

`Game` includes a `main` method, so it can be run directly from the command line — no IDE or extra setup needed. From the project folder:

```
javac *.java
java Game
```

(The project was originally structured for BlueJ, where you'd create a `Game` instance and call `.play()` on it directly — that still works too.)

## What's Templated vs. Original

This project is built on **"World of Zuul"**, a Java text-adventure skeleton written by Michael Kölling and David J. Barnes. The `Parser`, `Command`, and `CommandWords` classes, and the core structure of `Room` and `Game` (navigation, the command loop), come from that base.

Everything specific to *The Long Night* was built on top of it: the `Item` and `Inventory` classes, the weight-based inventory system, the NPC/possession mechanic and key puzzle, the locked-room logic, the transporter room, the room-history/`back` command, the win condition, and all of the ship's story, rooms, and items.

## Known Limitations

- Crew movement is fully random, so finding the crew member with the key can take a few turns of exploring.
- No automated tests, and no save/load between sessions.

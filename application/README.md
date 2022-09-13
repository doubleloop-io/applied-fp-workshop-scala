# Enhanced Mars Rover Kata

Kata explanation adapted from: https://kata-log.rocks/mars-rover-kata

## Intro

You’re part of the team that explores Mars by sending remotely controlled vehicles to the surface of the planet. Develop an API that translates the commands sent from earth to instructions that are understood by the rover.

## Requirements

- The planet is divided into a grid with x (width) and y (height) size.
- There are many obstacles on the planet. An obstacle has a position expressed as x, y co-ordinates.
- You are given the initial starting point (x,y) of a rover and the orientation (N,S,E,W) it is facing.
- The rover receives a character array of commands.
- Supported commands are: move the rover forward/backward (f,b) and turn the rover left/right (l,r).
- Implement wrapping at edges (pacman effect). Connect the x edge to the other x edge, so (1,1) for x-1 to (5,1), but connect vertical edges towards themselves in inverted coordinates, so (1,1) for y-1 connects to (5,1).
- Implement obstacle detection before each move to a new square. If a given sequence of commands encounters an obstacle, the rover moves up to the last possible point, **aborts** the sequence and reports the obstacle.

## Example

Given a planet of 5x4 with obstacles in 1,1 and 4,1 the layout is:

```
                  North
        +-----+-----+-----+-----+-----+
        | 0,3 |     |     |     | 4,3 |
        +-----+-----+-----+-----+-----+
        |     |     |     |     |     |
  West  +-----+-----+-----+-----+-----+  Est
        |     |*1,1*|     |     |*4,1*|
        +-----+-----+-----+-----+-----+
        | 0,0 |     |     |     | 4,0 |
        +-----+-----+-----+-----+-----+
                  South
```

With a starting position 0,0 N the rover will be in the lower left corner facing North.
After the `BRFFFF` commands sequence the rover will be in position 4,3 facing Est.
Or, after the `LFRFB` commands sequence the rover will be in position 4,0 facing North.

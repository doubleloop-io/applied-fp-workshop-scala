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

## V1 - Focus on the center (pure domain logic)

Develop an API (types and functions) that executes commands:

- Implement all commands logic.
- Commands are sent in batch and executed sequentially.
- The planet grid has a wrapping effect from one edge to another (pacman).
- For now, ignore obstacle detection logic

## V2 - Focus on boundaries (from primitive to domain types and viceversa)

Our domain is declared with rich types but inputs/outputs are should be primitive types

- Write a parser for input planet data (size, obstacles)
- Write a parser for input rover data (position, orientation)
- Write a parser for input commands (skip unknown chars)
- Render the final result as string: "positionX:positionY:orientation"

## V3 - More domain logic (handle obstacles w/ effects)

Extend the API and it's implementation to handle obstacle detection:

- Implement obstacle detection before each move to a new square.
- If the rover encounters an obstacle, rest in the same position and aborts the sequence.
- Render the final result as string:
  - sequence completed: "positionX:positionY:orientation"
  - obstacle detected: "O:positionX:positionY:orientation"

## V4 - Focus on infrastructure (compose I/O operations)

Extend the pure way of work also to the infrastructural layer

- Read planet data from file into IO (size and obstacles)
- Read rover from file into IO (position and orientation)
- Ask and read commands from console into IO
- Implement an entrypoint that:
  - orchestrate domain, infrastructure, parsing and error handling
  - run the whole app lifted in the IO monad
  - print final rover output to the console if everything is ok
  - recover from any unhandled error and print it

## V5 - Testability via injection (Port/Adapter architectural style)

Apply Dependency Inversion Principle and Dependency Injection to our application

- Look to the Ports for read planet, rover and commands
- Implement port adapters
- Define injectable application
- Use test doubles in test suite

## V6 (bonus iteration) - Testability via values (Elm-ish architectural style)

Use values to obtain a strong and simple separation between domain and infrastructure logic

- implement init, update and test them without infrastructure and mocks
- implement infrastructure and test it with integration tests

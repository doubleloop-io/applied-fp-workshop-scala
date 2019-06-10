# Enanched Mars Rover Kata

You’re part of the team that explores Mars by sending remotely controlled vehicles to the surface of the planet.
Implement an application that simulates the movement of a rover on a planet.

NOTE: for each version: first model all functions signatures (use ??? marker) and then implement them.
      The idea is to get a quick and cheap responsibility distribution phase.

## V1 - Focus on the center (pure domain logic and types)
Develop an API that translates the commands sent from earth to instructions that are executed by the rover.
- The planet is divided into a grid with x (width) and y (height) size.
- The rover has a position expressed as x, y co-ordinates and an orientation (North, Est, West, South).
- The rover can handle four commands: TurnLeft, TurnRight, MoveForward, MoveBackward.
- Commands are sent in batch (like an array)
- Implement wrapping from one edge of the grid to another (pacman effect).

## V2 - Focus on boundaries (from primitive types to domain types)
Our domain is composed by rich types but input/output data must be privitive
- Write a parser for the planet (grid) size: "5x4"
- Write a rover initial state parser: "1,3:W"
- Write an rendering as string: "positionX:positionY:direction"

## V3 - More domain logic (partial function in domain logic)
We discover that there are are obstacles on the planet.
- domain logic:
    - An obstacle has a position expressed as x, y co-ordinates.
    - There are many obstacles.
    - Implement obstacle detection before each move to a new position. 
    - If a given sequence of commands encounters an obstacle, the rover moves up to the last possible point and aborts the sequence.
- boundaries:
    - Write a parser for a list of obstacles.
    - Update rendering, show hit obstacle info.

## V4 - Focus on I/O (compose pure IO values)
Extend the "pure" way of work also to the infrastructural layer
- Build initial state and execute all commands:
    - Read and parse the planet.txt file
    - Read and parse the obstacles.txt file
    - Read and parse the rover.txt file
    - Read and parse the commands.txt file
- After commands execution:
    - Print final output to the console (happy and not happy paths)
    - Handle, in a safe way, any unhandled exception and log them
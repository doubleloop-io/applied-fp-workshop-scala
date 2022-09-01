# Enhanced Mars Rover Kata

You’re part of the team that explores Mars by sending remotely controlled vehicles to the surface of the planet.
Implement an application that simulates the movement of a rover on a planet with data provided by user.

NOTE: for each version try to first model all functions signatures (use ??? marker) and then implement them. The idea is
to get a quick and cheap responsibility distribution phase.

For a full kata explanation see: https://kata-log.rocks/mars-rover-kata

## Data

- The planet is divided into a grid with x (width) and y (height) size.
- There are many obstacles on the planet. An obstacle has a position expressed as x, y co-ordinates.
- The rover has a position expressed as x, y co-ordinates and an orientation (North, Est, West, South).
- The rover can handle four command types: turn left or right, move forward or backward.

## V1 - Focus on the center (pure domain logic)

Develop an API (types and functions) that executes commands:

- Implement all commands logic.
- Commands are sent in batch and executed sequentially.
- The planet grid has a wrapping effect from one edge to another (pacman).
- For now, ignore obstacle detection logic

## V2 - Focus on boundaries (from primitive types to domain types and viceversa)

Our domain is declared with rich types but inputs/outputs are should be primitive types

- Write a parser for input planet data (size, obstacles)
- Write a parser for input rover data (position, orientation)
- Write a parser for input commands (skip unknown chars)
- Render the final result as string: "positionX:positionY:orientation"

## V3 - More domain logic (effect in domain logic)

Extend the API and it's implementation to handle obstacle detection:

- If a given sequence of commands encounters an obstacle, the rover moves up to the last possible point and aborts the sequence.
- Render the final result as string:
  - normal: "positionX:positionY:orientation"
  - when hit obstacle "O:positionX:positionY:orientation"

## V4 - Focus on infrastructure (compose I/O operations)

Extend the "pure" way of work also to the infrastructural layer

- Read planet data from file into IO (size and obstacles)
- Read rover from file into IO (position and direction)
- Read commands from console into IO
- Implement an application service (encapsulate application logic) that:
  - orchestrate domain, infrastructure, parsing and error handling
  - run the whole app lifted in the IO monad
  - print final rover output to the console if everything is ok
  - handle, safely, any unhandled exception and print them

## V5 - Obtain testability (Port/Adapter architectural style)

Apply Dependency Inversion Principle and Dependency Injection to our application

- Define ports for planet, rover and commands source
- Implement port adapters
- Define injectable application
- Use test doubles in test suite

## V6 - Obtain testability (Elm-ish architectural style)

Use values to obtain a strong and simple separation between domain and infrastructure logic

- implement init, update and test them without infrastructure and mocks
- implement infrastructure and test it with integration tests

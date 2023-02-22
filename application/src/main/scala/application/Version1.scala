package application

/*
    ## V1 - Focus on the center (pure domain logic)

    Develop an API (types and functions) that executes commands:

    - Implement all commands logic.
    - Commands are sent in batch and executed sequentially.
    - The planet grid has a wrapping effect from one edge to another (pacman).
    - For now, ignore obstacle detection logic
 */
object Version1 {

  // TODO 1: Those type alias are only placeholders,
  //  use correct type definitions and feel free to add more...
  type Rover = String
  type Planet = String
  type Command = String

  // TODO 2: Execute all commands and accumulate final rover state
  def executeAll(planet: Planet, rover: Rover, commands: List[Command]): Rover = ???

  // TODO 3: Dispatch one command to a specific function
  def execute(planet: Planet, rover: Rover, command: Command): Rover = ???

  // TODO 4: Change rover orientation
  def turnRight(rover: Rover): Rover = ???

  // TODO 5: Change rover orientation
  def turnLeft(rover: Rover): Rover = ???

  // TODO 6: Change rover position
  def moveForward(planet: Planet, rover: Rover): Rover = ???

  // TODO 7: Change rover position
  def moveBackward(planet: Planet, rover: Rover): Rover = ???

  // NOTE: utility function to get the pacman effect
  def wrap(value: Int, limit: Int, delta: Int): Int =
    (((value + delta) % limit) + limit) % limit
}

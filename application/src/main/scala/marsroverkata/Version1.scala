package marsroverkata

// V1 - Focus on the center (pure domain logic)
object Version1 {

  // NOTE: This module exposes a `wrap` function, an utility to get the pacman effect
  import Pacman._

  // TODO: Implements types and functions. Feel free to add more...

  // TODO: Execute all commands and accumulate final rover state
  def executeAll(planet: Planet, rover: Rover, commands: List[Command]): Rover = ???

  // TODO: Dispatch one command to a specific function
  def execute(planet: Planet, rover: Rover, command: Command): Rover = ???

  // TODO: Dispatch to a specific turn function
  def turn(rover: Rover, turn: Rotation): Rover = ???

  // TODO: Dispatch to a specific move function
  def move(planet: Planet, rover: Rover, move: Movement): Rover = ???

  // TODO: Change rover orientation
  def turnRight(rover: Rover): Rover = ???

  // TODO: Change rover orientation
  def turnLeft(rover: Rover): Rover = ???

  // TODO: Change rover position
  def moveForward(planet: Planet, rover: Rover): Rover = ???

  // TODO: Change rover position
  def moveBackward(planet: Planet, rover: Rover): Rover = ???

  // TODO: Those type alias are only placeholders,
  //  use correct type definitions and feel free to add more...
  type Rover = String
  type Planet = String
  type Command = String
  type Rotation = String
  type Movement = String
}

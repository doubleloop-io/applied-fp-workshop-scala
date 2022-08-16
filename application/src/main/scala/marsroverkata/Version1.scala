package marsroverkata

object Version1 {

  import Pacman._

  // TODO: implements functions and feel free to add more...
  def executeAll(rover: Rover, planet: Planet, commands: List[Command]): Rover = ???
  def execute(rover: Rover, planet: Planet, command: Command): Rover = ???

  def turn(rover: Rover, turn: Rotation): Rover = ???
  def turnRight(rover: Rover): Rover = ???
  def turnLeft(rover: Rover): Rover = ???

  def move(rover: Rover, planet: Planet, move: Movement): Rover = ???
  def moveForward(rover: Rover, planet: Planet): Rover = ???
  def moveBackward(rover: Rover, planet: Planet): Rover = ???

  // TODO: those type alias are only placeholders,
  //  use correct type definitions and feel free to add more...
  type Rover = String
  type Planet = String
  type Orientation = String
  type Position = String
  type Command = String
  type Rotation = String
  type Movement = String
}

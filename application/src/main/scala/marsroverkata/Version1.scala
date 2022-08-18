package marsroverkata

object Version1 {

  import Pacman._

  // TODO: implements functions and feel free to add more...
  def executeAll(planet: Planet, rover: Rover, commands: List[Command]): Rover = ???
  def execute(planet: Planet, rover: Rover, command: Command): Rover = ???

  def turn(rover: Rover, turn: Rotation): Rover = ???
  def turnRight(rover: Rover): Rover = ???
  def turnLeft(rover: Rover): Rover = ???

  def move(planet: Planet, rover: Rover, move: Movement): Rover = ???
  def moveForward(planet: Planet, rover: Rover): Rover = ???
  def moveBackward(planet: Planet, rover: Rover): Rover = ???

  // TODO: those type alias are only placeholders,
  //  use correct type definitions and feel free to add more...
  type Rover = String
  type Planet = String
  type Command = String
  type Rotation = String
  type Movement = String
}

package marsroverkata.answers

object Version1 {

  import marsroverkata.Pacman._
  import Rotation._, Orientation._, Movement._, Command._

  def execute(rover: Rover, planet: Planet, command: Command): Rover =
    command match {
      case Turn(rotation) => turn(rover, rotation)
      case Move(movement) => move(rover, planet, movement)
    }

  def turn(rover: Rover, turn: Rotation): Rover =
    turn match {
      case OnRight => turnRight(rover)
      case OnLeft  => turnLeft(rover)
    }

  def turnRight(rover: Rover): Rover =
    rover.copy(orientation = rover.orientation match {
      case N => E
      case E => S
      case S => W
      case W => N
    })

  def turnLeft(rover: Rover): Rover =
    rover.copy(orientation = rover.orientation match {
      case N => W
      case W => S
      case S => E
      case E => N
    })

  def move(rover: Rover, planet: Planet, move: Movement): Rover =
    move match {
      case Forward  => moveForward(rover, planet)
      case Backward => moveBackward(rover, planet)
    }

  def moveForward(rover: Rover, planet: Planet): Rover =
    rover.copy(position = next(rover, planet, delta(rover.orientation)))

  def moveBackward(rover: Rover, planet: Planet): Rover =
    rover.copy(position = next(rover, planet, delta(opposite(rover.orientation))))

  def opposite(orientation: Orientation): Orientation =
    orientation match {
      case N => S
      case S => N
      case E => W
      case W => E
    }

  def delta(orientation: Orientation): Delta =
    orientation match {
      case N => Delta(0, 1)
      case S => Delta(0, -1)
      case E => Delta(1, 0)
      case W => Delta(-1, 0)
    }

  def next(rover: Rover, planet: Planet, delta: Delta): Position = {
    val position = rover.position
    position.copy(
      x = wrap(position.x, planet.size.width, delta.x),
      y = wrap(position.y, planet.size.height, delta.y)
    )
  }

  case class Delta(x: Int, y: Int)
  case class Position(x: Int, y: Int)
  case class Size(width: Int, height: Int)
  case class Planet(size: Size)
  case class Rover(position: Position, orientation: Orientation)

  enum Command {
    case Move(to: Movement)
    case Turn(on: Rotation)
  }

  enum Movement {
    case Forward, Backward
  }

  enum Rotation {
    case OnRight, OnLeft
  }

  enum Orientation {
    case N, E, W, S
  }
}

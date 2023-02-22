package application.answers

object Version1 {

  import Orientation._, Command._

  def executeAll(planet: Planet, rover: Rover, commands: List[Command]): Rover =
    commands.foldLeft(rover)((prev, cmd) => execute(planet, prev, cmd))

  def execute(planet: Planet, rover: Rover, command: Command): Rover =
    command match {
      case TurnRight    => turnRight(rover)
      case TurnLeft     => turnLeft(rover)
      case MoveForward  => moveForward(planet, rover)
      case MoveBackward => moveBackward(planet, rover)
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

  def moveForward(planet: Planet, rover: Rover): Rover =
    rover.copy(position = next(planet, rover, delta(rover.orientation)))

  def moveBackward(planet: Planet, rover: Rover): Rover =
    rover.copy(position = next(planet, rover, delta(opposite(rover.orientation))))

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

  def next(planet: Planet, rover: Rover, delta: Delta): Position = {
    val position = rover.position
    position.copy(
      x = wrap(position.x, planet.size.width, delta.x),
      y = wrap(position.y, planet.size.height, delta.y)
    )
  }

  def wrap(value: Int, limit: Int, delta: Int): Int =
    (((value + delta) % limit) + limit) % limit

  case class Delta(x: Int, y: Int)
  case class Position(x: Int, y: Int)
  case class Size(width: Int, height: Int)
  case class Planet(size: Size)
  case class Rover(position: Position, orientation: Orientation)

  enum Command {
    case MoveForward
    case MoveBackward
    case TurnRight
    case TurnLeft
  }

  enum Orientation {
    case N, E, W, S
  }
}

package marsroverkata.answers

object Version2 {

  import marsroverkata.Pacman._
  import Rotation._, Orientation._, Movement._, Command._, CommandError._
  import cats.implicits._

  def executeAll(rover: Rover, planet: Planet, commands: List[Command]): Either[CommandError, Rover] =
    commands.foldLeft(rover.asRight)((prev, cmd) => prev.flatMap(execute(_, planet, cmd)))

  def execute(rover: Rover, planet: Planet, command: Command): Either[CommandError, Rover] =
    command match {
      case Turn(rotation) => turn(rover, rotation).asRight
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

  def move(rover: Rover, planet: Planet, move: Movement): Either[CommandError, Rover] =
    move match {
      case Forward  => moveForward(rover, planet)
      case Backward => moveBackward(rover, planet)
    }

  def moveForward(rover: Rover, planet: Planet): Either[CommandError, Rover] =
    next(rover, planet, delta(rover.orientation))
      .map(x => rover.copy(position = x))

  def moveBackward(rover: Rover, planet: Planet): Either[CommandError, Rover] =
    next(rover, planet, delta(opposite(rover.orientation)))
      .map(x => rover.copy(position = x))

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

  def next(rover: Rover, planet: Planet, delta: Delta): Either[CommandError, Position] = {
    val position = rover.position
    val candidate = position.copy(
      x = wrap(position.x, planet.size.width, delta.x),
      y = wrap(position.y, planet.size.height, delta.y)
    )
    val hitObstacle = planet.obstacles.map(_.position).contains(candidate)
    Either.cond(!hitObstacle, candidate, HitObstacle(rover))
  }

  case class Delta(x: Int, y: Int)
  case class Position(x: Int, y: Int)
  case class Size(width: Int, height: Int)
  case class Obstacle(position: Position)
  case class Planet(size: Size, obstacles: List[Obstacle])
  case class Rover(position: Position, orientation: Orientation)

  enum CommandError {
    case HitObstacle(rover: Rover)
  }

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

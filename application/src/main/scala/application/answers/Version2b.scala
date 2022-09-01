package application.answers

// NOTE: with mapN (Applicative)
object Version2b {

  import Rotation._, Orientation._, Movement._, Command._, ParseError._
  import cats.syntax.either._
  import cats.syntax.apply._
  import cats.syntax.bifunctor._
  import cats.syntax.traverse._

  def runMission(inputPlanet: (String, String), inputRover: (String, String), inputCommands: String): Either[ParseError, String] =
    (parsePlanet(inputPlanet), parseRover(inputRover), parseCommands(inputCommands).asRight[ParseError])
      .mapN(executeAll)
      .map(render)

  // PARSING
  def parseCommand(input: Char): Command =
    input.toString.toLowerCase match {
      case "f" => Move(Forward)
      case "b" => Move(Backward)
      case "r" => Turn(OnRight)
      case "l" => Turn(OnLeft)
      case _   => Unknown
    }

  def parseCommands(input: String): List[Command] =
    input.map(parseCommand).toList

  def parsePosition(input: String): Either[ParseError, Position] =
    parseInts(",", input)
      .map(Position.apply)
      .leftMap(_ => InvalidRover(s"invalid position: $input"))

  def parseOrientation(input: String): Either[ParseError, Orientation] =
    input.trim.toLowerCase match {
      case "n" => Right(N)
      case "w" => Right(W)
      case "e" => Right(E)
      case "s" => Right(S)
      case _   => Left(InvalidRover(s"invalid orientation: $input"))
    }

  def parseRover(input: (String, String)): Either[ParseError, Rover] =
    input
      .bimap(parsePosition, parseOrientation)
      .mapN(Rover.apply)

  def parseSize(input: String): Either[ParseError, Size] =
    parseInts("x", input)
      .map(Size.apply)
      .leftMap(_ => InvalidPlanet(s"invalid size: $input"))

  def parseObstacle(input: String): Either[ParseError, Obstacle] =
    parsePosition(input)
      .map(Obstacle.apply)
      .leftMap(_ => InvalidPlanet(s"invalid obstacle: $input"))

  def parseObstacles(input: String): Either[ParseError, List[Obstacle]] =
    input.split(" ").toList.traverse(parseObstacle)

  def parsePlanet(input: (String, String)): Either[ParseError, Planet] =
    input
      .bimap(parseSize, parseObstacles)
      .mapN(Planet.apply)

  def parseInts(separator: String, input: String): Either[Throwable, (Int, Int)] =
    Either.catchNonFatal {
      val parts = input.split(separator).toList
      (parts(0).trim.toInt, parts(1).trim.toInt)
    }

  // RENDERING
  def render(rover: Rover): String =
    s"${rover.position.x}:${rover.position.y}:${rover.orientation}"

  // DOMAIN
  def executeAll(planet: Planet, rover: Rover, commands: List[Command]): Rover =
    commands.foldLeft(rover)((prev, cmd) => execute(planet, prev, cmd))

  def execute(planet: Planet, rover: Rover, command: Command): Rover =
    command match {
      case Turn(rotation) => turn(rover, rotation)
      case Move(movement) => move(planet, rover, movement)
      case Unknown        => rover
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

  def move(planet: Planet, rover: Rover, move: Movement): Rover =
    move match {
      case Forward  => moveForward(planet, rover)
      case Backward => moveBackward(planet, rover)
    }

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

  // TYPES
  case class Delta(x: Int, y: Int)
  case class Position(x: Int, y: Int)
  case class Size(width: Int, height: Int)
  case class Obstacle(position: Position)
  case class Planet(size: Size, obstacles: List[Obstacle])
  case class Rover(position: Position, orientation: Orientation)

  enum ParseError {
    case InvalidPlanet(message: String)
    case InvalidRover(message: String)
  }

  enum Command {
    case Move(to: Movement)
    case Turn(on: Rotation)
    case Unknown
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

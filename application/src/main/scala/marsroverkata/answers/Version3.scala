package marsroverkata.answers

object Version3 {

  import marsroverkata.Pacman._
  import Rotation._, Orientation._, Movement._, Command._, ParseError._
  import cats.implicits._

  def runMission(inputPlanet: (String, String), inputRover: (String, String), inputCommands: String): Either[ParseError, String] =
    for {
      planet <- parsePlanet(inputPlanet)
      rover <- parseRover(inputRover)
      commands = parseCommands(inputCommands)
      result = executeAll(rover, planet, commands)
    } yield render(result)

  // --RENDERING
  def render(rover: Rover): String =
    s"${rover.position.x}:${rover.position.y}:${rover.orientation}"

  // --PARSING
  def parseCommands(input: String): List[Command] =
    input.map(parseCommand).toList

  def parseCommand(input: Char): Command =
    input.toString.toLowerCase match {
      case "f" => Move(Forward)
      case "b" => Move(Backward)
      case "r" => Turn(OnRight)
      case "l" => Turn(OnLeft)
      case _   => Unknown
    }

  def parseRover(input: (String, String)): Either[ParseError, Rover] = {
    val (inputPosition, inputOrientation) = input
    for {
      position <- parsePosition(inputPosition)
      orientation <- parseOrientation(inputOrientation)
    } yield Rover(position, orientation)
  }

  def parseOrientation(input: String): Either[ParseError, Orientation] =
    input.trim.toLowerCase match {
      case "n" => Right(N)
      case "w" => Right(W)
      case "e" => Right(E)
      case "s" => Right(S)
      case _   => Left(InvalidRover(s"invalid orientation: $input"))
    }

  def parsePosition(input: String): Either[ParseError, Position] =
    parseTuple(",", input)
      .map(Position.apply)
      .leftMap(_ => InvalidRover(s"invalid position: $input"))

  def parseSize(input: String): Either[ParseError, Size] =
    parseTuple("x", input)
      .map(Size.apply)
      .leftMap(_ => InvalidPlanet(s"invalid size: $input"))

  def parseObstacle(input: String): Either[ParseError, Obstacle] =
    parsePosition(input)
      .map(Obstacle.apply)
      .leftMap(_ => InvalidPlanet(s"invalid obstacle: $input"))

  def parseObstacles(raw: String): Either[ParseError, List[Obstacle]] =
    raw.split(" ").toList.traverse(parseObstacle)

  def parsePlanet(raw: (String, String)): Either[ParseError, Planet] = {
    val (inputSize, inputObstacles) = raw
    for {
      size <- parseSize(inputSize)
      obstacles <- parseObstacles(inputObstacles)
    } yield Planet(size, obstacles)
  }

  def parseTuple(separator: String, input: String): Either[Throwable, (Int, Int)] =
    Either.catchNonFatal {
      val parts = input.split(separator).toList
      (parts(0).trim.toInt, parts(1).trim.toInt)
    }

  // --DOMAIN
  def executeAll(rover: Rover, planet: Planet, commands: List[Command]): Rover =
    commands.foldLeft(rover)((prev, cmd) => execute(prev, planet, cmd))

  def execute(rover: Rover, planet: Planet, command: Command): Rover =
    command match {
      case Turn(rotation) => turn(rover, rotation)
      case Move(movement) => move(rover, planet, movement)
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

  // --TYPES
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

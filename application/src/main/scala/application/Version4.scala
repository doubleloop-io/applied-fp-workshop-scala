package application

/*
    ## V4 - Focus on infrastructure (compose I/O operations)

    Extend the pure way of work also to the infrastructural layer

    - Read planet data from file into IO (size and obstacles)
    - Read rover from file into IO (position and orientation)
    - Ask and read commands from console into IO
    - Implement an entrypoint that:
      - orchestrate domain, infrastructure, parsing and error handling
      - run the whole app lifted in the IO monad
      - print final rover output to the console if everything is ok
      - recover from any unhandled error and print it
 */
object Version4 {

  import application.Console._
  import Orientation._, Command._, ParseError._
  import cats.syntax.either._
  import cats.syntax.traverse._
  import cats.syntax.applicativeError._
  import cats.effect.IO

  // TODO 7:
  //  - combine loadPlanet, loadRover, loadCommands
  //  - call runMission
  //  - w/ writeError log any unhandled error in IO
  def createApplication(planetFile: String, roverFile: String): IO[Unit] = ???

  def runMission(planet: Planet, rover: Rover, commands: List[Command]): IO[Unit] =
    executeAll(planet, rover, commands)
      .fold(writeObstacleDetected, writeSequenceCompleted)

  // INFRASTRUCTURE

  // NOTE: utility function to convert Either in IO
  def eitherToIO[A](value: Either[ParseError, A]): IO[A] =
    IO.fromEither(value.leftMap(e => new RuntimeException(renderError(e))))

  // TODO 1: load file as pair of strings, parse and then convert Either to IO
  def loadPlanet(file: String): IO[Planet] = ???

  // TODO 2: load file as pair of strings, parse and then convert Either to IO
  def loadRover(file: String): IO[Rover] = ???

  // TODO 3: ask for commands string, parse and then convert Either to IO
  def loadCommands(): IO[List[Command]] = ???

  // TODO 4: log rendered result w/ Info level
  def writeSequenceCompleted(rover: Rover): IO[Unit] = ???

  // TODO 5: log rendered result w/ Info level
  def writeObstacleDetected(rover: ObstacleDetected): IO[Unit] = ???

  // TODO 6: log rendered result w/ Error level
  def writeError(error: Throwable): IO[Unit] = ???

  // PARSING
  def parseCommand(input: Char): Command =
    input.toString.toLowerCase match {
      case "f" => MoveForward
      case "b" => MoveBackward
      case "r" => TurnRight
      case "l" => TurnLeft
      case _   => Unknown
    }

  def parseCommands(input: String): List[Command] =
    input.map(parseCommand).toList

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
    parseIntTuple(",", input)
      .map(Position.apply)
      .leftMap(_ => InvalidRover(s"invalid position: $input"))

  def parseSize(input: String): Either[ParseError, Size] =
    parseIntTuple("x", input)
      .map(Size.apply)
      .leftMap(_ => InvalidPlanet(s"invalid size: $input"))

  def parseObstacle(input: String): Either[ParseError, Obstacle] =
    parsePosition(input)
      .map(Obstacle.apply)
      .leftMap(_ => InvalidPlanet(s"invalid obstacle: $input"))

  def parseObstacles(input: String): Either[ParseError, List[Obstacle]] =
    input.split(" ").toList.traverse(parseObstacle)

  def parsePlanet(input: (String, String)): Either[ParseError, Planet] = {
    val (inputSize, inputObstacles) = input
    for {
      size <- parseSize(inputSize)
      obstacles <- parseObstacles(inputObstacles)
    } yield Planet(size, obstacles)
  }

  def parseIntTuple(separator: String, input: String): Either[Throwable, (Int, Int)] =
    Either.catchNonFatal {
      val parts = input.split(separator).toList
      (parts(0).trim.toInt, parts(1).trim.toInt)
    }

  // RENDERING
  def renderComplete(rover: Rover): String =
    s"${rover.position.x}:${rover.position.y}:${rover.orientation}"

  def renderObstacle(hit: ObstacleDetected): String =
    s"O:${renderComplete(hit.rover)}"

  // NOTE: produce a string from an error
  def renderError(error: ParseError): String =
    error match {
      case InvalidPlanet(message) => s"Planet parsing: $message"
      case InvalidRover(message)  => s"Rover parsing: $message"
    }

  // DOMAIN
  def executeAll(planet: Planet, rover: Rover, commands: List[Command]): Either[ObstacleDetected, Rover] =
    commands.foldLeft(rover.asRight)((prev, cmd) => prev.flatMap(execute(planet, _, cmd)))

  def execute(planet: Planet, rover: Rover, command: Command): Either[ObstacleDetected, Rover] =
    command match {
      case TurnRight    => turnRight(rover).asRight
      case TurnLeft     => turnLeft(rover).asRight
      case MoveForward  => moveForward(planet, rover)
      case MoveBackward => moveBackward(planet, rover)
      case Unknown      => rover.asRight
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

  def moveForward(planet: Planet, rover: Rover): Either[ObstacleDetected, Rover] =
    next(planet, rover, delta(rover.orientation))
      .map(x => rover.copy(position = x))

  def moveBackward(planet: Planet, rover: Rover): Either[ObstacleDetected, Rover] =
    next(planet, rover, delta(opposite(rover.orientation)))
      .map(x => rover.copy(position = x))

  def next(planet: Planet, rover: Rover, delta: Delta): Either[ObstacleDetected, Position] = {
    val position = rover.position
    val candidate = position.copy(
      x = wrap(position.x, planet.size.width, delta.x),
      y = wrap(position.y, planet.size.height, delta.y)
    )
    val hitObstacle = planet.obstacles.map(_.position).contains(candidate)
    Either.cond(!hitObstacle, candidate, ObstacleDetected(rover))
  }

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

  def wrap(value: Int, limit: Int, delta: Int): Int =
    (((value + delta) % limit) + limit) % limit

  // TYPES
  case class Delta(x: Int, y: Int)
  case class Position(x: Int, y: Int)
  case class Size(width: Int, height: Int)
  case class Obstacle(position: Position)
  case class Planet(size: Size, obstacles: List[Obstacle])
  case class Rover(position: Position, orientation: Orientation)
  case class ObstacleDetected(rover: Rover)

  enum ParseError {
    case InvalidPlanet(message: String)
    case InvalidRover(message: String)
  }

  enum Command {
    case MoveForward
    case MoveBackward
    case TurnRight
    case TurnLeft
    case Unknown
  }

  enum Orientation {
    case N, E, W, S
  }
}

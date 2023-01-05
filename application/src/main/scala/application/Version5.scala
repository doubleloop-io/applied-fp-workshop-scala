package application

/*
    ## V5 - Testability via injection (Port/Adapter architectural style)

    Apply Dependency Inversion Principle and Dependency Injection to our application
    - Look to the Ports for read planet, rover and commands
    - Implement port adapters
    - Define injectable application
    - Use test doubles in test suite
 */

object Version5 {

  import application.Infra._
  import Rotation._, Orientation._, Movement._, Command._, ParseError._
  import cats.syntax.either._
  import cats.syntax.traverse._
  import cats.syntax.applicativeError._
  import cats.effect.IO

  // NOTE: defined port contracts
  trait PlanetReader {
    def read(): IO[Planet]
  }
  trait RoverReader {
    def read(): IO[Rover]
  }
  trait CommandsReader {
    def read(): IO[List[Command]]
  }
  trait DisplayWriter {
    def sequenceCompleted(rover: Rover): IO[Unit]
    def obstacleDetected(rover: ObstacleDetected): IO[Unit]
    def error(error: Throwable): IO[Unit]
  }

  // NOTE: Wired entry point
  def createApplication(planetFile: String, roverFile: String): IO[Unit] = {
    // TODO 1: calls relative load function
    val filePlanetReader = new PlanetReader {
      def read(): IO[Planet] = ???
    }

    // TODO 2: calls relative load function
    val fileRoverReader = new RoverReader {
      def read(): IO[Rover] = ???
    }

    // TODO 3: calls relative ask function
    val consoleCommandsReader = new CommandsReader {
      def read(): IO[List[Command]] = ???
    }

    // TODO 4: calls relative write* functions
    val loggerDisplayWriter = new DisplayWriter {
      def sequenceCompleted(rover: Rover): IO[Unit] = ???
      def obstacleDetected(rover: ObstacleDetected): IO[Unit] = ???
      def error(error: Throwable): IO[Unit] = ???
    }

    // TODO 5: call injectable createApplication
    ???
  }

  // NOTE: injectable entry point (use normal function parameters for Dependency Injection)
  def createApplication(planetReader: PlanetReader, roverReader: RoverReader, commandsReader: CommandsReader, display: DisplayWriter): IO[Unit] = {
    val runResult =
      for {
        planet <- planetReader.read()
        rover <- roverReader.read()
        commands <- commandsReader.read()
        _ <- runMission(display, planet, rover, commands)
      } yield ()

    runResult.recoverWith(display.error(_))
  }

  // NOTE: is used display to print obstacle/completed cases
  def runMission(display: DisplayWriter, planet: Planet, rover: Rover, commands: List[Command]): IO[Unit] =
    executeAll(planet, rover, commands)
      .fold(display.obstacleDetected, display.sequenceCompleted)

  // INFRASTRUCTURE
  def eitherToIO[A](value: Either[ParseError, A]): IO[A] =
    IO.fromEither(value.leftMap(e => new RuntimeException(renderError(e))))

  def loadPlanet(file: String): IO[Planet] =
    loadTuple(file)
      .map(parsePlanet)
      .flatMap(eitherToIO)

  def loadRover(file: String): IO[Rover] =
    loadTuple(file)
      .map(parseRover)
      .flatMap(eitherToIO)

  def loadCommands(): IO[List[Command]] =
    ask("Waiting commands...")
      .map(parseCommands)

  def writeSequenceCompleted(rover: Rover): IO[Unit] =
    logInfo(renderComplete(rover))

  def writeObstacleDetected(rover: ObstacleDetected): IO[Unit] =
    logInfo(renderObstacle(rover))

  def writeError(error: Throwable): IO[Unit] =
    logError(error.getMessage)

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
    parseInts(",", input)
      .map(Position.apply)
      .leftMap(_ => InvalidRover(s"invalid position: $input"))

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

  def parsePlanet(input: (String, String)): Either[ParseError, Planet] = {
    val (inputSize, inputObstacles) = input
    for {
      size <- parseSize(inputSize)
      obstacles <- parseObstacles(inputObstacles)
    } yield Planet(size, obstacles)
  }

  def parseInts(separator: String, input: String): Either[Throwable, (Int, Int)] =
    Either.catchNonFatal {
      val parts = input.split(separator).toList
      (parts(0).trim.toInt, parts(1).trim.toInt)
    }

  // RENDERING
  def renderError(error: ParseError): String =
    error match {
      case InvalidPlanet(message) => s"Planet parsing: $message"
      case InvalidRover(message)  => s"Rover parsing: $message"
    }

  def renderComplete(rover: Rover): String =
    s"${rover.position.x}:${rover.position.y}:${rover.orientation}"

  def renderObstacle(hit: ObstacleDetected): String =
    s"O:${renderComplete(hit.rover)}"

  // DOMAIN
  def executeAll(planet: Planet, rover: Rover, commands: List[Command]): Either[ObstacleDetected, Rover] =
    commands.foldLeft(rover.asRight)((prev, cmd) => prev.flatMap(execute(planet, _, cmd)))

  def execute(planet: Planet, rover: Rover, command: Command): Either[ObstacleDetected, Rover] =
    command match {
      case Turn(rotation) => turn(rover, rotation).asRight
      case Move(movement) => move(planet, rover, movement)
      case Unknown        => rover.asRight
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

  def move(planet: Planet, rover: Rover, move: Movement): Either[ObstacleDetected, Rover] =
    move match {
      case Forward  => moveForward(planet, rover)
      case Backward => moveBackward(planet, rover)
    }

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

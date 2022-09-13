package application

/*
    ## V3 - More domain logic (handle obstacles w/ effects)

    Extend the API and it's implementation to handle obstacle detection:

    - Implement obstacle detection before each move to a new square.
    - If the rover encounters an obstacle, rest in the same position and aborts the sequence.
    - Render the final result as string:
      - sequence completed: "positionX:positionY:orientation"
      - obstacle detected: "O:positionX:positionY:orientation"
 */
object Version3 {

  import Rotation._, Orientation._, Movement._, Command._, ParseError._
  import cats.syntax.either._
  import cats.syntax.traverse._

  // TODO 7: remove Either by calling the correct rendering (renderComplete or renderObstacle)
  // previous implementation
  //   yield render(result)
  def runMission(inputPlanet: (String, String), inputRover: (String, String), inputCommands: String): Either[ParseError, String] =
    for {
      planet <- parsePlanet(inputPlanet)
      rover <- parseRover(inputRover)
      commands = parseCommands(inputCommands)
      result = executeAll(planet, rover, commands)
    } yield ???

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
  def renderComplete(rover: Rover): String =
    s"${rover.position.x}:${rover.position.y}:${rover.orientation}"

  def renderObstacle(hit: ObstacleDetected): String =
    s"O:${renderComplete(hit.rover)}"

  // DOMAIN
  // TODO 6: combine prev and current execution result in order to propagate Either
  // previous implementation:
  //   commands.foldLeft(rover)((prev, cmd) => execute(planet, prev, cmd))
  def executeAll(planet: Planet, rover: Rover, commands: List[Command]): Either[ObstacleDetected, Rover] = {
    val init = rover.asRight[ObstacleDetected]
    commands.foldLeft(init)((prev, cmd) => ???)
  }

  // TODO 5: fix in order to propagate Either
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

  // TODO 5: fix in order to propagate Either
  def move(planet: Planet, rover: Rover, move: Movement): Rover =
    move match {
      case Forward  => moveForward(planet, rover)
      case Backward => moveBackward(planet, rover)
    }

  // TODO 4: fix in order to propagate Either
  def moveForward(planet: Planet, rover: Rover): Rover =
    rover.copy(position = next(planet, rover, delta(rover.orientation)))

  // TODO 3: fix in order to propagate Either
  def moveBackward(planet: Planet, rover: Rover): Rover =
    rover.copy(position = next(planet, rover, delta(opposite(rover.orientation))))

  // TODO 2: change return type (follow result type)
  def next(planet: Planet, rover: Rover, delta: Delta): Position = {
    val position = rover.position
    val candidate = position.copy(
      x = wrap(position.x, planet.size.width, delta.x),
      y = wrap(position.y, planet.size.height, delta.y)
    )

    val hitObstacle = planet.obstacles.map(_.position).contains(candidate)
    val result = Either.cond(!hitObstacle, candidate, ObstacleDetected(rover))

    // TODO 1: on the next line, replace candidate with result
    candidate
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

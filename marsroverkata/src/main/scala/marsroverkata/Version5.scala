package marsroverkata

object Version5 {

  import cats.effect._
  import cats.implicits._

  import scala.Console._
  import scala.io._
  import scala.util._

  case class AppError(err: Error) extends RuntimeException(err.toString)

  def createApplication(planetFile: String, roverFile: String): IO[Unit] = ???

  def handleResult(result: Either[Throwable, String]): IO[Unit] =
    result match {
      case Right(value) => logInfo(value)
      case Left(t)      => logError(t.getMessage)
    }

  def logInfo(message: String): IO[Unit] =
    puts(s"${GREEN}OK: $message$RESET")

  def logError(message: String): IO[Unit] =
    puts(s"${RED}ERROR: $message$RESET")

  def loadPlanetData(file: String): IO[(String, String)] = loadTupled(file)
  def loadRoverData(file: String): IO[(String, String)]  = loadTupled(file)

  def loadTupled(file: String): IO[(String, String)] =
    Resource
      .fromAutoCloseable(IO(Source.fromURL(getClass.getClassLoader.getResource(file))))
      .use { source =>
        IO(source.getLines().toArray match {
          case Array(first, second) => (first, second)
          case _                    => throw new RuntimeException("invalid content")
        })
      }

  def puts(message: String): IO[Unit] = IO(println(message))
  def reads(): IO[String]             = IO(scala.io.StdIn.readLine())
  def ask(question: String): IO[String] =
    puts(question) *> reads()

  def askCommands(): IO[String] =
    ask("Waiting commands...")

  def run(planet: (String, String), rover: (String, String), commands: String): Either[Error, String] =
    init(planet, rover)
      .map(execute(_, parseCommands(commands)))
      .map(_.bimap(_.rover, _.rover).fold(renderHit, render))

  def parseTuple[A](separator: String, raw: String, ctor: (Int, Int) => A): Try[A] =
    Try {
      val parts = raw.split(separator)
      (parts(0).trim.toInt, parts(1).trim.toInt)
    }.map(ctor.tupled(_))

  def parsePlanet(raw: (String, String)): Either[Error, Planet] =
    raw
      .bimap(parseSize, parseObstacles)
      .mapN(Planet.apply)

  def parseSize(raw: String): Either[Error, Size] =
    parseTuple("x", raw, Size.apply).toEither
      .leftMap(_ => InvalidPlanet(raw, "InvalidSize"))

  def parseRover(raw: (String, String)): Either[Error, Rover] =
    raw
      .bimap(parsePosition, parseDirection)
      .mapN(Rover.apply)

  def parsePosition(raw: String): Either[Error, Position] =
    parseTuple(",", raw, Position.apply).toEither
      .leftMap(_ => InvalidRover(raw, "InvalidPosition"))

  def parseDirection(raw: String): Either[Error, Direction] =
    Try {
      raw.trim.toLowerCase match {
        case "n" => N
        case "w" => W
        case "e" => E
        case "s" => S
      }
    }.toEither
      .leftMap(_ => InvalidRover(raw, "InvalidDirection"))

  def parseCommands(raw: String): List[Command] =
    raw.map(parseCommand).toList

  def parseCommand(raw: Char): Command =
    raw.toString.toLowerCase match {
      case "f" => Move(Forward)
      case "b" => Move(Backward)
      case "r" => Turn(OnRight)
      case "l" => Turn(OnLeft)
      case _   => Unknown
    }

  def parseObstacles(raw: String): Either[Error, List[Obstacle]] =
    raw.split(" ").toList.traverse(parseObstacle)

  def parseObstacle(raw: String): Either[Error, Obstacle] =
    parsePosition(raw)
      .map(Obstacle.apply)
      .leftMap(ex => InvalidObstacle(raw, ex.getClass.getSimpleName))

  def init(planet: (String, String), rover: (String, String)): Either[Error, Mission] =
    (
      parsePlanet(planet),
      parseRover(rover)
      ).mapN(Mission.apply)

  def render(rover: Rover): String =
    s"${rover.position.x}:${rover.position.y}:${rover.direction}"

  def renderHit(rover: Rover): String =
    s"O:${rover.position.x}:${rover.position.y}:${rover.direction}"

  def execute(mission: Mission, commands: List[Command]): Either[Mission, Mission] =
    commands.foldLeftM(mission)(execute)

  def execute(mission: Mission, command: Command): Either[Mission, Mission] =
    (command match {
      case Turn(tt) => turn(mission.rover, tt).some
      case Move(mt) => move(mission.rover, mission.planet, mt)
      case Unknown  => noOp(mission.rover).some
    }).map(r => mission.copy(rover = r)).toRight(mission)

  val noOp: Rover => Rover = identity

  def turn(rover: Rover, turn: TurnType): Rover =
    rover.copy(direction = turn match {
      case OnRight => turnRight(rover.direction)
      case OnLeft  => turnLeft(rover.direction)
    })

  def turnRight(direction: Direction): Direction =
    direction match {
      case N => E
      case E => S
      case S => W
      case W => N
    }

  def turnLeft(direction: Direction): Direction =
    direction match {
      case N => W
      case W => S
      case S => E
      case E => N
    }

  def move(rover: Rover, planet: Planet, move: MoveType): Option[Rover] =
    (move match {
      case Forward  => forward(rover, planet)
      case Backward => backward(rover, planet)
    }).map(p => rover.copy(position = p))

  def forward(rover: Rover, planet: Planet): Option[Position] =
    next(rover.position, planet, delta(rover.direction))

  def backward(rover: Rover, planet: Planet): Option[Position] =
    next(rover.position, planet, delta(opposite(rover.direction)))

  def opposite(direction: Direction): Direction =
    direction match {
      case N => S
      case S => N
      case E => W
      case W => E
    }

  def delta(direction: Direction): Delta =
    direction match {
      case N => Delta(0, 1)
      case S => Delta(0, -1)
      case E => Delta(1, 0)
      case W => Delta(-1, 0)
    }

  def wrap(axis: Int, size: Int, delta: Int): Int =
    (((axis + delta) % size) + size) % size

  def next(position: Position, planet: Planet, delta: Delta): Option[Position] = {
    val candidate = Position(
      wrap(position.x, planet.size.x, delta.x),
      wrap(position.y, planet.size.y, delta.y)
    )
    if (planet.obstacles.map(_.position).contains(candidate)) None
    else candidate.some
  }

  sealed trait Error
  case class InvalidPlanet(value: String, error: String)   extends Error
  case class InvalidRover(value: String, error: String)    extends Error
  case class InvalidObstacle(value: String, error: String) extends Error

  case class Delta(x: Int, y: Int)
  case class Position(x: Int, y: Int)
  case class Size(x: Int, y: Int)
  case class Obstacle(position: Position)
  case class Planet(size: Size, obstacles: List[Obstacle])
  case class Rover(position: Position, direction: Direction)
  case class Mission(planet: Planet, rover: Rover)

  sealed trait Command
  case class Move(direction: MoveType) extends Command
  case class Turn(direction: TurnType) extends Command
  case object Unknown                  extends Command

  sealed trait MoveType
  case object Forward  extends MoveType
  case object Backward extends MoveType

  sealed trait TurnType
  case object OnRight extends TurnType
  case object OnLeft  extends TurnType

  sealed trait Direction
  case object N extends Direction
  case object E extends Direction
  case object W extends Direction
  case object S extends Direction
}

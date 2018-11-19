package marsroverkata.answers

import scala.io.StdIn._

import cats.effect.IO

class Game {

  def run(): Unit =
    runIO().unsafeRunSync()

  def runIO(): IO[Unit] =
    welcome()
      .flatMap(_ => readPlanet())
      .flatMap(p => readObstacles().map(setObstacles(p, _)))
      .flatMap(p => readPosition().map(Rover.apply(p, _)))
      .flatMap(r => readCommands().map(handle(r, _)))
      .flatMap(r => info(r))

  def info(r: Rover): IO[Unit] =
    puts(s"${r.direction}:${r.position.x},${r.position.y}")

  def welcome(): IO[Unit] =
    puts("Welcome to the Mars Rover Kata!")

  def readPlanet(): IO[Planet] =
    ask("What is the size of the planet?")
      .map(parsePlanet)

  def parsePlanet(raw: String): Planet = {
    val tokens = raw.split("x")
    Planet(tokens(0).toInt, tokens(1).toInt)
  }

  def readObstacles(): IO[List[Obstacle]] =
    ask("Where are the obstacles?")
      .map(parseObstacles)

  def parseObstacles(raw: String): List[Obstacle] =
    raw.split("/").toList.map(parseObstacle)

  def parseObstacle(raw: String): Obstacle =
    Obstacle(parsePosition(raw))

  def setObstacles(p: Planet, os: List[Obstacle]): Planet =
    p.copy(obstacles = os)

  def readPosition(): IO[Position] =
    ask("What is the position of the rover?")
      .map(parsePosition)

  def parsePosition(raw: String): Position = {
    val tokens = raw.split(",")
    Position(tokens(0).toInt, tokens(1).toInt)
  }

  def readCommands(): IO[List[Command]] =
    ask("Waiting commands...")
      .map(parseCommands)

  def parseCommands(raw: String): List[Command] =
    raw.map(parseCommand).toList

  def parseCommand(raw: Char): Command =
    raw.toString match {
      case "f" => MoveForward
      case "b" => MoveBackward
      case "r" => TurnRight
      case "l" => TurnLeft
      case _   => Unknown
    }

  def handle(r: Rover, cs: List[Command]): Rover =
    cs match {
      case c :: rest => handleCommand(r, c).fold(handle(r, Nil))(handle(_, rest))
      case Nil       => r
    }

  def handleCommand(r: Rover, c: Command): Option[Rover] =
    c match {
      case MoveForward  => forward(r)
      case MoveBackward => backward(r)
      case TurnRight    => pure(turnRight(r))
      case TurnLeft     => pure(turnLeft(r))
      case Unknown      => pure(r)
    }

  def pure[A](value: A): Option[A] =
    Some(value)

  def turnRight(r: Rover): Rover =
    r.direction match {
      case N => r.copy(direction = E)
      case E => r.copy(direction = S)
      case S => r.copy(direction = W)
      case W => r.copy(direction = N)
    }

  def turnLeft(r: Rover): Rover =
    r.direction match {
      case N => r.copy(direction = W)
      case W => r.copy(direction = S)
      case S => r.copy(direction = E)
      case E => r.copy(direction = N)
    }

  def forward(r: Rover): Option[Rover] =
    move(r.direction, r.planet, r.position)
      .map(p => r.copy(position = p))

  def backward(r: Rover): Option[Rover] =
    move(opposite(r.direction), r.planet, r.position)
      .map(p => r.copy(position = p))

  def move(direction: Direction, planet: Planet, current: Position): Option[Position] =
    direction match {
      case N => nextUp(planet, current)
      case E => nextRight(planet, current)
      case W => nextLeft(planet, current)
      case S => nextDown(planet, current)
    }

  def opposite(direction: Direction): Direction =
    direction match {
      case N => S
      case S => N
      case E => W
      case W => E
    }

  def nextUp(planet: Planet, current: Position): Option[Position] = {
    val nextX = (current.x - 1 + planet.xSize) % planet.xSize
    setX(planet.obstacles, current, nextX)
  }

  def nextDown(planet: Planet, current: Position): Option[Position] = {
    val nextX = (current.x + 1) % planet.xSize
    setX(planet.obstacles, current, nextX)
  }

  def nextRight(planet: Planet, current: Position): Option[Position] = {
    val nextY = (current.y + 1) % planet.ySize
    setY(planet.obstacles, current, nextY)
  }

  def nextLeft(planet: Planet, current: Position): Option[Position] = {
    val nextY = (current.y - 1 + planet.ySize) % planet.ySize
    setY(planet.obstacles, current, nextY)
  }

  def hit(os: List[Obstacle], p: Position): Option[Unit] =
    if (os.map(_.position).contains(p)) None
    else Some(())

  def setX(os: List[Obstacle], p: Position, nextX: Int): Option[Position] = {
    val candidate = p.copy(x = nextX)
    hit(os, candidate).map(_ => candidate)
  }

  def setY(os: List[Obstacle], p: Position, nextY: Int): Option[Position] = {
    val candidate = p.copy(y = nextY)
    hit(os, candidate).map(_ => candidate)
  }

  def ask(question: String): IO[String] =
    puts(question)
      .flatMap(_ => reads())

  def puts(s: String): IO[Unit] = IO {
    println(s)
  }

  def reads(): IO[String] = IO {
    readLine()
  }

  case class Position(x: Int, y: Int)
  case class Planet(xSize: Int, ySize: Int, obstacles: List[Obstacle] = List())
  case class Obstacle(position: Position)
  case class Rover(planet: Planet, position: Position, direction: Direction = N)

  sealed trait Command
  case object MoveForward  extends Command
  case object MoveBackward extends Command
  case object TurnRight    extends Command
  case object TurnLeft     extends Command
  case object Unknown      extends Command

  sealed trait Direction
  case object N extends Direction
  case object E extends Direction
  case object W extends Direction
  case object S extends Direction
}

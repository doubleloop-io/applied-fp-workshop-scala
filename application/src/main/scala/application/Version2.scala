package application

/*
    ## V2 - Focus on boundaries (from primitive to domain types and viceversa)

    Our domain is declared with rich types but inputs/outputs are should be primitive types

    - Write a parser for input planet data (size, obstacles)
    - Write a parser for input rover data (position, orientation)
    - Write a parser for input commands (skip unknown chars)
    - Render the final result as string: "positionX:positionY:orientation"
 */
object Version2 {

  import Rotation._, Orientation._, Movement._, Command._, ParseError._
  import cats.syntax.either._
  import cats.syntax.traverse._

  // TODO 11:
  //  - combine planet, rover and commands parsers
  //  - call executeAll
  //  - render the mission result
  def runMission(inputPlanet: (String, String), inputRover: (String, String), inputCommands: String): Either[ParseError, String] = ???

  // PARSING

  // TODO 1: implements (input = 'F')
  def parseCommand(input: Char): Command = ???

  // TODO 2: combine parseCommand (input = "BFFLLR")
  def parseCommands(input: String): List[Command] = ???

  // TODO 3: combine parseInts (input = "3,2"), error should be InvalidRover
  def parsePosition(input: String): Either[ParseError, Position] = ???

  // TODO 4: implements (input = "N"), error should be InvalidRover
  def parseOrientation(input: String): Either[ParseError, Orientation] = ???

  // TODO 5: combine parsePosition and parseOrientation, error should be InvalidRover
  def parseRover(input: (String, String)): Either[ParseError, Rover] = ???

  // TODO 6: combine parseInts (input = "5x5"), error should be InvalidPlanet
  def parseSize(input: String): Either[ParseError, Size] = ???

  // TODO 7: combine parsePosition (input = "3,2"), error should be InvalidPlanet
  def parseObstacle(input: String): Either[ParseError, Obstacle] = ???

  // TODO 8: combine parseObstacle (input = "3,2 7,0 2,9"), error should be InvalidPlanet
  def parseObstacles(input: String): Either[ParseError, List[Obstacle]] = ???

  // TODO 9: combine parseSize and parseObstacles, error should be InvalidPlanet
  def parsePlanet(input: (String, String)): Either[ParseError, Planet] = ???

  // NOTE: utility function to split a string in a pair of ints
  def parseInts(separator: String, input: String): Either[Throwable, (Int, Int)] =
    Either.catchNonFatal {
      val parts = input.split(separator).toList
      (parts(0).trim.toInt, parts(1).trim.toInt)
    }

  // RENDERING

  // TODO 10: implements render, combine the mission result string
  def render(rover: Rover): String = ???

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

  def next(planet: Planet, rover: Rover, delta: Delta): Position = {
    val position = rover.position
    position.copy(
      x = wrap(position.x, planet.size.width, delta.x),
      y = wrap(position.y, planet.size.height, delta.y)
    )
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

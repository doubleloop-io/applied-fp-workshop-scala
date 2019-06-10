package marsroverkata.answers

import scala.io.StdIn._

import cats.effect.IO

object Version1 {
  class Game {

    def example1(): Unit = {
      val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
      val commands = List(Turn(OnRight), Move(Forward))
      val updated  = execute(mission, commands)
      println(updated)
      assert(updated == Mission(Planet(Size(5, 4)), Rover(Position(0, 1), E)))
    }

    def example2(): Unit = {
      val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
      val commands = List(Move(Forward), Move(Forward), Turn(OnLeft), Move(Backward))
      val updated  = execute(mission, commands)
      println(updated)
      assert(updated == Mission(Planet(Size(5, 4)), Rover(Position(3, 1), W)))
    }

    def execute(mission: Mission, commands: List[Command]): Mission =
      commands.foldLeft(mission)(execute)

    def execute(mission: Mission, command: Command): Mission =
      mission.copy(rover = command match {
        case Turn(tt) => turn(mission.rover, tt)
        case Move(mt) => move(mission.rover, mission.planet, mt)
      })

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

    def move(rover: Rover, planet: Planet, move: MoveType): Rover =
      rover.copy(position = move match {
        case Forward  => forward(rover, planet)
        case Backward => backward(rover, planet)
      })

    def forward(rover: Rover, planet: Planet): Position =
      next(rover.position, rover.direction, planet)

    def backward(rover: Rover, planet: Planet): Position =
      next(rover.position, opposite(rover.direction), planet)

    def opposite(direction: Direction): Direction =
      direction match {
        case N => S
        case S => N
        case E => W
        case W => E
      }

    def next(position: Position, direction: Direction, planet: Planet): Position =
      direction match {
        case N => nextUp(position, planet)
        case E => nextRight(position, planet)
        case W => nextLeft(position, planet)
        case S => nextDown(position, planet)
      }

    def nextUp(position: Position, planet: Planet): Position =
      setX(position, (position.x - 1 + planet.size.x) % planet.size.x)

    def nextDown(position: Position, planet: Planet): Position =
      setX(position, (position.x + 1) % planet.size.x)

    def nextLeft(position: Position, planet: Planet): Position =
      setY(position, (position.y - 1 + planet.size.y) % planet.size.y)

    def nextRight(position: Position, planet: Planet): Position =
      setY(position, (position.y + 1) % planet.size.y)

    def setX(position: Position, value: Int): Position =
      position.copy(x = value)

    def setY(position: Position, value: Int): Position =
      position.copy(y = value)

    case class Position(x: Int, y: Int)
    case class Size(x: Int, y: Int)
    case class Planet(size: Size)
    case class Rover(position: Position, direction: Direction)
    case class Mission(planet: Planet, rover: Rover)

    sealed trait Command
    case class Move(direction: MoveType) extends Command
    case class Turn(direction: TurnType) extends Command

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
}

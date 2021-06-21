package marsroverkata

object Version2 {

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
    next(rover.position, planet.size, delta(rover.direction))

  def backward(rover: Rover, planet: Planet): Position =
    next(rover.position, planet.size, delta(opposite(rover.direction)))

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

  def next(position: Position, size: Size, delta: Delta): Position =
    position.copy(
      x = wrap(position.x, size.x, delta.x),
      y = wrap(position.y, size.y, delta.y)
    )

  case class Delta(x: Int, y: Int)
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

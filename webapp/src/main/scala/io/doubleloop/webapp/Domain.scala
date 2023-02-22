package io.doubleloop.webapp

import Orientation._, Command._, ParseError._
import cats.syntax.either._

object Domain {

  def executeAll(
    planet: Planet,
    rover: Rover,
    commands: List[Command]
  ): Either[ObstacleDetected, Rover] =
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

  def moveForward(
    planet: Planet,
    rover: Rover
  ): Either[ObstacleDetected, Rover] =
    next(planet, rover, delta(rover.orientation))
      .map(x => rover.copy(position = x))

  def moveBackward(
    planet: Planet,
    rover: Rover
  ): Either[ObstacleDetected, Rover] =
    next(planet, rover, delta(opposite(rover.orientation)))
      .map(x => rover.copy(position = x))

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

  def next(
    planet: Planet,
    rover: Rover,
    delta: Delta
  ): Either[ObstacleDetected, Position] = {
    val position = rover.position
    val candidate = position.copy(
      x = wrap(position.x, planet.size.width, delta.x),
      y = wrap(position.y, planet.size.height, delta.y)
    )
    val hitObstacle = planet.obstacles.map(_.position).contains(candidate)
    Either.cond(!hitObstacle, candidate, ObstacleDetected(rover))
  }

  def wrap(value: Int, limit: Int, delta: Int): Int =
    (((value + delta) % limit) + limit) % limit

}

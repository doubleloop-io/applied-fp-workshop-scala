package io.doubleloop.webapp

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

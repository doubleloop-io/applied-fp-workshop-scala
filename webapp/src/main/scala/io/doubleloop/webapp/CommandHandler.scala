package io.doubleloop.webapp

import Parsing._, Domain._, Infra._
import cats.effect.IO

object CommandHandler {

  def handleCommands(
    planetReader: PlanetReader,
    roverReader: RoverReader,
    inputCommands: String
  ): IO[Either[ObstacleDetected, Rover]] =
    for {
      planet <- planetReader.read()
      rover <- roverReader.read()
      commands = parseCommands(inputCommands)
    } yield executeAll(planet, rover, commands)
}

package io.doubleloop.webapp

import Infra._
import cats.effect.IO

object Adapters {

  def filePlanetReader(planetFile: String): PlanetReader = new PlanetReader {
    def read(): IO[Planet] = loadPlanet(planetFile)
  }

  def fileRoverReader(roverFile: String): RoverReader = new RoverReader {
    def read(): IO[Rover] = loadRover(roverFile)
  }
}

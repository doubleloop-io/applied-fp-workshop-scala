package io.doubleloop.webapp

import cats.effect.IO

trait PlanetReader {
  def read(): IO[Planet]
}

trait RoverReader {
  def read(): IO[Rover]
}

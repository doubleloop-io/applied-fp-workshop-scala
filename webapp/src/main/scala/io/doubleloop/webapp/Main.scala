package io.doubleloop.webapp

import cats.effect.{ IO, IOApp }

object Main extends IOApp.Simple {
  override def run: IO[Unit] =
    WebappServer.run
}

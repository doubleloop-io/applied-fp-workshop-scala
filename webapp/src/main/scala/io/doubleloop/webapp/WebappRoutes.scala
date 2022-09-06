package io.doubleloop.webapp

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import Rendering._, Domain._, CommandHandler._

object WebappRoutes {

  def helloRoutes(H: HelloWorld): HttpRoutes[IO] =
    HttpRoutes.of[IO] { case GET -> Root / "hello" / name =>
      for {
        greeting <- H.hello(HelloWorld.Name(name))
        resp <- Ok(greeting)
      } yield resp
    }

  def roverRoutes(
    planetReader: PlanetReader,
    roverReader: RoverReader
  ): HttpRoutes[IO] = {

    def sequenceCompleted(rover: Rover) =
      Ok(renderComplete(rover))

    def obstacleDetected(rover: ObstacleDetected) =
      UnprocessableEntity(renderObstacle(rover))

    HttpRoutes.of[IO] { case POST -> Root / "rover" / commands =>
      for {
        result <- handleCommands(
          planetReader,
          roverReader,
          commands
        )
        resp <- result.fold(obstacleDetected, sequenceCompleted)
      } yield resp
    }
  }

}

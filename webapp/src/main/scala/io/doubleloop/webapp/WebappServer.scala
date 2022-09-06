package io.doubleloop.webapp

import com.comcast.ip4s._
import org.http4s.implicits._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger
import cats.effect.{ IO, Resource }
import cats.implicits._
import ciris._

case class AppConfig(planetFile: String, roverFile: String)

object WebappServer {

  def appConfig(): ConfigValue[IO, AppConfig] =
    for {
      planetFile <- env("PLANET_FILE")
        .or(prop("files.planet"))
        .as[String]
        .default("planet.txt")
      roverFile <- env("ROVER_FILE")
        .or(prop("files.rover"))
        .as[String]
        .default("rover.txt")
    } yield AppConfig(planetFile, roverFile)

  def run: IO[Nothing] =
    (for {
      // Load application configuration
      conf <- Resource.eval(appConfig().load[IO])

      // Wire dependencies
      helloWorldAlg = HelloWorld.impl
      planetReader = Adapters.filePlanetReader(conf.planetFile)
      roverReader = Adapters.fileRoverReader(conf.roverFile)

      // Combine Routes into an HttpApp.
      httpApp = (
        WebappRoutes.helloRoutes(helloWorldAlg) <+>
          WebappRoutes.roverRoutes(planetReader, roverReader)
      ).orNotFound

      // With Middlewares in place
      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      // Build server
      _ <-
        EmberServerBuilder
          .default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(finalHttpApp)
          .build
    } yield ()).useForever

}

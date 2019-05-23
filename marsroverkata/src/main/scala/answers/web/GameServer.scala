package marsroverkata.answers.web

import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.{ConcurrentEffect, ContextShift, Timer}
import cats.implicits._
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger
import fs2.Stream
import scala.concurrent.ExecutionContext.global

object GameServer extends IOApp {
  def run(args: List[String]) =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(Logger.httpApp(true, true)(GameService.service.orNotFound))
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}

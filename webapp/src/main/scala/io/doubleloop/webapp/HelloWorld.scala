package io.doubleloop.webapp

import cats.effect.IO
import cats.implicits._
import io.circe.{ Decoder, Encoder }
import io.circe._
import org.http4s.Method._
import org.http4s._
import org.http4s.circe._
import org.http4s.implicits._

trait HelloWorld {
  def hello(n: HelloWorld.Name): IO[HelloWorld.Greeting]
}

object HelloWorld {
  case class Name(name: String)
  case class Greeting(message: String)

  object Greeting {
    given Encoder[Greeting] = Encoder.AsObject.derived[Greeting]
    given EntityEncoder[IO, Greeting] = jsonEncoderOf
  }

  def impl: HelloWorld = new HelloWorld {
    def hello(n: HelloWorld.Name): IO[HelloWorld.Greeting] =
      Greeting("Hello, " + n.name).pure[IO]
  }
}

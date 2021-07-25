package marsroverkata

import cats.effect._
import cats.effect.concurrent._
import cats.implicits._
import marsroverkata.answers.Version7._
import cats.effect.Ref

class Version7Tests extends munit.FunSuite {
  /*
   * TODO: Implements Console, Logger, MissionSource real instances
   *       Check out test implementations
   */

  case class TestState(
    info: Option[String],
    error: Option[String]
  )

  test("go to opposite angle, system test".ignore) {

    val (_, result) = Ref
      .of[IO, TestState](TestState(None, None))
      .flatMap(execute(("5x4", "2,0 0,3 3,2"), ("0,0", "N"), "RBBLBRF"))
      .unsafeRunSync()

    assertEquals(result, TestState(Some("4:3:E"), None))
  }

  test("invalid planet data, system test".ignore) {
    val (_, result) = Ref
      .of[IO, TestState](TestState(None, None))
      .flatMap(execute(("ax4", "2,0 0,3 3,2"), ("0,0", "N"), "RBBLBRF"))
      .unsafeRunSync()

    assertEquals(result, TestState(None, Some("InvalidPlanet(ax4,InvalidSize)")))
  }

  def execute(
    planet: (String, String),
    rover: (String, String),
    commands: String
  ): Ref[IO, TestState] => IO[(Unit, TestState)] =
    ref => {
      implicit val console: Console[IO] = new Console[IO] {
        def puts(line: String): IO[Unit] = IO.unit
        def reads(): IO[String]          = IO.pure(commands)
      }

      implicit val logger: Logger[IO] = new Logger[IO] {
        def logInfo(message: String): IO[Unit] =
          ref.update(ts => ts.copy(info = Some(message)))
        def logError(error: Throwable): IO[Unit] =
          ref.update(ts => ts.copy(error = Some(error.getMessage)))
      }

      implicit val source: MissionSource[IO] = new MissionSource[IO] {
        def loadPlanetData(file: String): IO[(String, String)] = IO.pure(planet)
        def loadRoverData(file: String): IO[(String, String)]  = IO.pure(rover)
      }

      (createApplication("anything", "anything"), ref.get).tupled
    }
}

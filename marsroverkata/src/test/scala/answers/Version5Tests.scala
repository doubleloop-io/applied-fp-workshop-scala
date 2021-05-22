package marsroverkata.answers

import cats._
import cats.data._
import cats.implicits._
import cats.effect._
import cats.effect.concurrent._

import marsroverkata.answers.Version5._

class Version5Tests extends munit.FunSuite {

  test("opposite angle") {
    val planet   = IO.pure(("5x4", "2,0 0,3 3,2"))
    val rover    = IO.pure(("0,0", "N"))
    val commands = IO.pure("RBBLBRF")
    val app      = (planet, rover, commands).mapN(run)
    val result   = app.unsafeRunSync()
    assertEquals(result, Right("4:3:E"))
  }

  test("bad planet size") {
    val planet   = IO.pure(("ax4", "2,0 0,3 3,2"))
    val rover    = IO.pure(("0,0", "N"))
    val commands = IO.pure("RFF")
    val app      = (planet, rover, commands).mapN(run)
    val result   = app.unsafeRunSync()
    assertEquals(result, Left(NonEmptyList.of(InvalidPlanet("ax4", "InvalidSize"))))
  }

  test("simulate app throws RuntimeException") {

    def scenario(ref: Ref[IO, Int]) = {
      implicit val silent = new Logger[IO] {

        private val increment = ref.modify(x => (x + 1, x))

        def logInfo(message: String): IO[Unit] =
          IO.unit

        def logError(message: String): IO[Unit] =
          increment *> IO.unit
      }

      val planet                      = IO.pure(("5x4", "2,0 0,3 3,2"))
      val rover: IO[(String, String)] = IO(throw new RuntimeException("boom!"))
      val commands                    = IO.pure("RFF")
      val app                         = (planet, rover, commands).mapN(run)
      val result                      = handleApp(app)
      (result, ref.get).tupled
    }

    val (result, errorCount) = Ref
      .of[IO, Int](0)
      .flatMap(scenario)
      .unsafeRunSync()

    assertEquals(result, "Ooops :-(")
    assertEquals(errorCount, 1)
  }
}

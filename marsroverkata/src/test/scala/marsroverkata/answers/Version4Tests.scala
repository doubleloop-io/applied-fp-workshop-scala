package marsroverkata.answers

import scala.util._
import cats.implicits._
import cats.effect._

import marsroverkata.answers.Version4._

class Version4Tests extends munit.FunSuite {

  test("load planet data (integration test with real filesystem)") {
    val planet = loadPlanetData("planet.txt")
    val result = planet.unsafeRunSync()
    assertEquals(result, ("5x4", "2,0 0,3 3,2"))
  }

  test("ask commands (integration test with real console)") {
    def execute(commands: String): String = {
      import java.io.ByteArrayOutputStream
      import java.io.StringReader

      val input = new StringReader(commands)
      val out   = new ByteArrayOutputStream
      Console.withIn(input) {
        Console.withOut(out) {
          askCommands().unsafeRunSync()
        }
      }
      out.toString.replace("\r", "")
    }

    val result = execute("RRF")

    assertEquals(result, "Waiting commands...\n")
  }

  test("go to opposite angle, system test (with real infrastructure)") {
    def execute[A](commands: String)(app: => IO[A]): A = {
      import java.io.ByteArrayOutputStream
      import java.io.StringReader

      val input = new StringReader(commands)
      val out   = new ByteArrayOutputStream
      Console.withIn(input) {
        Console.withOut(out) {
          app.unsafeRunSync()
        }
      }
    }

    val result = execute("RBBLBRF") {
      val planet   = loadPlanetData("planet.txt")
      val rover    = loadRoverData("rover.txt")
      val commands = askCommands()
      (planet, rover, commands).mapN(run)
    }

    assertEquals(result, Right("4:3:E"))
  }

  test("go to opposite angle, stubbed") {
    val planet   = IO.pure(("5x4", "2,0 0,3 3,2"))
    val rover    = IO.pure(("0,0", "N"))
    val commands = IO.pure("RBBLBRF")

    val app    = (planet, rover, commands).mapN(run)
    val result = app.unsafeRunSync()

    assertEquals(result, Right("4:3:E"))
  }

  test("unhandled RuntimeException") {
    val planet                      = IO.pure(("5x4", "2,0 0,3 3,2"))
    val rover: IO[(String, String)] = IO(throw new RuntimeException("boom!"))
    val commands                    = IO.pure("RFF")

    val app = (planet, rover, commands).mapN(run)
    val ex  = intercept[Exception](app.unsafeRunSync())

    assertEquals("boom!", ex.getMessage)
  }

  test("handled RuntimeException") {
    val planet                      = IO.pure(("5x4", "2,0 0,3 3,2"))
    val rover: IO[(String, String)] = IO(throw new RuntimeException("boom!"))
    val commands                    = IO.pure("RFF")

    val app    = (planet, rover, commands).mapN(run)
    val result = app.attempt.unsafeRunSync()

    assert(result.isLeft)
  }

}

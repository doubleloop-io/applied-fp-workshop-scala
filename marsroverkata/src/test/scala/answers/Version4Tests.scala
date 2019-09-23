package marsroverkata.answers

import minitest._

import cats._
import cats.data._
import cats.implicits._
import cats.effect._

import marsroverkata.answers.Version4._

object Version4Tests extends SimpleTestSuite {

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
    assertEquals(result, Left(List(InvalidPlanet("ax4", "InvalidSize"))))
  }

  test("simulate app throws unexpectedly") {
    val planet                      = IO.pure(("5x4", "2,0 0,3 3,2"))
    val rover: IO[(String, String)] = IO(throw new RuntimeException("boom!"))
    val commands                    = IO.pure("RFF")
    val app                         = (planet, rover, commands).mapN(run)
    val silentLogger                = (_: String) => IO.unit
    val result                      = handleApp(silentLogger)(app).unsafeRunSync()
    assertEquals(result, "Ooops :-(")
  }

  test("ask question (integration test with real console)") {
    def execute(answer: String): String = {
      import java.io.ByteArrayOutputStream
      import java.io.StringReader

      val input = new StringReader(answer)
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

  test("load planet data (integration test with real filesystem)") {
    val load   = loadPlanetData("planet.txt")
    val result = load.unsafeRunSync()
    assertEquals(result, ("5x4", "2,0 0,3 3,2"))
  }

  test("load rover data (integration test with real filesystem)") {
    val load   = loadRoverData("rover.txt")
    val result = load.unsafeRunSync()
    assertEquals(result, ("0,0", "N"))
  }
}

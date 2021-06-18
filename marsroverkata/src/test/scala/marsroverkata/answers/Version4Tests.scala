package marsroverkata.answers

import scala.util._
import cats.implicits._
import cats.effect._

import marsroverkata.answers.Version4._

class Version4Tests extends munit.FunSuite {

  test("go to opposite angle") {
    val planet   = IO.pure(("5x4", "2,0 0,3 3,2"))
    val rover    = IO.pure(("0,0", "N"))
    val commands = IO.pure("RBBLBRF")

    val app    = (planet, rover, commands).mapN(run)
    val result = app.unsafeRunSync()

    assertEquals(result, Right("4:3:E"))
  }

  test("invalid planet input data") {
    val planet   = IO.pure(("ax4", "2,0 0,3 3,2"))
    val rover    = IO.pure(("0,0", "N"))
    val commands = IO.pure("RFF")

    val app    = (planet, rover, commands).mapN(run)
    val result = app.unsafeRunSync()

    assertEquals(result, Left(InvalidPlanet("ax4", "InvalidSize")))
  }

  test("load planet data (integration test with real filesystem)") {
    val planet = loadPlanetData("planet.txt")
    val result = planet.unsafeRunSync()
    assertEquals(result, ("5x4", "2,0 0,3 3,2"))
  }

  test("load and execute data (integration test with real filesystem)") {
    val planet   = loadPlanetData("planet.txt")
    val rover    = loadRoverData("rover.txt")
    val commands = loadCommandsData("commands.txt")

    val app    = (planet, rover, commands).mapN(run)
    val result = app.unsafeRunSync()

    assertEquals(result, Right("4:3:E"))
  }

  test("simulate app throws RuntimeException") {
    val planet                      = IO.pure(("5x4", "2,0 0,3 3,2"))
    val rover: IO[(String, String)] = IO(throw new RuntimeException("boom!"))
    val commands                    = IO.pure("RFF")

    val app = (planet, rover, commands).mapN(run)
    val ex  = intercept[Exception](app.unsafeRunSync())

    assertEquals("boom!", ex.getMessage)
  }

}

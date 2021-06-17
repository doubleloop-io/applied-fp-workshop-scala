package marsroverkata

import cats._
import cats.data._
import cats.implicits._
import cats.effect._
import marsroverkata.Version4._

class Version4Tests extends munit.FunSuite {

// +-----+-----+-----+-----+-----+
// | 0,3 |     |     |     | 4,3 |
// +-----+-----+-----+-----+-----+
// |     |     |     |     |     |
// +-----+-----+-----+-----+-----+
// |     |     |     |     |     |
// +-----+-----+-----+-----+-----+
// | 0,0 |     |     |     | 4,0 |
// +-----+-----+-----+-----+-----+

  test("go to opposite angle") {
    val planet   = ("5x4", "2,0 0,3 3,2")
    val rover    = ("0,0", "N")
    val commands = "RBBLBRF"

    // TODO: complete test
    // lift planet, rover and commands in separated IO monad instances
    // lift domain entry point with: planet, rover and commands
    // run IO monad

    // assert result, OK "4:3:E"
  }

  test("invalid planet input data") {
    val planet   = ("ax4", "2,0 0,3 3,2")
    val rover    = ("1,2", "N")
    val commands = "RBRF"

    // TODO: complete test
    // lift planet, rover and commands in separated IO monad instances
    // lift domain domain entry point: planet, rover and commands
    // run IO monad

    // assert result, ERROR "Invalid planet size"
  }

  test("load planet data (integration test with real filesystem)") {
    // TODO: all the code for this test has already been implemented.
    //  Take a look to the loadPlanetData implementation.
    val load   = loadPlanetData("planet.txt")
    val result = load.unsafeRunSync()
    assertEquals(result, ("5x4", "2,0 0,3 3,2"))
  }

  test("load and execute data (integration test with real filesystem)") {
    // TODO: complete test
    // val planet   = load planet data...
    // val rover    = load rover data...
    // val commands = load commands data...

    // lift domain domain entry point: planet, rover and commands
    // run IO monad

    // assert result, OK "4:3:E"
  }

  test("simulate app throws RuntimeException") {
    // val planet                      = IO.pure(("5x4", "2,0 0,3 3,2"))
    // val rover: IO[(String, String)] = IO(throw new RuntimeException("boom!"))
    // val commands                    = IO.pure("RFF")
    // val app                         = (planet, rover, commands).mapN(run)
    // val silentLogger                = (_: String) => IO.unit
    // val result                      = handleApp(silentLogger)(app).unsafeRunSync()
    // assertEquals(result, "Ooops :-(")
  }
}

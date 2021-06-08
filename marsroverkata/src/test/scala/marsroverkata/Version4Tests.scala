package marsroverkata

import cats._
import cats.data._
import cats.implicits._
import cats.effect._

// TODO: uncomment import
// import marsroverkata.Version4._

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

  test("opposite angle".ignore) {
    // val planet   = IO.pure(("5x4", "2,0 0,3 3,2"))
    // val rover    = IO.pure(("0,0", "N"))
    // val commands = IO.pure("RBBLBRF")
    // val app      = (planet, rover, commands).mapN(run)
    // val result   = app.unsafeRunSync()
    // assertEquals(result, Right("4:3:E"))
  }

  test("bad planet size".ignore) {
    // val planet   = IO.pure(("ax4", "2,0 0,3 3,2"))
    // val rover    = IO.pure(("0,0", "N"))
    // val commands = IO.pure("RFF")
    // val app      = (planet, rover, commands).mapN(run)
    // val result   = app.unsafeRunSync()
    // assertEquals(result, Left(NonEmptyList.of(InvalidPlanet("ax4", "InvalidSize"))))
  }

  test("simulate app throws RuntimeException".ignore) {
    // val planet                      = IO.pure(("5x4", "2,0 0,3 3,2"))
    // val rover: IO[(String, String)] = IO(throw new RuntimeException("boom!"))
    // val commands                    = IO.pure("RFF")
    // val app                         = (planet, rover, commands).mapN(run)
    // val silentLogger                = (_: String) => IO.unit
    // val result                      = handleApp(silentLogger)(app).unsafeRunSync()
    // assertEquals(result, "Ooops :-(")
  }
}

package marsroverkata

import cats._
import cats.data._
import cats.implicits._

// TODO: uncomment import
// import marsroverkata.Version2._

class Version2Tests extends munit.FunSuite {

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
    /*
        Plant: 5 4
        Rover: 0 0 N
        Commands: RBBLBRF
        --
        Right(Rover: 4 3 E)
     */
    // val result = run("5x4", "0,0:N", "RBBLBRF")
  }

  test("all inputs are bad".ignore) {
    /*
        Plant: a 4
        Rover: 1 c X
        Commands: RBRF
        --
        Left(["Invalid Planet Size",
              "Invalid Rover Position",
              "Invalid Rover Direction"])
     */
    // val result = run("ax4", "1,c:N", "RBRF")
  }
}

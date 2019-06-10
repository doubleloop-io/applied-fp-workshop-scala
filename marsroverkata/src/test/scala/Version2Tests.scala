package marsroverkata

import minitest._

import cats._
import cats.data._
import cats.implicits._

// TODO: uncomment import
// import marsroverkata.Version2._

object Version2Tests extends SimpleTestSuite {

  test("opposite angle") {
    /*
        Plant: 5 4
        Rover: 0 0 N
        Commands: RBBLBRF
        --
        Right(Rover: 4 3 E)
     */
    ignore()
    // val result = run("5x4", "0,0:N", "RBBLBRF")
    // assertEquals(result, Right("4:3:E"))
  }

  test("all inputs are bad") {
    /*
        Plant: a 4
        Rover: 1 c N
        Commands: RBRF
        --
        Left([InvalidPlanet, InvalidRover])
     */
    ignore()
    // val result = run("ax4", "1,c:N", "RBRF")
  }
}

package marsroverkata

import cats._
import cats.data._
import cats.implicits._

// TODO: uncomment import
// import marsroverkata.Version3._

class Version3Tests extends munit.FunSuite {

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
    // val planet = ("5x4", "2,0 0,3 3,2")
    // val rover  = ("0,0", "N")
    // val result = run(planet, rover, "RBBLBRF")
    // assertEquals(result, Right("4:3:E"))
  }

  test("hit obstacle".ignore) {
    // val planet = ("5x4", "2,0 0,3 3,2")
    // val rover  = ("0,0", "N")
    // val result = run(planet, rover, "RFF")
    // assertEquals(result, Right("O:1:0:E"))
  }
}

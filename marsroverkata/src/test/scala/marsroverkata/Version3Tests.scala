package marsroverkata

import scala.util._
import marsroverkata.Version3._

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

  test("go to opposite angle") {
    val planet   = ("5x4", "2,0 0,3 3,2")
    val rover    = ("0,0", "N")
    val commands = "RBBLBRF"

    // TODO: complete the test
    // invoke a function with: planet, obstacles, rover and commands

    // assert result, OK "4:3:E"
  }

  test("hit obstacle") {
    val planet   = ("5x4", "2,0 0,3 3,2")
    val rover    = ("0,0", "N")
    val commands = "RFF"

    // TODO: complete the test
    // invoke a function with: planet, obstacles, rover and commands

    // assert result, OK "O:1:0:E"
  }

  test("invalid planet input data") {
    val planet   = ("ax4", "2,0 0,3 3,2")
    val rover    = ("1,2", "N")
    val commands = "RBRF"

    // TODO: complete the test
    // invoke a function with: planet, obstacles, rover and commands

    // assert result, ERROR "Invalid planet size"
  }

  test("invalid rover input data") {
    val planet   = ("5x4", "2,0 0,3 3,2")
    val rover    = ("1,2", "X")
    val commands = "RBRF"

    // TODO: complete the test
    // invoke a function with: planet, obstacles, rover and commands

    // assert result, ERROR "Invalid rover direction"
  }

  test("unknown command") {
    val planet   = ("5x4", "2,0 0,3 3,2")
    val rover    = ("1,2", "N")
    val commands = "RBXRF"

    // TODO: complete the test
    // invoke a function with: planet, obstacles, rover and commands

    // assert result, OK "0:1:S"
  }
}

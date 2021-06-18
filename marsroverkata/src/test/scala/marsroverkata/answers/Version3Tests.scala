package marsroverkata.answers

import scala.util._
import marsroverkata.answers.Version3._

class Version3Tests extends munit.FunSuite {

  test("go to opposite angle") {
    val planet   = ("5x4", "2,0 0,3 3,2")
    val rover    = ("0,0", "N")
    val commands = "RBBLBRF"

    val result = run(planet, rover, commands)

    assertEquals(result, Right("4:3:E"))
  }

  test("hit obstacle") {
    val planet   = ("5x4", "2,0 0,3 3,2")
    val rover    = ("0,0", "N")
    val commands = "RFF"

    val result = run(planet, rover, commands)

    assertEquals(result, Right("O:1:0:E"))
  }

  test("invalid planet input data") {
    val planet   = ("ax4", "2,0 0,3 3,2")
    val rover    = ("1,2", "N")
    val commands = "RBRF"

    val result = run(planet, rover, commands)

    assertEquals(result, Left(InvalidPlanet("ax4", "InvalidSize")))
  }

  test("invalid rover input data") {
    val planet   = ("5x4", "2,0 0,3 3,2")
    val rover    = ("1,2", "X")
    val commands = "RBRF"

    val result = run(planet, rover, commands)

    assertEquals(result, Left(InvalidRover("X", "InvalidDirection")))
  }

  test("unknown command") {
    val planet   = ("5x4", "2,0 0,3 3,2")
    val rover    = ("1,2", "N")
    val commands = "RBXRF"

    val result = run(planet, rover, commands)

    assertEquals(result, Right("0:1:S"))
  }
}

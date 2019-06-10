package marsroverkata.answers

import minitest._

import cats._
import cats.data._
import cats.implicits._

import marsroverkata.answers.Version2._

object Version2Tests extends SimpleTestSuite {

  test("opposite angle") {
    val rover  = ("0,0", "N")
    val result = run("5x4", rover, "RBBLBRF")
    assertEquals(result, Right("4:3:E"))
  }

  test("all inputs are bad") {
    val rover  = ("1,c", "X")
    val result = run("ax4", rover, "RBRF")
    assertEquals(
      result,
      Left(
        NonEmptyList.of(InvalidPlanet("ax4", "InvalidSize"),
                        InvalidRover("1,c", "InvalidPosition"),
                        InvalidRover("X", "InvalidDirection"))
      )
    )
  }
}

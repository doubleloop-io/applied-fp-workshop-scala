package marsroverkata.answers

import minitest._

import cats._
import cats.data._
import cats.implicits._

import marsroverkata.answers.Version2._

object Version2Tests extends SimpleTestSuite {

  test("three times right") {
    val result = run("5x4", "0,0:N", "RRR")
    assertEquals(result, Right("0:0:W"))
  }

  test("two times left") {
    val result = run("5x4", "0,0:N", "LL")
    assertEquals(result, Right("0:0:S"))
  }

  test("wrap on North") {
    val result = run("5x4", "0,0:N", "FFFFFF")
    assertEquals(result, Right("0:2:N"))
  }

  test("wrap on South") {
    val result = run("5x4", "0,0:S", "F")
    assertEquals(result, Right("0:3:S"))
  }

  test("wrap on Est") {
    val result = run("5x4", "0,0:E", "FFFFF")
    assertEquals(result, Right("0:0:E"))
  }

  test("wrap on West") {
    val result = run("5x4", "0,0:W", "FF")
    assertEquals(result, Right("3:0:W"))
  }

  test("opposite angle") {
    val result = run("5x4", "0,0:N", "RBBLBRF")
    assertEquals(result, Right("4:3:E"))
  }

  test("all inputs are bad") {
    val result = run("ax4", "1,c:N", "RBRF")
    assertEquals(
      result,
      Left(
        NonEmptyList.of(InvalidPlanet("ax4", "NumberFormatException"), InvalidRover("1,c:N", "NumberFormatException"))
      )
    )
  }
}

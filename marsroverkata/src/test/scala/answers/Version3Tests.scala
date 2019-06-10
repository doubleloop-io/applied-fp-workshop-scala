package marsroverkata.answers

import minitest._

import cats._
import cats.data._
import cats.implicits._

import marsroverkata.answers.Version3._

object Version3Tests extends SimpleTestSuite {

  test("opposite angle") {
    val planet = ("5x4", "2,0 0,3 3,2")
    val rover  = ("0,0", "N")
    val result = run(planet, rover, "RBBLBRF")
    assertEquals(result, Right("4:3:E"))
  }

  test("hit obstacle") {
    val planet = ("5x4", "2,0 0,3 3,2")
    val rover  = ("0,0", "N")
    val result = run(planet, rover, "RFF")
    assertEquals(result, Right("O:1:0:E"))
  }
}

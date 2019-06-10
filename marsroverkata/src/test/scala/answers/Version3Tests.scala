package marsroverkata.answers

import minitest._

import cats._
import cats.data._
import cats.implicits._

import marsroverkata.answers.Version3._

object Version3Tests extends SimpleTestSuite {

  test("opposite angle") {
    val result = run("5x4", "2,0 0,3 3,2", "0,0:N", "RBBLBRF")
    assertEquals(result, Right("4:3:E"))
  }

  test("all inputs are bad") {
    val result = run("ax4", "2,0 0,3 3,2", "1,c:N", "RBRF")
    assertEquals(
      result,
      Left(
        NonEmptyList.of(InvalidPlanet("ax4", "NumberFormatException"), InvalidRover("1,c:N", "NumberFormatException"))
      )
    )
  }

  test("hit obstacle") {
    val result = run("5x4", "2,0 0,3 3,2", "0,0:N", "RFF")
    assertEquals(result, Right("O:1:0:E"))
  }

  def run(planet: String, obstacles: String, rover: String, commands: String): Either[NonEmptyList[Error], String] =
    init(planet, rover)
      .map(execute(_, parseCommands(commands)))
      .map(_.bimap(_.rover, _.rover).fold(renderHit, render))
}

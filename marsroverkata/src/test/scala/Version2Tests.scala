package marsroverkata

import minitest._

import cats._
import cats.data._
import cats.implicits._

import marsroverkata.Workspace._

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
    val result = run("5x4", "0,0:N", "RBBLBRF")
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
    val result = run("ax4", "1,c:N", "RBRF")
  }

  def run(planet: String, rover: String, commands: String): Either[NonEmptyList[Error], String] =
    ???
}

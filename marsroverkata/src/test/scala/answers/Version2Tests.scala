package marsroverkata

import minitest._

import cats._
import cats.data._
import cats.implicits._

import marsroverkata.answers.Version2._

object Version2Tests extends SimpleTestSuite {

  test("example1") {
    /*
        Title: three times right
        --
        Plant: 5 4
        Rover: 0 0 N
        Commands: RRR
        --
        Rover: 0 0 W
     */
    val result = run("5x4", "0,0:N", "RRR")
    assertEquals(result, Right("0:0:W"))
  }

  test("example2") {
    /*
        Title: two times left
        --
        Plant: 5 4
        Rover: 0 0 N
        Commands: LL
        --
        Rover: 0 0 S
     */
    val result = run("5x4", "0,0:N", "LL")
    assertEquals(result, Right("0:0:S"))
  }

  test("example3") {
    /*
        Title: wrap on North
        --
        Plant: 5 4
        Rover: 0 0 N
        Commands: FFFFFF
        --
        Rover: 0 2 N
     */
    val result = run("5x4", "0,0:N", "FFFFFF")
    assertEquals(result, Right("0:2:N"))
  }

  test("example4") {
    /*
        Title: wrap on South
        --
        Plant: 5 4
        Rover: 0 0 S
        Commands: F
        --
        Rover: 0 3 S
     */
    val result = run("5x4", "0,0:S", "F")
    assertEquals(result, Right("0:3:S"))
  }

  test("example5") {
    /*
        Title: wrap on Est
        --
        Plant: 5 4
        Rover: 0 0 E
        Commands: FFFFF
        --
        Rover: 0 0 E
     */
    val result = run("5x4", "0,0:E", "FFFFF")
    assertEquals(result, Right("0:0:E"))
  }

  test("example6") {
    /*
        Title: wrap on West
        --
        Plant: 5 4
        Rover: 0 0 W
        Commands: FF
        --
        Rover: 3 0 W
     */
    val result = run("5x4", "0,0:W", "FF")
    assertEquals(result, Right("3:0:W"))
  }

  test("example7") {
    /*
        Title: opposite angle
        --
        Plant: 5 4
        Rover: 0 0 N
        Commands: RBBLBRF
        --
        Rover: 4 3 E
     */
    val result = run("5x4", "0,0:N", "RBBLBRF")
    assertEquals(result, Right("4:3:E"))
  }

  test("example8") {
    /*
        Title: all inputs are bad
        --
        Plant: 5 4
        Rover: 0 0 N
        Commands: RBBLBRF
        --
        Rover: 4 3 E
     */
    val result = run("ax4", "1,c:N", "RBRF")
    assertEquals(
      result,
      Left(
        NonEmptyList.of(InvalidPlanet("ax4", "NumberFormatException"), InvalidRover("1,c:N", "NumberFormatException"))
      )
    )
  }

  def run(planet: String, rover: String, commands: String): Either[NonEmptyList[Error], String] =
    init(planet, rover)
      .map(execute(_, parseCommands(commands)))
      .map(m => render(m.rover))
}

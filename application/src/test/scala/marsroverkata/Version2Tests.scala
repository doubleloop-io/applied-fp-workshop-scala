package marsroverkata

class Version2Tests extends munit.FunSuite {

  import marsroverkata.Version2._
  import marsroverkata.Version2.Rotation._, Orientation._, Movement._, Command._, ParseError._

  // TODO: remove ignore

  test("go to opposite angle".ignore) {
    val planet = ("5x4", "2,0 0,3 3,2")
    val rover = ("0,0", "N")
    val commands = "RBBLBRF"

    val result = runMission(planet, rover, commands)

    assertEquals(result, Right("4:3:E"))
  }

  test("invalid planet input data".ignore) {
    val planet = ("ax4", "2,0 0,3 3,2")
    val rover = ("1,2", "N")
    val commands = "RBRF"

    val result = runMission(planet, rover, commands)

    assertEquals(result, Left(InvalidPlanet("invalid size: ax4")))
  }

  test("invalid rover input data".ignore) {
    val planet = ("5x4", "2,0 0,3 3,2")
    val rover = ("1,2", "X")
    val commands = "RBRF"

    val result = runMission(planet, rover, commands)

    assertEquals(result, Left(InvalidRover("invalid orientation: X")))
  }

  test("unknown command".ignore) {
    val planet = ("5x4", "2,0 0,3 3,2")
    val rover = ("1,2", "N")
    val commands = "RBXRF"

    val result = runMission(planet, rover, commands)

    assertEquals(result, Right("0:1:S"))
  }
}

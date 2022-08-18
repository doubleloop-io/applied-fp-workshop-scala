package marsroverkata.answers

class Version3Tests extends munit.FunSuite {

  import marsroverkata.answers.Version3._
  import marsroverkata.answers.Version3.ParseError._

  test("go to opposite angle") {
    val planet = ("5x4", "2,0 0,3 3,2")
    val rover = ("0,0", "N")
    val commands = "RBBLBRF"

    val result = runMission(planet, rover, commands)

    assertEquals(result, Right("4:3:E"))
  }

  test("hit obstacle during commands execution") {
    val planet = ("5x4", "2,0 0,3 3,2")
    val rover = ("0,0", "N")
    val commands = "RFF"

    val result = runMission(planet, rover, commands)

    assertEquals(result, Right("O:1:0:E"))
  }

  test("invalid planet input data") {
    val planet = ("ax4", "2,0 0,3 3,2")
    val rover = ("1,2", "N")
    val commands = "RBRF"

    val result = runMission(planet, rover, commands)

    assertEquals(result, Left(InvalidPlanet("invalid size: ax4")))
  }
}

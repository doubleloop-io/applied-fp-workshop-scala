package application

class Version1Tests extends munit.FunSuite {

  import application.Version1._

  // TODO: implements tests

  test("turn right command") {
//    val planet = Planet:  5 4
//    val rover = Rover: 0 0 N
//    val command = Command: R
//    val result = execute(planet, rover, command)
//    assertEquals(result, Rover: 0 0 E)
  }

  test("turn left command") {
//    val planet = Planet: 5 4
//    val rover = Rover: 0 0 N
//    val command = Command: L
//    val result = execute(planet, rover, command)
//    assertEquals(result, Rover: 0 0 W)
  }

  test("move forward command") {
//    val planet = Planet: 5 4
//    val rover = Rover: 0 1 N
//    val command = Command: F
//    val result = execute(planet, rover, command)
//    assertEquals(result, Rover: 0 2 N)
  }

  test("move forward command, opposite orientation") {
//    val planet = Planet: 5 4
//    val rover = Rover: 0 1 S
//    val command = Command: F
//    val result = execute(planet, rover, command)
//    assertEquals(result, Rover: 0 0 S)
  }

  test("move backward command") {
//    val planet = Planet: 5 4
//    val rover = Rover: 0 1 N
//    val command = Command: B
//    val result = execute(planet, rover, command)
//    assertEquals(result, Rover: 0 0 N)
  }

  test("move backward command, opposite orientation") {
//    val planet = Planet: 5 4
//    val rover = Rover: 0 1 S
//    val command = Command: B
//    val result = execute(planet, rover, command)
//    assertEquals(result, Rover: 0 2 S)
  }

  test("wrap on North") {
//    val planet = Planet: 5 4
//    val rover = Rover: 0 3 N
//    val command = Command: F
//    val result = execute(planet, rover, command)
//    assertEquals(result, Rover: 0 0 N)
  }

  test("go to opposite angle") {
//    val planet = Planet 5 4
//    val rover = Rover: 0 0 N
//    val commands = Commands: L F R B
//    val result = executeAll(rover, planet, commands)
//    assertEquals(result, Rover: 4 3 N)
  }
}

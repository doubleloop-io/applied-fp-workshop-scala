package marsroverkata

class Version1Tests extends munit.FunSuite {

  // Planet layout
  // +-----+-----+-----+-----+-----+
  // | 0,3 |     |     |     | 4,3 |
  // +-----+-----+-----+-----+-----+
  // |     |     |     |     |     |
  // +-----+-----+-----+-----+-----+
  // |     |     |     |     |     |
  // +-----+-----+-----+-----+-----+
  // | 0,0 |     |     |     | 4,0 |
  // +-----+-----+-----+-----+-----+
  
  import marsroverkata.Version1._

  // TODO: implements tests and feel free to add more...
  
  test("turn right command") {
//    val rover = Rover: 0 0 N 
//    val planet = Planet: 5 4
//    val command = R
//    val result = execute(mission, command)
//    assertEquals(result, Rover: 0 0 E)
  }

  test("turn left command") {
//    val rover = Rover: 0 0 N
//    val planet = Planet: 5 4
//    val command = Command: L
//    val result = execute(mission, command)
//    assertEquals(result, Rover: 0 0 W)
  }

  test("move forward command") {
//    val rover = Rover: 0 1 N
//    val planet = Planet: 5 4
//    val command = Command: F
//    val result = execute(mission, command)
//    assertEquals(result, Rover: 0 2 N)
  }

  test("move forward command, opposite orientation") {
//    val rover = Rover: 0 1 S
//    val planet = Planet: 5 4
//    val command = Command: F
//    val result = execute(mission, command)
//    assertEquals(result, Rover: 0 0 S)
  }

  test("move backward command") {
//    val rover = Rover: 0 1 N
//    val planet = Planet: 5 4
//    val command = Command: B
//    val result = execute(mission, command)
//    assertEquals(result, Rover: 0 0 N)
  }

  test("move backward command, opposite orientation") {
//    val rover = Rover: 0 1 S
//    val planet = Planet: 5 4
//    val command = Command: B
//    val result = execute(mission, command)
//    assertEquals(result, Rover: 0 2 S)
  }

  test("wrap on North") {
//    val rover = Rover: 0 3 N
//    val planet = Planet: 5 4
//    val command = Command: F
//    val result = execute(mission, command)
//    assertEquals(result, Rover: 0 0 N)
  }
}
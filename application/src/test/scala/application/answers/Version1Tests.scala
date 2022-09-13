package application.answers

@munit.IgnoreSuite
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

  import application.answers.Version1._
  import application.answers.Version1.Rotation._, Orientation._, Movement._, Command._

  test("turn right command") {
    val planet = Planet(Size(5, 4))
    val rover = Rover(Position(0, 0), N)
    val command = Turn(OnRight)
    val result = execute(planet, rover, command)
    assertEquals(result, Rover(Position(0, 0), E))
  }

  test("turn left command") {
    val planet = Planet(Size(5, 4))
    val rover = Rover(Position(0, 0), N)
    val command = Turn(OnLeft)
    val result = execute(planet, rover, command)
    assertEquals(result, Rover(Position(0, 0), W))
  }

  test("move forward command") {
    val planet = Planet(Size(5, 4))
    val rover = Rover(Position(0, 1), N)
    val command = Move(Forward)
    val result = execute(planet, rover, command)
    assertEquals(result, Rover(Position(0, 2), N))
  }

  test("move forward command, opposite orientation") {
    val planet = Planet(Size(5, 4))
    val rover = Rover(Position(0, 1), S)
    val command = Move(Forward)
    val result = execute(planet, rover, command)
    assertEquals(result, Rover(Position(0, 0), S))
  }

  test("move backward command") {
    val planet = Planet(Size(5, 4))
    val rover = Rover(Position(0, 1), N)
    val command = Move(Backward)
    val result = execute(planet, rover, command)
    assertEquals(result, Rover(Position(0, 0), N))
  }

  test("move backward command, opposite orientation") {
    val planet = Planet(Size(5, 4))
    val rover = Rover(Position(0, 1), S)
    val command = Move(Backward)
    val result = execute(planet, rover, command)
    assertEquals(result, Rover(Position(0, 2), S))
  }

  test("wrap on North") {
    val planet = Planet(Size(5, 4))
    val rover = Rover(Position(0, 3), N)
    val command = Move(Forward)
    val result = execute(planet, rover, command)
    assertEquals(result, Rover(Position(0, 0), N))
  }

  test("go to opposite angle") {
    val planet = Planet(Size(5, 4))
    val rover = Rover(Position(0, 0), N)
    val commands = List(Turn(OnLeft), Move(Forward), Turn(OnRight), Move(Backward))
    val result = executeAll(planet, rover, commands)
    assertEquals(result, Rover(Position(4, 3), N))
  }
}

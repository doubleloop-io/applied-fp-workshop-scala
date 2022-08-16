package marsroverkata.answers

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

  import marsroverkata.answers.Version1._
  import marsroverkata.answers.Version1.Rotation._, Orientation._, Movement._, Command._

  test("turn right command") {
    val rover = Rover(Position(0, 0), N)
    val planet = Planet(Size(5, 4))
    val command = Turn(OnRight)
    val result = execute(rover, planet, command)
    assertEquals(result, Rover(Position(0, 0), E))
  }

  test("turn left command") {
    val rover = Rover(Position(0, 0), N)
    val planet = Planet(Size(5, 4))
    val command = Turn(OnLeft)
    val result = execute(rover, planet, command)
    assertEquals(result, Rover(Position(0, 0), W))
  }

  test("move forward command") {
    val rover = Rover(Position(0, 1), N)
    val planet = Planet(Size(5, 4))
    val command = Move(Forward)
    val result = execute(rover, planet, command)
    assertEquals(result, Rover(Position(0, 2), N))
  }

  test("move forward command, opposite orientation") {
    val rover = Rover(Position(0, 1), S)
    val planet = Planet(Size(5, 4))
    val command = Move(Forward)
    val result = execute(rover, planet, command)
    assertEquals(result, Rover(Position(0, 0), S))
  }

  test("move backward command") {
    val rover = Rover(Position(0, 1), N)
    val planet = Planet(Size(5, 4))
    val command = Move(Backward)
    val result = execute(rover, planet, command)
    assertEquals(result, Rover(Position(0, 0), N))
  }

  test("move backward command, opposite orientation") {
    val rover = Rover(Position(0, 1), S)
    val planet = Planet(Size(5, 4))
    val command = Move(Backward)
    val result = execute(rover, planet, command)
    assertEquals(result, Rover(Position(0, 2), S))
  }

  test("wrap on North") {
    val rover = Rover(Position(0, 3), N)
    val planet = Planet(Size(5, 4))
    val command = Move(Forward)
    val result = execute(rover, planet, command)
    assertEquals(result, Rover(Position(0, 0), N))
  }

  test("go to opposite angle") {
    val rover = Rover(Position(0, 0), N)
    val planet = Planet(Size(5, 4))
    val commands = List(Turn(OnLeft), Move(Forward), Turn(OnRight), Move(Backward))
    val result = executeAll(rover, planet, commands)
    assertEquals(result, Rover(Position(4, 3), N))
  }
}

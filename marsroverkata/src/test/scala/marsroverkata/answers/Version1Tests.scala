package marsroverkata.answers

import marsroverkata.answers.Version1._

class Version1Tests extends munit.FunSuite {
  test("turn right command") {
    val mission = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val command = Turn(OnRight)
    val result  = execute(mission, command)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), E)))
  }

  test("turn left command") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val command = Turn(OnLeft)
    val result   = execute(mission, command)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), W)))
  }

  test("move forward command") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 1), N))
    val command = Move(Forward)
    val result   = execute(mission, command)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 2), N)))
  }

  test("move forward command, opposite orientation") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 1), S))
    val command = Move(Forward)
    val result   = execute(mission, command)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), S)))
  }

  test("move backward command") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 1), N))
    val command = Move(Backward)
    val result   = execute(mission, command)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N)))
  }

  test("move backward command, opposite orientation") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 1), S))
    val command = Move(Backward)
    val result   = execute(mission, command)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 2), S)))
  }

  test("wrap on North") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 3), N))
    val command = Move(Forward)
    val result   = execute(mission, command)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N)))
  }

  test("wrap on South") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), S))
    val command = Move(Forward)
    val result   = execute(mission, command)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 3), S)))
  }

  test("wrap on Est") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(4, 1), E))
    val command = Move(Forward)
    val result   = execute(mission, command)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 1), E)))
  }

  test("wrap on West") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 1), W))
    val command = Move(Forward)
    val result   = execute(mission, command)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(4, 1), W)))
  }
}

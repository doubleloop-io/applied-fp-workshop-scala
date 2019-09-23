package marsroverkata.answers

import minitest._

import marsroverkata.answers.Version1._

object Version1Tests extends SimpleTestSuite {
  test("three times right") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands = List(Turn(OnRight), Turn(OnRight), Turn(OnRight))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), W)))
  }

  test("two times left") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands = List(Turn(OnLeft), Turn(OnLeft))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), S)))
  }

  test("wrap on North") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands = List(Move(Forward), Move(Forward), Move(Forward), Move(Forward), Move(Forward), Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 2), N)))
  }

  test("wrap on South") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), S))
    val commands = List(Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 3), S)))
  }

  test("wrap on Est") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), E))
    val commands = List(Move(Forward), Move(Forward), Move(Forward), Move(Forward), Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), E)))
  }

  test("wrap on West") {
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), W))
    val commands = List(Move(Forward), Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(3, 0), W)))
  }

  test("opposite angle") {
    val mission = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands =
      List(Turn(OnRight), Move(Backward), Move(Backward), Turn(OnLeft), Move(Backward), Turn(OnRight), Move(Forward))
    val result = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(4, 3), E)))
  }
}

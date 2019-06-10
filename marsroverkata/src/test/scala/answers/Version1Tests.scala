package marsroverkata.answers

import minitest._

import marsroverkata.answers.Version1._

object Version1Tests extends SimpleTestSuite {
  test("three times right") {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: RRR
        --
        Rover: 0 0 W
     */
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands = List(Turn(OnRight), Turn(OnRight), Turn(OnRight))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), W)))
  }

  test("two times left") {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: LL
        --
        Rover: 0 0 S
     */
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands = List(Turn(OnLeft), Turn(OnLeft))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), S)))
  }

  test("wrap on North") {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: FFFFFF
        --
        Rover: 0 2 N
     */
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands = List(Move(Forward), Move(Forward), Move(Forward), Move(Forward), Move(Forward), Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 2), N)))
  }

  test("wrap on South") {
    /*
        Planet: 5 4
        Rover: 0 0 S
        Commands: FFFFFFF
        --
        Rover: 0 3 S
     */
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), S))
    val commands = List(Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 3), S)))
  }

  test("wrap on Est") {
    /*
        Planet: 5 4
        Rover: 0 0 E
        Commands: FFFFF
        --
        Rover: 0 0 E
     */
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), E))
    val commands = List(Move(Forward), Move(Forward), Move(Forward), Move(Forward), Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), E)))
  }

  test("wrap on West") {
    /*
        Planet: 5 4
        Rover: 0 0 W
        Commands: FF
        --
        Rover: 3 0 W
     */
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), W))
    val commands = List(Move(Forward), Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(3, 0), W)))
  }

  test("opposite angle") {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: RBBLBRF
        --
        Rover: 4 3 E
     */
    val mission = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands =
      List(Turn(OnRight), Move(Backward), Move(Backward), Turn(OnLeft), Move(Backward), Turn(OnRight), Move(Forward))
    val result = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(4, 3), E)))
  }
}

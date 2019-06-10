package marsroverkata

import minitest._

import marsroverkata.answers.Version1._

object Version1Tests extends SimpleTestSuite {
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
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands = List(Turn(OnRight), Turn(OnRight), Turn(OnRight))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), W)))
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
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands = List(Turn(OnLeft), Turn(OnLeft))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), S)))
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
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands = List(Move(Forward), Move(Forward), Move(Forward), Move(Forward), Move(Forward), Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 2), N)))
  }

  test("example4") {
    /*
        Title: wrap on South
        --
        Plant: 5 4
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
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), E))
    val commands = List(Move(Forward), Move(Forward), Move(Forward), Move(Forward), Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(0, 0), E)))
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
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), W))
    val commands = List(Move(Forward), Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(3, 0), W)))
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
    val mission = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands =
      List(Turn(OnRight), Move(Backward), Move(Backward), Turn(OnLeft), Move(Backward), Turn(OnRight), Move(Forward))
    val result = execute(mission, commands)
    assertEquals(result, Mission(Planet(Size(5, 4)), Rover(Position(4, 3), E)))
  }
}

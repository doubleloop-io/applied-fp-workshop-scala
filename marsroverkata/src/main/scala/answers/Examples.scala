package marsroverkata.answers

import marsroverkata.answers.Version1._

object Examples {

  def example1(): Unit = {
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
    println(execute(mission, commands))
  }

  def example2(): Unit = {
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
    println(execute(mission, commands))
  }

  def example3(): Unit = {
    /*
        Title: wrap on North
        --
        Plant: 5 4
        Rover: 0 0 N
        Commands: FFFFFF
        --
        Rover: 0 2 N
     */
    val mission = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), N))
    val commands = List(
      Move(Forward),
      Move(Forward),
      Move(Forward),
      Move(Forward),
      Move(Forward),
      Move(Forward)
    )
    println(execute(mission, commands))
  }

  def example4(): Unit = {
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
    println(execute(mission, commands))
  }

  def example5(): Unit = {
    /*
        Title: wrap on Est
        --
        Plant: 5 4
        Rover: 0 0 E
        Commands: FFFFF
        --
        Rover: 0 0 E
     */
    val mission = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), E))
    val commands = List(
      Move(Forward),
      Move(Forward),
      Move(Forward),
      Move(Forward),
      Move(Forward)
    )
    println(execute(mission, commands))
  }

  def example6(): Unit = {
    /*
        Title: wrap on West
        --
        Plant: 5 4
        Rover: 0 0 W
        Commands: FF
        --
        Rover: 0 0 W
     */
    val mission  = Mission(Planet(Size(5, 4)), Rover(Position(0, 0), W))
    val commands = List(Move(Forward), Move(Forward))
    println(execute(mission, commands))
  }

  def example7(): Unit = {
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
    val commands = List(
      Turn(OnRight),
      Move(Backward),
      Move(Backward),
      Turn(OnLeft),
      Move(Backward),
      Turn(OnRight),
      Move(Forward),
    )
    println(execute(mission, commands))
  }

  def run(): Unit = {
    example1()
    example2()
    example3()
    example4()
    example5()
    example6()
    example7()
  }
}

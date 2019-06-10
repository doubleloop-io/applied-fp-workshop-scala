package marsroverkata

object Examples {

  def example1(): Unit =
    /*
        Title: three times right
        --
        Plant: 5 4
        Rover: 0 0 N
        Commands: RRR
        --
        Rover: 0 0 W
     */
    println("example1")

  def example2(): Unit =
    /*
        Title: two times left
        --
        Plant: 5 4
        Rover: 0 0 N
        Commands: LL
        --
        Rover: 0 0 S
     */
    println("example2")

  def example3(): Unit =
    /*
        Title: wrap on North
        --
        Plant: 5 4
        Rover: 0 0 N
        Commands: FFFFFF
        --
        Rover: 0 2 N
     */
    println("example3")

  def example4(): Unit =
    /*
        Title: wrap on South
        --
        Plant: 5 4
        Rover: 0 0 S
        Commands: FFFFFFF
        --
        Rover: 0 3 S
     */
    println("example4")

  def example5(): Unit =
    /*
        Title: wrap on Est
        --
        Plant: 5 4
        Rover: 0 0 E
        Commands: FFFFF
        --
        Rover: 0 0 E
     */
    println("example5")

  def example6(): Unit =
    /*
        Title: wrap on West
        --
        Plant: 5 4
        Rover: 0 0 W
        Commands: FF
        --
        Rover: 3 0 W
     */
    println("example6")

  def example7(): Unit =
    /*
        Title: opposite angle
        --
        Plant: 5 4
        Rover: 0 0 N
        Commands: RBBLBRF
        --
        Rover: 4 3 E
     */
    println("example7")

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

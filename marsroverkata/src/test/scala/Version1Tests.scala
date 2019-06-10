package marsroverkata

import minitest._

import marsroverkata.Workspace._

object Version1Tests extends SimpleTestSuite {
  test("three times right") {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: RRR
        --
        Rover: 0 0 W
     */
    ignore()
  }

  test("two times left") {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: LL
        --
        Rover: 0 0 S
     */
    ignore()
  }

  test("wrap on North") {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: FFFFFF
        --
        Rover: 0 2 N
     */
    ignore()
  }

  test("wrap on South") {
    /*
        Planet: 5 4
        Rover: 0 0 S
        Commands: FFFFFFF
        --
        Rover: 0 3 S
     */
    ignore()
  }

  test("wrap on Est") {
    /*
        Planet: 5 4
        Rover: 0 0 E
        Commands: FFFFF
        --
        Rover: 0 0 E
     */
    ignore()
  }

  test("wrap on West") {
    /*
        Planet: 5 4
        Rover: 0 0 W
        Commands: FF
        --
        Rover: 3 0 W
     */
    ignore()
  }

  test("opposite angle") {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: RBBLBRF
        --
        Rover: 4 3 E
     */
    ignore()
  }
}

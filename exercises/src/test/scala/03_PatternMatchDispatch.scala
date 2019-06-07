package exercises

import minitest._

/*
 * Pattern match enable the structural recursion
 * a fancy name to express a way to dispatch logic
 * by type and data. It goes hand in hand with ADT
 * specially Sum Type. Think, how we can implement
 * some special logic `foo` for an "exclusive-or"
 * data type?
 */

object PatternMatchDispatch extends SimpleTestSuite {

  /*
   * TODO: rewrite the dispatch logic
   *       from polymorphic dispatch (a fundamental OOP technique)
   *       to pattern match dispatch.
   *       Keep tests green.
   */

  trait Direction {
    def turnRight: Direction
    def turnLeft: Direction
  }
  case class N() extends Direction {
    def turnRight: Direction = E()
    def turnLeft: Direction  = W()
  }
  case class E() extends Direction {
    def turnRight: Direction = S()
    def turnLeft: Direction  = N()
  }
  case class W() extends Direction {
    def turnRight: Direction = N()
    def turnLeft: Direction  = S()
  }
  case class S() extends Direction {
    def turnRight: Direction = W()
    def turnLeft: Direction  = E()
  }

  test("turn right") {
    assertEquals(N().turnRight, E())
    assertEquals(E().turnRight, S())
    assertEquals(S().turnRight, W())
    assertEquals(W().turnRight, N())
  }

  test("turn left") {
    assertEquals(N().turnLeft, W())
    assertEquals(W().turnLeft, S())
    assertEquals(S().turnLeft, E())
    assertEquals(E().turnLeft, N())
  }
}

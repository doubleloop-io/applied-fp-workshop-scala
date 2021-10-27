package firststeps

/*
 * Multiple dispatch is a feature in which a function can be dispatched
 * based on the type of one of its arguments.
 * Pattern match enable the structural recursion
 * a fancy name to express a way to dispatch logic
 * by type and data. It goes hand in hand with ADT
 * specially Sum Type.
 */

class MultipleDispatch extends munit.FunSuite {

  /*
   * TODO: rewrite the logic
   *       from polymorphic dispatch (OOP inheritance)
   *       to pattern match dispatch.
   */

  trait TrafficLight {
    def next: TrafficLight
  }
  case class Red()    extends TrafficLight {
    def next: TrafficLight = Green()
  }
  case class Green()  extends TrafficLight {
    def next: TrafficLight = Yellow()
  }
  case class Yellow() extends TrafficLight {
    def next: TrafficLight = Red()
  }

  test("turn right") {
    assertEquals(Red().next, Green())
    assertEquals(Green().next, Yellow())
    assertEquals(Yellow().next, Red())
  }
}

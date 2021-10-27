package firststeps.answers

class MultipleDispatch extends munit.FunSuite {

  sealed trait TrafficLight {
    def next: TrafficLight = this match {
      case Red()    => Green()
      case Green()  => Yellow()
      case Yellow() => Red()
    }
  }
  case class Red()    extends TrafficLight
  case class Green()  extends TrafficLight
  case class Yellow() extends TrafficLight

  test("next") {
    assertEquals(Red().next, Green())
    assertEquals(Green().next, Yellow())
    assertEquals(Yellow().next, Red())
  }

  // Alternative style, same implementation
  // but place it in the companion object
  object TrafficLight {

    def next(current: TrafficLight): TrafficLight =
      current match {
        case Red()    => Green()
        case Green()  => Yellow()
        case Yellow() => Red()
      }
  }

  test("next - alternative") {
    assertEquals(TrafficLight.next(Red()), Green())
    assertEquals(TrafficLight.next(Green()), Yellow())
    assertEquals(TrafficLight.next(Yellow()), Red())
  }
}

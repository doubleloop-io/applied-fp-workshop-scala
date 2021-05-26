package exercises.answers

class PatternMatchDispatch extends munit.FunSuite {

  sealed trait Direction {
    def turnRight: Direction = this match {
      case N() => E()
      case E() => S()
      case S() => W()
      case W() => N()
    }
    def turnLeft: Direction  = this match {
      case N() => W()
      case W() => S()
      case S() => E()
      case E() => N()
    }
  }
  case class N() extends Direction
  case class E() extends Direction
  case class W() extends Direction
  case class S() extends Direction

  // Alternative style, same implementation
  // but place it in the companion object
  object Direction {

    def turnRight(current: Direction): Direction =
      current match {
        case N() => E()
        case E() => S()
        case S() => W()
        case W() => N()
      }

    def turnLeft(current: Direction): Direction =
      current match {
        case N() => W()
        case W() => S()
        case S() => E()
        case E() => N()
      }
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

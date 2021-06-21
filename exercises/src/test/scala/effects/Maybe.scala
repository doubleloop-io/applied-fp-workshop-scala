package effects

class Maybe extends munit.FunSuite {

  sealed trait Maybe[A] {

    def map[B](f: A => B): Maybe[B] = ???

    def flatMap[B](f: A => Maybe[B]): Maybe[B] = ???

    def runOr(default: => A): A = ???
  }

  case class Yes[A](value: A) extends Maybe[A]

  case class Nope[A]() extends Maybe[A]

  object Maybe {
    def pure[A](a: A): Maybe[A] = ???
  }

  def increment(x: Int): Int =
    x + 1

  def reversedString(x: Int): Maybe[String] =
    Maybe.pure(x.toString.reverse)

  test("lift a value and run the effect".ignore) {
    // TODO: implement 'pure' function
    val c = Maybe
      .pure(10)

    assertEquals(c.runOr(100), 10)
  }

  test("chain pure function".ignore) {
    // TODO: implement 'map' function
    val c = Maybe
      .pure(10)
      .map(increment)

    assertEquals(c.runOr(100), 11)
  }

  test("chain effectful function".ignore) {
    // TODO: implement 'flatMap' function
    val c = Maybe
      .pure(10)
      .flatMap(reversedString)

    assertEquals(c.runOr("100"), "01")
  }
}

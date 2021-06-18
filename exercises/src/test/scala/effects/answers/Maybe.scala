package effects.answers

class Maybe extends munit.FunSuite {

  sealed trait Maybe[A] {

    def map[B](f: A => B): Maybe[B] =
      this match {
        case Yes(value) => Yes(f(value))
        case Nope()     => Nope()
      }

    def flatMap[B](f: A => Maybe[B]): Maybe[B] =
      this match {
        case Yes(value) => f(value)
        case Nope()     => Nope()
      }

    def runOr(default: => A): A =
      this match {
        case Yes(value) => value
        case Nope()     => default
      }
  }
  case class Yes[A](value: A) extends Maybe[A]
  case class Nope[A]()        extends Maybe[A]

  object Maybe {
    def pure[A](a: A): Maybe[A] =
      if (a == null) Nope() else Yes(a)
  }

  def increment(x: Int): Int =
    x + 1

  def reversedString(x: Int): Maybe[String] =
    Maybe.pure(x.toString.reverse)

  test("lift a value and run the effect") {
    val c = Maybe
      .pure(10)

    assertEquals(c.runOr(100), 10)
  }

  test("chain pure function") {
    val c = Maybe
      .pure(10)
      .map(increment)

    assertEquals(c.runOr(100), 11)
  }

  test("chain effectful function") {
    val c = Maybe
      .pure(10)
      .flatMap(reversedString)

    assertEquals(c.runOr("100"), "01")
  }
}

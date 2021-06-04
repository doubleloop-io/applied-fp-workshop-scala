package exercises

class LazyTests extends munit.FunSuite {

  /*
   * TODO: Follow the instruction in the ignores
   *
   * ADD YOUR CODE HERE INSIDE THE TEST OBJECT
   */

  case class Lazy[A](value: () => A) {
    def map[B](f: A => B): Lazy[B] = ???

    def flatMap[B](f: A => Lazy[B]): Lazy[B] = ???
  }

  object Lazy {
    def pure[A](a: () => A): Lazy[A] = ???
  }

  def expensiveComputation() =
    6 + 4

  def increment(x: Int): Int =
    x + 1

  def reversedString(x: Int): Lazy[String] =
    Lazy.pure(() => x.toString.reverse)

  test("lift a value into a container".ignore) {
    // TODO: implement 'pure' function
    val c = Lazy
      .pure(expensiveComputation _)

    assertEquals(c.value(), 10)
  }

  test("chain not container-aware functions".ignore) {
    // TODO: implement 'map' function
    val c = Lazy
      .pure(expensiveComputation _)
      .map(increment)

    assertEquals(c.value(), 11)
  }

  test("chain container-aware functions".ignore) {
    // TODO: implement 'flatMap' function
    val c = Lazy
      .pure(expensiveComputation _)
      .flatMap(reversedString)

    assertEquals(c.value(), "01")
  }
}

package exercises

class CustomOption extends munit.FunSuite {

  // TODO: remove ignores

  enum Option[A] {
    case Yes[A](value: A) extends Option[A]
    case Nope[A]() extends Option[A]

    // TODO: implement map function
    def map[B](f: A => B): Option[B] = ???

    // TODO: implement flatMap function
    def flatMap[B](f: A => Option[B]): Option[B] = ???

    // TODO: implement fold function
    def fold[B](default: => B, to: A => B): B = ???
  }

  object Option {
    // TODO: implement pure function
    def pure[A](a: A): Option[A] = ???
  }

  def increment(x: Int): Int =
    x + 1

  def reverseString(x: Int): Option[String] =
    Option.pure(x.toString.reverse)

  test("creation phase".ignore) {
    val result = Option
      .pure(10)

    assertEquals(result, Option.Yes(10))
  }

  test("combination phase - normal".ignore) {
    val result = Option
      .Yes(10)
      .map(increment)

    assertEquals(result, Option.Yes(11))
  }

  test("combination phase - effectful".ignore) {
    val result = Option
      .Yes(10)
      .flatMap(reverseString)

    assertEquals(result, Option.Yes("01"))
  }

  test("removal phase - value".ignore) {
    val result = Option
      .Yes(10)
      .fold("nope", _.toString)

    assertEquals(result, "10")
  }

  test("removal phase - alternative value".ignore) {
    val result = Option
      .Nope()
      .fold("nope", _.toString)

    assertEquals(result, "nope")
  }
}

package exercises.answers

class CustomOption extends munit.FunSuite {

  enum Option[A] {
    case Yes[A](value: A) extends Option[A]
    case Nope[A]() extends Option[A]

    def map[B](f: A => B): Option[B] =
      this match {
        case Yes(value) => Yes(f(value))
        case Nope()     => Nope()
      }

    def flatMap[B](f: A => Option[B]): Option[B] =
      this match {
        case Yes(value) => f(value)
        case Nope()     => Nope()
      }

    def fold[B](default: => B, to: A => B): B =
      this match {
        case Yes(value) => to(value)
        case Nope()     => default
      }
  }

  object Option {
    def pure[A](a: A): Option[A] = Yes(a)
  }

  def increment(x: Int): Int =
    x + 1

  def reverseString(x: Int): Option[String] =
    Option.pure(x.toString.reverse)

  test("creation phase") {
    val result = Option
      .pure(10)

    assertEquals(result, Option.Yes(10))
  }

  test("combination phase - normal") {
    val result = Option
      .Yes(10)
      .map(increment)

    assertEquals(result, Option.Yes(11))
  }

  test("combination phase - effectful") {
    val result = Option
      .Yes(10)
      .flatMap(reverseString)

    assertEquals(result, Option.Yes("01"))
  }

  test("removal phase - value") {
    val result = Option
      .Yes(10)
      .fold("nope", _.toString)

    assertEquals(result, "10")
  }

  test("removal phase - alternative value") {
    val result = Option
      .Nope()
      .fold("nope", _.toString)

    assertEquals(result, "nope")
  }
}

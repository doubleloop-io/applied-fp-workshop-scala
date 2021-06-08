package effects.answers

class BasicsTests extends munit.FunSuite {

  case class Container[A](value: A) {
    def chain[B](f: A => B): Container[B] =
      Container.of(f(value))
  }

  object Container {
    def of[A](a: A): Container[A] = Container(a)
  }

  test("lift a value into a container") {
    val c = Container.of(10)

    assertEquals(c, Container(10))
  }

  def doubles(x: Int): Int       = x * x
  def asString(x: Int): String   = x.toString
  def reverse(x: String): String = x.reverse

  test("chain not container-aware functions") {
    val c = Container
      .of(10)
      .chain(doubles)
      .chain(asString)
      .chain(reverse)

    assertEquals(c, Container("001"))
  }
}

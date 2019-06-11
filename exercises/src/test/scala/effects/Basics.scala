package exercises

import minitest._

object BasicsTests extends SimpleTestSuite {

  case class Container[A](value: A) {
    def chain[B](f: A => B): Container[B] = ???
  }

  object Container {
    def of[A](a: A): Container[A] = ???
  }

  test("lift a value into a container") {
    ignore("implement 'of' function")
    val c = Container.of(10)

    assertEquals(c, Container(10))
  }

  def doubles(x: Int): Int       = x * x
  def asString(x: Int): String   = x.toString
  def reverse(x: String): String = x.reverse

  test("chain not container-aware functions") {
    ignore("implement 'chain' function")
    val c = Container
      .of(10)
      .chain(doubles)
      .chain(asString)
      .chain(reverse)

    assertEquals(c, Container("001"))
  }
}

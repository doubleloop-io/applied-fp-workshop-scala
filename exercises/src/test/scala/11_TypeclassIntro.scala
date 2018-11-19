package exercises

import minitest._

/*
 * Type classes are a programming pattern originating in Haskell.
 * They are a way to extends program w/out change it.
 * We can simplify the context and say that it is a different way to gain
 * dynamic dispatch polymorphism for statically typed language.
 *
 * The main difference is that in OOP a class implements an interface
 * in order to access to it's internal state.
 * Here, in FP, state and behaviour are separated so we need
 * a different strategy, enter the Type class pattern.
 *
 * The final payoff is that thanks to them we can write more
 * generic/abstract logic and reuse it for different types.
 */

object TypeclassIntroTests extends SimpleTestSuite {

  /*
   * TODO: remove ignores and
   *       implements functions maked with `???`
   */

  object Concrete {
    case class BoxInt(value: Int)
    case class BoxString(value: String)

    def sumInt(a: BoxInt, b: BoxInt): BoxInt =
      BoxInt(a.value + b.value)

    def sumString(a: BoxString, b: BoxString): BoxString =
      BoxString(a.value + b.value)
  }

  test("create boxes") {
    import Concrete._

    assertEquals(BoxInt(42).value, 42)
    assertEquals(BoxString("foo").value, "foo")
  }

  test("sum boxes") {
    import Concrete._

    assertEquals(sumInt(BoxInt(42), BoxInt(100)).value, 142)
    assertEquals(sumString(BoxString("foo"), BoxString("bar")).value, "foobar")
  }

  object Polymorphic {

    case class Box[A](value: A)

    def sum[A](a: Box[A], b: Box[A]): Box[A] =
      ???
  }

  test("create boxes - polymorphic") {
    import Polymorphic._

    assertEquals(Box(42).value, 42)
    assertEquals(Box("foo").value, "foo")
  }

  test("sum boxes - polymorphic") {
    import Polymorphic._

    ignore("implements sum[A] function")

    assertEquals(sum(Box(42), Box(100)).value, 142)
    assertEquals(sum(Box("foo"), Box("bar")).value, "foobar")
  }

}

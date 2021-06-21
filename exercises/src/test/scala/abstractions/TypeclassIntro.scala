package abstractions

/*
 * Type classes are a programming pattern originating in Haskell.
 * They are a way to extends program w/out change it.
 * We can simplify the context and say that it is a different way to gain
 * dynamic dispatch polymorphism for statically typed language.
 *
 * The main difference is that in OOP a class implements an interface
 * in order to access to it's internal state.
 * In FP, state and behaviour are separated so we need
 * a different strategy, enter the Type class pattern.
 *
 * The final payoff is that thanks to them we can write more
 * generic/abstract logic and reuse it for different types.
 */

class TypeclassIntro extends munit.FunSuite {

  object Concrete {
    case class BoxInt(value: Int)
    case class BoxString(value: String)

    def sumInt(a: BoxInt, b: BoxInt): BoxInt =
      BoxInt(a.value + b.value)

    def sumString(a: BoxString, b: BoxString): BoxString =
      BoxString(a.value + b.value)
  }

  test("sum boxes") {
    import Concrete._

    assertEquals(sumInt(BoxInt(42), BoxInt(100)).value, 142)
    assertEquals(sumString(BoxString("foo"), BoxString("bar")).value, "foobar")
  }

  object Polymorphic {

    case class Box[A](value: A)

    // TODO: implements sum[A] function
    def sum[A](a: Box[A], b: Box[A]): Box[A] =
      ???
  }

  test("sum boxes - polymorphic".ignore) {
    import Polymorphic._

    assertEquals(sum(Box(42), Box(100)).value, 142)
    assertEquals(sum(Box("foo"), Box("bar")).value, "foobar")
  }

  object PolymorphicImplicits {

    case class Box[A](value: A)

    // TODO: implements sum[A] function like in Polymorphic
    //       but with implicit parameters
    def sum[A](a: Box[A], b: Box[A]): Box[A] =
      ???
  }

  test("sum boxes - polymorphic w/ implicits".ignore) {
    import PolymorphicImplicits._

    assertEquals(sum(Box(42), Box(100)).value, 142)
    assertEquals(sum(Box("foo"), Box("bar")).value, "foobar")
  }

}

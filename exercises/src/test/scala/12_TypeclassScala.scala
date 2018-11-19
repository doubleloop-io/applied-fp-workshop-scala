package exercises

import minitest._

/*
 * The Type classes pattern is composed by two parts:
 * - type class (abstract contract definition)
 * - type class instances (concrete contract implementations)
 *
 * This pattern is not native to Scala. In order to
 * use it more smoothly we need to add some other
 * artifacts that fit thogheter thanks to Scala's
 * implicit features. Those artifacts are:
 * - implicit parameter/value
 * - interface object
 * - interface syntax
 */

object TypeclassScalaTests extends SimpleTestSuite {

  /*
   * TODO: remove ignores
   */

  case class Box[A](value: A)

  trait Semigroup[A] {
    def combine(a: A, b: A): A
  }

  val intSemigroup = new Semigroup[Int] {
    def combine(a: Int, b: Int): Int = a + b
  }

  object Semigroup {
    def apply[A]: Semigroup[A] = ???
  }

  implicit class SemigroupOps[A](a: A) {
    def combine(b: A): A =
      ???
  }

  test("implicit parameter/value") {
    def sum[A](a: Box[A], b: Box[A])(s: Semigroup[A]): Box[A] =
      Box[A](s.combine(a.value, b.value))

    ignore("let sum accept Semigroup via implicit parameter")
    assertEquals(sum(Box(42), Box(100))(intSemigroup).value, 142)
  }

  test("interface object") {
    def sum[A](a: Box[A], b: Box[A]): Box[A] =
      Box[A](Semigroup[A].combine(a.value, b.value))

    ignore("add constraint to the generic parameter A")
    ignore("implement Semigroup.apply")
    assertEquals(sum(Box(42), Box(100)).value, 142)
  }

  test("interface syntax") {
    def sum[A](a: Box[A], b: Box[A]): Box[A] =
      Box[A](a.value.combine(b.value))

    ignore("add constraint to the generic parameter A")
    ignore("implement SemigroupOps.combine")
    assertEquals(sum(Box(42), Box(100)).value, 142)
  }
}

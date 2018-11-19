package exercises.answers

import minitest._

/*
 * For a happy fp programmin in Scala we need
 * to add all these typeclasses and many more
 * stuff so we need the help of a library,
 * we need Cats.
 * https://typelevel.org/cats/
 *
 *
 * The following importe are required to use cats:
 * - type class and interface object: cats.Functor, cats.Semigroup
 * - type class instance: cats.instances.list._, cats.instances.int._
 * - (optional) interface syntax: cats.syntax.functor._, cats.syntax.semigroup._
 *
 * For examble if you want concatenate two strings:
 *
 * import cats.Semigroup
 * import cats.instances.string._
 *
 * val result = Semigroup[String].combine("foo", "bar")
 */

object TypeclassCatsTests extends SimpleTestSuite {

  case class Box[A](value: A)

  test("implicit parameter/value") {
    import cats.Semigroup
    import cats.instances.int._

    def sum[A](a: Box[A], b: Box[A])(implicit s: Semigroup[A]): Box[A] =
      Box[A](s.combine(a.value, b.value))

    assertEquals(sum(Box(42), Box(100)).value, 142)
  }

  test("interface object") {
    import cats.Semigroup
    import cats.instances.int._

    def sum[A: Semigroup](a: Box[A], b: Box[A]): Box[A] =
      Box[A](Semigroup[A].combine(a.value, b.value))

    assertEquals(sum(Box(42), Box(100)).value, 142)
  }

  test("interface syntax") {
    import cats.Semigroup
    import cats.instances.int._
    import cats.syntax.semigroup._

    def sum[A: Semigroup](a: Box[A], b: Box[A]): Box[A] =
      Box[A](a.value.combine(b.value))

    assertEquals(sum(Box(42), Box(100)).value, 142)
  }
}

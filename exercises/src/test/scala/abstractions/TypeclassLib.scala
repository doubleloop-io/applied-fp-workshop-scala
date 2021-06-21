package abstractions

/*
 * For a happy fp programming in Scala we need
 * to add all these type classes and many more
 * stuff so we need the help of a library,
 * we need Cats.
 * https://typelevel.org/cats/
 *
 *
 * The following import are required to use cats:
 * - type class and interface object: cats.Semigroup, cats.Functor
 * - type class instance: cats.instances.list._, cats.instances.int._
 */

class TypeclassLib extends munit.FunSuite {

  case class Box[A](value: A)

  // Typeclass (contract)
  import cats.Semigroup

  def sum[A](a: Box[A], b: Box[A])(implicit s: Semigroup[A]): Box[A] =
    Box[A](s.combine(a.value, b.value))

  test("combine two int") {
    // Typeclasses instances for int
    import cats.instances.int._
    assertEquals(sum(Box(42), Box(100)).value, 142)
  }

  test("combine two string") {
    // TODO: add proper import & uncomment the rest
    // assertEquals(sum(Box("foo"), Box("bar")).value, "foobar")
  }

  import cats.Monoid

  def sumAll[A](l: List[A])(implicit M: Monoid[A]): A =
    l.foldLeft(Monoid[A].empty)(Monoid[A].combine)

  test("reduce a list of int") {
    import cats.instances.int._
    assertEquals(6, sumAll(List(1, 2, 3)))
  }

  test("reduce a list of string") {
    // TODO: add proper import & uncomment the rest
    // assertEquals("abc", sumAll(List("a", "b", "c")))
  }
}

package abstractions.answers

class TypeclassLib extends munit.FunSuite {

  case class Box[A](value: A)

  import cats.Semigroup

  def sum[A](a: Box[A], b: Box[A])(implicit s: Semigroup[A]): Box[A] =
    Box[A](s.combine(a.value, b.value))

  test("combine two int") {
    import cats.instances.int._
    assertEquals(sum(Box(42), Box(100)).value, 142)
  }

  test("combine two string") {
    import cats.instances.string._
    assertEquals(sum(Box("foo"), Box("bar")).value, "foobar")
  }

  import cats.Monoid

  def sumAll[A](l: List[A])(implicit M: Monoid[A]): A =
    l.foldLeft(Monoid[A].empty)(Monoid[A].combine)

  test("reduce a list of int") {
    import cats.instances.int._
    assertEquals(6, sumAll(List(1, 2, 3)))
  }

  test("reduce a list of string") {
    import cats.instances.string._
    assertEquals("abc", sumAll(List("a", "b", "c")))
  }
}

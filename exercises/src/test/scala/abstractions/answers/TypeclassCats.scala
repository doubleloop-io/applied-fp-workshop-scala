package abstractions.answers

class TypeclassCatsTests extends munit.FunSuite {

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

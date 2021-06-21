package abstractions

/*
 * Typeclasses instances can be combined in the same way
 * that we can combine types.
 * */
class TypeclassRicher extends munit.FunSuite {

  import cats.Semigroup

  test("combine monadic data types".ignore) {
    import cats.instances.int._
    import cats.instances.option._

    // The instance of Semigroup for Option
    // reuse the instance for Int type
    val S      = Semigroup[Option[Int]]
    val result = S.combine(Some(2), Some(5))

    assertEquals(???, result)
  }

  test("combine data structures".ignore) {
    import cats.instances.map._

    val S      = Semigroup[Map[String, Int]]
    val result = S.combine(Map("a" -> 1, "b" -> 2), Map("b" -> 1, "c" -> 3))

    assertEquals(???, result)
  }

  case class Item(price: Double, qty: Int)

  // We can define custom Typeclass instance for our types
  def mkItemSemigroup(implicit SI: Semigroup[Int], SD: Semigroup[Double]): Semigroup[Item] = new Semigroup[Item] {

    // TODO: implement the Item combination logic.
    // follow the types declaration and reuse the instances
    // passed as implicit parameters
    def combine(x: Item, y: Item): Item =
      ???
  }

  test("combine custom structures".ignore) {
    import cats.instances.double._
    import cats.instances.int._

    val S      = mkItemSemigroup
    val result = S.combine(Item(1.23, 5), Item(3.50, 1))

    assertEquals(Item(4.73, 6), result)
  }

}

package abstractions.answers

class TypeclassRicher extends munit.FunSuite {

  import cats.Semigroup

  test("combine monadic data types") {
    import cats.instances.int._
    import cats.instances.option._

    val S      = Semigroup[Option[Int]]
    val result = S.combine(Some(2), Some(5))

    assertEquals(Option(7), result)
  }

  test("combine data structures") {
    import cats.instances.map._

    val S      = Semigroup[Map[String, Int]]
    val result = S.combine(Map("a" -> 1, "b" -> 2), Map("b" -> 1, "c" -> 3))

    assertEquals(Map("a" -> 1, "b" -> 3, "c" -> 3), result)
  }

  case class Item(price: Double, qty: Int)

  def mkItemSemigroup(implicit SI: Semigroup[Int], SD: Semigroup[Double]): Semigroup[Item] = new Semigroup[Item] {
    def combine(x: Item, y: Item): Item =
      Item(SD.combine(x.price, y.price), SI.combine(x.qty, y.qty))
  }

  test("combine custom structures") {
    import cats.instances.int._
    import cats.instances.double._

    val S      = mkItemSemigroup
    val result = S.combine(Item(1.23, 5), Item(3.50, 1))

    assertEquals(Item(4.73, 6), result)
  }

}

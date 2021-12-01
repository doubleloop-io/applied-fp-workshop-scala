package effects

class Foldable extends munit.FunSuite {

  case class Item(qty: Int)

  test("total qty".ignore) {
    val items       = List(Item(100), Item(10), Item(45))
    // TODO: use foldLeft to make test green
    val result: Int = ???
    assertEquals(result, 155)
  }

  test("reduce to single total item".ignore) {
    def checkIn(item: Item, qty: Int): Item =
      item.copy(qty = item.qty + qty)

    val items        = List(Item(100), Item(10), Item(45))
    // TODO: use foldLeft to make test green
    val result: Item = ???
    assertEquals(result, Item(155))
  }
}

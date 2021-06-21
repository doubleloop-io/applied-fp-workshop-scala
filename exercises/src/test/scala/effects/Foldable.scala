package effects

class Foldable extends munit.FunSuite {

  case class Item(qty: Int)

  def checkIn(item: Item, qty: Int): Item =
    item.copy(qty = item.qty + qty)

  test("total qty".ignore) {
    val items = List(Item(100), Item(10), Item(45))
    // TODO: use foldLeft to make test green
    val result = ???
    assertEquals(result, 155)
  }

  test("reduce to single total item".ignore) {
    val items = List(Item(100), Item(10), Item(45))
    // TODO: use foldLeft to make test green
    val result = ???
    assertEquals(result, Item(155))
  }
}

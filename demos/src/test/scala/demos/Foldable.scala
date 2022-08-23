package demos

class FoldableDemo extends munit.FunSuite {

  case class Item(qty: Int)

  test("total qty") {
    val items = List(Item(100), Item(10), Item(45))
    val result = items.foldLeft(0)(_ + _.qty)
    assertEquals(result, 155)
  }

  test("reduce to single total item") {
    def checkIn(item: Item, qty: Int): Item =
      item.copy(qty = item.qty + qty)

    val items = List(Item(100), Item(10), Item(45))
    val result = items.foldLeft(Item(0))((acc, cur) => checkIn(acc, cur.qty))
    assertEquals(result, Item(155))
  }
}

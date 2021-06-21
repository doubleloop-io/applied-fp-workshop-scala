package effects.answers

class Traverse extends munit.FunSuite {

  import cats.implicits._

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  test("all valid creation - individual result with map") {
    val values = List("1", "10", "100")
    val items  = values.map(createItem)
    assertEquals(items, List(Some(Item(1)), Some(Item(10)), Some(Item(100))))
  }

  test("some invalid creation - individual result with map") {
    val values = List("1", "asf", "100")
    val items  = values.map(createItem)
    assertEquals(items, List(Some(Item(1)), None, Some(Item(100))))
  }

  test("all valid creation - omni result with traverse") {
    val values = List("1", "10", "100")
    val items  = values.traverse(createItem)
    assertEquals(items, Some(List(Item(1), Item(10), Item(100))))
  }

  test("some invalid creation - omni result with traverse") {
    val values = List("1", "asd", "100")
    val items  = values.traverse(createItem)
    assertEquals(items, None)
  }
}

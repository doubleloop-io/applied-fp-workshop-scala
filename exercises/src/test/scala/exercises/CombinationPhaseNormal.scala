package exercises

// TODO: Remove IgnoreSuite annotation

@munit.IgnoreSuite
class CombinationPhaseNormal extends munit.FunSuite {

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  // NOTE: Option.map function documentation
  // https://www.scala-lang.org/api/3.1.3/scala/Option.html#map-27d

  test("creation and checkIn") {
    val item = createItem("100")
    // TODO: Use map to checkIn 10 items
    val result: Option[Item] = ???
    assertEquals(result, Some(Item(110)))
  }

  test("invalid creation") {
    val item = createItem("asd")
    // TODO: Use map to checkIn 10 items
    val result: Option[Item] = ???
    assertEquals(result, None)
  }

}

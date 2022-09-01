package exercises.answers

@munit.IgnoreSuite
class CombinationPhaseNormal extends munit.FunSuite {

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  test("creation and checkIn") {
    val item = createItem("100")
    val result = item.map(checkIn(10, _))
    assertEquals(result, Some(Item(110)))
  }

  test("invalid creation") {
    val item = createItem("asd")
    val result = item.map(checkIn(10, _))
    assertEquals(result, None)
  }

}

package exercises.answers

@munit.IgnoreSuite
class CombinationPhaseEffectful extends munit.FunSuite {

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  def checkOut(qty: Int, item: Item): Option[Item] =
    if (qty <= item.qty) Some(item.copy(qty = item.qty - qty))
    else None

  test("creation and checkOut") {
    val item = createItem("100")
    val result = item.flatMap(checkOut(10, _))
    assertEquals(result, Some(Item(90)))
  }

  test("creation, checkIn and checkOut") {
    val item = createItem("100")
    val result = item.map(checkIn(10, _)).flatMap(checkOut(20, _))
    assertEquals(result, Some(Item(90)))
  }

  test("invalid checkOut") {
    val item = createItem("100")
    val result = item.flatMap(checkOut(110, _))
    assertEquals(result, None)
  }

  test("invalid creation") {
    val item = createItem("asd")
    val result = item.flatMap(checkOut(10, _))
    assertEquals(result, None)
  }

}

package exercises

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

  // TODO: remove ignores

  // NOTE: Option.flatMap function documentation
  // https://www.scala-lang.org/api/3.1.3/scala/Option.html#flatMap-27d

  test("creation and checkOut".ignore) {
    val item = createItem("100")
    // TODO: use flatMap to checkOut 10 items
    val result: Option[Item] = ???
    assertEquals(result, Some(Item(90)))
  }

  test("creation, checkIn and checkOut".ignore) {
    val item = createItem("100")
    // TODO: use map to checkIn 10 and then flatMap to checkOut 20 items
    val result: Option[Item] = ???
    assertEquals(result, Some(Item(90)))
  }

  test("invalid checkOut".ignore) {
    val item = createItem("100")
    // TODO: use flatMap to checkOut 110 items
    val result: Option[Item] = ???
    assertEquals(result, None)
  }

  test("invalid creation".ignore) {
    val item = createItem("asd")
    // TODO: use flatMap to checkOut 10 items
    val result: Option[Item] = ???
    assertEquals(result, None)
  }

}
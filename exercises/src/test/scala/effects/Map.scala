package effects

class Map extends munit.FunSuite {

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  test("valid creation, can checkIn") {
    val item = createItem("100")
    // TODO: chain checkIn of 10 items and write the assert
  }

  test("invalid creation - map short circuit") {
    val item = createItem("asd")
    // TODO: chain checkIn of 10 items and write the assert
  }
}

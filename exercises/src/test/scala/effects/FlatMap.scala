package effects

class FlatMap extends munit.FunSuite {

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  def checkOut(qty: Int, item: Item): Option[Item] =
    if (qty <= item.qty) Some(item.copy(qty = item.qty - qty))
    else None

  test("valid creation, can checkOut") {
    val item = createItem("100")
    // TODO: chain checkOut of 10 items and write the assert
  }

  test("valid creation, can't checkOut (too much) - flatMap short circuit") {
    val item = createItem("100")
    // TODO: chain checkOut of 110 items and write the assert
  }

  test("invalid creation - flatMap short circuit") {
    val item = createItem("asd")
    // TODO: chain checkOut of 10 items and write the assert
  }
}

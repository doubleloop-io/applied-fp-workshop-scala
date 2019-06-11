package exercises

import minitest._

object MapTests extends SimpleTestSuite {

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  test("valid creation, can checkIn") {
    val item = createItem("100")
    ignore("chain checkIn of 10 items and write the assert")
  }

  test("invalid creation - map short circuit") {
    val item = createItem("asd")
    ignore("chain checkIn of 10 items and write the assert")
  }
}

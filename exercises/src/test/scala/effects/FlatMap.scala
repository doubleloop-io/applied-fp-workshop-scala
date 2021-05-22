package exercises

class FlatMapTests extends munit.FunSuite {

  /*
   * TODO: Follow the instruction in the ignores
   *
   * ADD YOUR CODE HERE INSIDE THE TEST OBJECT
   */

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  def checkOut(qty: Int, item: Item): Option[Item] =
    if (qty <= item.qty) Some(item.copy(qty = item.qty - qty))
    else None

  test("valid creation, can checkOut".ignore) {
    val item = createItem("100")
    // TODO: ingore(chain checkOut of 10 items and write the assert")
  }

  test("valid creation, can't checkOut (too much) - flatMap short circuit".ignore) {
    val item = createItem("100")
    // TODO: ingore(chain checkOut of 110 items and write the assert")
  }

  test("invalid creation - flatMap short circuit".ignore) {
    val item = createItem("asd")
    // TODO: ingore(chain checkOut of 10 items and write the assert")
  }
}

package exercises

class MapTests extends munit.FunSuite {

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

  test("valid creation, can checkIn".ignore) {
    val item = createItem("100")
    // TODO: ingore(chain checkIn of 10 items and write the assert")
  }

  test("invalid creation - map short circuit".ignore) {
    val item = createItem("asd")
    // TODO: ingore(chain checkIn of 10 items and write the assert")
  }
}

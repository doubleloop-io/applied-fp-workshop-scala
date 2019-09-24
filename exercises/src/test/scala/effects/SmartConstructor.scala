package exercises

import minitest._

object SmartConstructorTests extends SimpleTestSuite {

  /*
   * TODO: Follow the instruction in the ignores
   *
   * ADD YOUR CODE HERE INSIDE THE TEST OBJECT
   */

  case class Item(qty: Int)

  def createItem(qty: String): Item =
    if (qty.matches("^[0-9]+$")) Item(qty.toInt)
    else null

  test("valid") {
    ignore("remove null with a custom container w/ valid state")
    assertEquals(createItem("100"), Item(100))
  }

  test("invalid") {
    ignore("remove null with a custom container w/ invalid state")
    assertEquals(createItem("asd"), null)
    assertEquals(createItem("1 0 0"), null)
    assertEquals(createItem(""), null)
  }
}

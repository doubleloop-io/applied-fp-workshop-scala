package exercises

import minitest._

object TraverseTests extends SimpleTestSuite {

  /*
   * TODO: Follow the instruction in the ignores
   *
   * ADD YOUR CODE HERE INSIDE THE OBJECT
   */

  import cats.implicits._

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  test("all valid creation - idividual result with map") {
    val values = List("1", "10", "100")
    val items  = values.map(createItem)
    ignore("write the assert")
  }

  test("some invalid creation - idividual result with map") {
    val values = List("1", "asf", "100")
    val items  = values.map(createItem)
    ignore("write the assert")
  }

  test("all valid creation - omni result with traverse") {
    val values = List("1", "10", "100")
    val items  = values.traverse(createItem)
    ignore("write the assert")
  }

  test("some invalid creation - omni result with traverse") {
    val values = List("1", "asd", "100")
    val items  = values.traverse(createItem)
    ignore("write the assert")
  }
}

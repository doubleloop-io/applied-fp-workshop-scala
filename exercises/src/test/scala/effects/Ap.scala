package exercises

import minitest._

object ApTests extends SimpleTestSuite {

  /*
   * TODO: Follow the instruction in the ignores
   *
   * ADD YOUR CODE HERE INSIDE THE OBJECT
   */

  import cats.implicits._

  case class Item(name: String, qty: Int)

  def checkName(value: String): Option[String] =
    if (!value.isEmpty) Some(value)
    else None

  def checkQty(qty: String): Option[Int] =
    if (qty.matches("^[0-9]+$")) Some(qty.toInt)
    else None

  def createItem(name: String, qty: String): Option[Item] =
    (checkName(name), checkQty(qty)).mapN(Item.apply)

  test("valid creation") {
    val item = createItem("foo", "100")
    ignore("write the assert")
  }

  test("invalid creation (name)") {
    val item = createItem("", "100")
    ignore("write the assert")
  }

  test("invalid creation (qty)") {
    val item = createItem("foo", "asd")
    ignore("write the assert")
  }

  test("invalid creation (both)") {
    val item = createItem("", "asd")
    ignore("write the assert")
  }
}

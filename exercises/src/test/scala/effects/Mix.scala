package exercises

import minitest._

object MixTests extends SimpleTestSuite {

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

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  def checkOut(qty: Int, item: Item): Option[Item] =
    if (qty <= item.qty) Some(item.copy(qty = item.qty - qty))
    else None

  def complex(values: List[(String, String)]): String =
    // create items from values
    // checkIn 10 to each items
    // checkOut 15 to each items
    // sum all items
    // render a string
    ???

  test("complex scenario - ok") {
    ignore("implement complex function")
    val values = List(("foo", "100"), ("bar", "10"))
    val result = complex(values)
    assertEquals(result, "130")
  }

  test("complex scenario - bad name") {
    ignore("implement complex function")
    val values = List(("", "100"), ("bar", "10"))
    val result = complex(values)
    assertEquals(result, "we can't, sorry")
  }

  test("complex scenario - bad qty") {
    ignore("implement complex function")
    val values = List(("foo", "100"), ("bar", "zanzan"))
    val result = complex(values)
    assertEquals(result, "we can't, sorry")
  }

}

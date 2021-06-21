package effects.answers

class MixEmAll extends munit.FunSuite {

  import cats.implicits._

  case class Item(name: String, qty: Int)

  def checkName(value: String): Option[String] =
    if (value.nonEmpty) Some(value)
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

  def complex(values: List[(String, String)]): String = {
    // create items from values
    val a: Option[List[Item]] = values.traverse { case (n, q) => createItem(n, q) }
    // checkIn 10 to each items
    val b: Option[List[Item]] = a.map(_.map(checkIn(10, _)))
    // checkOut 15 to each items
    val c: Option[List[Item]] = b.flatMap(_.traverse(checkOut(15, _)))
    // sum all items
    val d: Option[Int] = c.map(_.foldLeft(0)(_ + _.qty))
    // render a string
    val e: String = d.fold("we can't, sorry")(_.toString)
    e
  }

  test("complex scenario - ok") {
    val values = List(("foo", "100"), ("bar", "10"))
    val result = complex(values)
    assertEquals(result, "100")
  }

  test("complex scenario - bad name") {
    val values = List(("", "100"), ("bar", "10"))
    val result = complex(values)
    assertEquals(result, "we can't, sorry")
  }

  test("complex scenario - bad qty") {
    val values = List(("foo", "100"), ("bar", "zanzan"))
    val result = complex(values)
    assertEquals(result, "we can't, sorry")
  }

}

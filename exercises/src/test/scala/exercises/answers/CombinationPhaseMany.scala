package exercises.answers

class CombinationPhaseMany extends munit.FunSuite {

  case class Item(name: String, qty: Int)

  def checkName(value: String): Option[String] =
    if (value.nonEmpty) Some(value)
    else None

  def checkQty(qty: String): Option[Int] =
    if (qty.matches("^[0-9]+$")) Some(qty.toInt)
    else None

  def createItem(name: String, qty: String): Option[Item] =
    for {
      n <- checkName(name)
      q <- checkQty(qty)
    } yield Item(n, q)

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  def checkOut(qty: Int, item: Item): Option[Item] =
    if (qty <= item.qty) Some(item.copy(qty = item.qty - qty))
    else None

  test("creation") {
    val item = createItem("foo", "100")
    assertEquals(item, Some(Item("foo", 100)))
  }

  test("creation, checkIn and checkOut") {
    val result =
      for {
        item0 <- createItem("foo", "100")
        item1 = checkIn(10, item0)
        item2 <- checkOut(20, item1)
      } yield item2
    assertEquals(result, Some(Item("foo", 90)))
  }

  test("invalid creation (name)") {
    val item = createItem("", "100")
    assertEquals(item, None)
  }

  test("invalid creation (qty)") {
    val item = createItem("foo", "asd")
    assertEquals(item, None)
  }

  test("invalid creation (both)") {
    val item = createItem("", "asd")
    assertEquals(item, None)
  }
}

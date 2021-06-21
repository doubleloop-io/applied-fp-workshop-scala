package effects.answers

class Ap extends munit.FunSuite {

  import cats.Applicative
  import cats.implicits._

  case class Item(name: String, qty: Int)

  def checkName(value: String): Option[String] =
    if (value.nonEmpty) Some(value)
    else None

  def checkQty(qty: String): Option[Int] =
    if (qty.matches("^[0-9]+$")) Some(qty.toInt)
    else None

  def createItem(name: String, qty: String): Option[Item] =
    Applicative[Option].map2(checkName(name), checkQty(qty))(Item.apply)

  def createItem_withExtension(name: String, qty: String): Option[Item] =
    (checkName(name), checkQty(qty)).mapN(Item.apply)

  test("valid creation") {
    val item = createItem("foo", "100")
    assertEquals(item, Some(Item("foo", 100)))
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

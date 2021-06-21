package effects

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

  test("valid creation".ignore) {
    val item = createItem("foo", "100")
    // TODO: write the assert
  }

  test("invalid creation (name)".ignore) {
    val item = createItem("", "100")
    // TODO: write the assert
  }

  test("invalid creation (qty)".ignore) {
    val item = createItem("foo", "asd")
    // TODO: write the assert
  }

  test("invalid creation (both)".ignore) {
    val item = createItem("", "asd")
    // TODO: write the assert
  }
}

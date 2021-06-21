package effects.answers

class Fold extends munit.FunSuite {

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  test("valid creation") {
    val item   = createItem("100")
    val result = item.fold("alternative value")(_.qty.toString)
    assertEquals(result, "100")
  }

  test("invalid creation") {
    val item   = createItem("asd")
    val result = item.fold("alternative value")(_.qty.toString)
    assertEquals(result, "alternative value")
  }
}

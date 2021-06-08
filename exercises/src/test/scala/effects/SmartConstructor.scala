package effects

class SmartConstructorTests extends munit.FunSuite {

  /*
   * TODO: Follow the instruction in the ignores
   */

  case class Item(qty: Int)

  def createItem(qty: String): Item =
    if (qty.matches("^[0-9]+$")) Item(qty.toInt)
    else null

  test("valid".ignore) {
    // TODO: remove null with a custom type for valid state
    assertEquals(createItem("100"), Item(100))
  }

  test("invalid".ignore) {
    // TODO: remove null with a custom type for invalid state
    assertEquals(createItem("asd"), null)
    assertEquals(createItem("1 0 0"), null)
    assertEquals(createItem(""), null)
  }
}

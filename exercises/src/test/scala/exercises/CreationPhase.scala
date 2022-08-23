package exercises

class CreationPhase extends munit.FunSuite {

  /*
   * TODO: remove null with a custom return type for valid or invalid states
   */

  case class Item(qty: Int)

  def createItem(qty: String): Item =
    if (qty.matches("^[0-9]+$")) Item(qty.toInt)
    else null

  test("creation") {
    assertEquals(createItem("100"), Item(100))
  }

  test("invalid creation") {
    assertEquals(createItem("asd"), null)
    assertEquals(createItem("1 0 0"), null)
    assertEquals(createItem(""), null)
  }

}

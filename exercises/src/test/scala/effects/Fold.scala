package exercises

class FoldTests extends munit.FunSuite {

  /*
   * TODO: Follow the instruction in the ignores
   *
   * ADD YOUR CODE HERE INSIDE THE TEST OBJECT
   */

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  test("valid creation".ignore) {
    val item = createItem("100")
    // TODO: use fold to make test green
    val result = ???
    assertEquals(result, "100")
  }

  test("invalid creation".ignore) {
    val item = createItem("asd")
    // TODO: use fold to make test green
    val result = ???
    assertEquals(result, "alternative value")
  }
}

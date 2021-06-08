package effects

class TraverseTests extends munit.FunSuite {

  /*
   * TODO: Follow the instruction in the ignores
   *
   * ADD YOUR CODE HERE INSIDE THE TEST OBJECT
   */

  import cats.implicits._

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  test("all valid creation - idividual result with map".ignore) {
    val values = List("1", "10", "100")
    val items  = values.map(createItem)
    // TODO: write the assert
  }

  test("some invalid creation - idividual result with map".ignore) {
    val values = List("1", "asf", "100")
    val items  = values.map(createItem)
    // TODO: write the assert
  }

  test("all valid creation - omni result with traverse".ignore) {
    val values = List("1", "10", "100")
    val items  = values.traverse(createItem)
    // TODO: write the assert
  }

  test("some invalid creation - omni result with traverse".ignore) {
    val values = List("1", "asd", "100")
    val items  = values.traverse(createItem)
    // TODO: write the assert
  }
}

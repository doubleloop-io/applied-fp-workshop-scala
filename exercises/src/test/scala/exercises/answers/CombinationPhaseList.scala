package exercises.answers

@munit.IgnoreSuite
class CombinationPhaseList extends munit.FunSuite {

  import cats.syntax.traverse._

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  test("all valid - individual validation results") {
    val values = List("1", "10", "100")
    val items = values.map(createItem)
    assertEquals(items, List(Some(Item(1)), Some(Item(10)), Some(Item(100))))
  }

  test("some invalid - individual validation results") {
    val values = List("1", "asf", "100")
    val items = values.map(createItem)
    assertEquals(items, List(Some(Item(1)), None, Some(Item(100))))
  }

  test("all valid - one omni validation result") {
    val values = List("1", "10", "100")
    val items = values.traverse(createItem)
    assertEquals(items, Some(List(Item(1), Item(10), Item(100))))
  }

  test("some invalid - one omni validation result") {
    val values = List("1", "asd", "100")
    val items = values.traverse(createItem)
    assertEquals(items, None)
  }

}

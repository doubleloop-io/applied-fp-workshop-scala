package exercises

// TODO: Remove IgnoreSuite annotation

@munit.IgnoreSuite
class RemovalPhase extends munit.FunSuite {

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  // NOTE: Option.fold function documentation
  // https://www.scala-lang.org/api/3.1.3/scala/Option.html#fold-fffff805

  test("valid creation") {
    val item = createItem("100")
    // TODO: Use fold to always produce a string result
    val result: String = ???
    assertEquals(result, "100")
  }

  test("invalid creation") {
    val item = createItem("asd")
    // TODO: Use fold to always produce a string result
    val result: String = ???
    assertEquals(result, "alternative value")
  }

}

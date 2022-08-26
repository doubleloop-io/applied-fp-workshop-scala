package exercises

class RemovalPhase extends munit.FunSuite {

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  // TODO: remove ignores

  // NOTE: Option.fold function documentation
  // https://www.scala-lang.org/api/3.1.3/scala/Option.html#fold-fffff805

  test("valid creation".ignore) {
    val item = createItem("100")
    // TODO: use fold to convert result in string
    val result: String = ???
    assertEquals(result, "100")
  }

  test("invalid creation".ignore) {
    val item = createItem("asd")
    // TODO: use fold to convert result in string
    val result: String = ???
    assertEquals(result, "alternative value")
  }

}

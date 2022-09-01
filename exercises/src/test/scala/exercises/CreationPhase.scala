package exercises

// TODO: Remove IgnoreSuite annotation

@munit.IgnoreSuite
class CreationPhase extends munit.FunSuite {

  case class Item(qty: Int)

  // TODO: Uncomment and complete the type definition for valid or invalid states and
  // enum OptionalItem {
  // }

  // TODO: Use OptionalItem as return type
  def createItem(qty: String): Item =
    if (qty.matches("^[0-9]+$")) Item(qty.toInt)
    else ??? // typically return null or throw exception

  test("creation") {
    // TODO: Use OptionalItem as expected type
    assertEquals(createItem("100"), Item(100))
  }

  test("invalid creation") {
    // TODO: Use OptionalItem as expected type
    assertEquals(createItem("asd"), ???)
    assertEquals(createItem("1 0 0"), ???)
    assertEquals(createItem(""), ???)
  }

}

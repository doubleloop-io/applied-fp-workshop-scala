package exercises

// TODO: remove IgnoreSuite annotation

@munit.IgnoreSuite
class CreationPhase extends munit.FunSuite {

  case class Item(qty: Int)

  // TODO: uncomment and complete the type definition for valid or invalid states and
  // enum OptionalItem {
  // }

  // TODO: use OptionalItem as return type
  def createItem(qty: String): Item =
    if (qty.matches("^[0-9]+$")) Item(qty.toInt)
    else ??? // typically return null or throw exception

  test("creation") {
    // TODO: use OptionalItem as expected type
    assertEquals(createItem("100"), Item(100))
  }

  test("invalid creation") {
    // TODO: use OptionalItem as expected type
    assertEquals(createItem("asd"), ???)
    assertEquals(createItem("1 0 0"), ???)
    assertEquals(createItem(""), ???)
  }

}

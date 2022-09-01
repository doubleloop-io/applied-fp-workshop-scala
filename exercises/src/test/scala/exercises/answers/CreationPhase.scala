package exercises.answers

@munit.IgnoreSuite
class CreationPhase extends munit.FunSuite {

  case class Item(qty: Int)

  enum OptionalItem {
    case Valid(value: Item)
    case Invalid
  }

  def createItem(qty: String): OptionalItem =
    if (qty.matches("^[0-9]+$")) OptionalItem.Valid(Item(qty.toInt))
    else OptionalItem.Invalid

  def createItemOpt(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  test("creation") {
    assertEquals(createItem("100"), OptionalItem.Valid(Item(100)))
  }

  test("invalid creation") {
    assertEquals(createItem("asd"), OptionalItem.Invalid)
    assertEquals(createItem("1 0 0"), OptionalItem.Invalid)
    assertEquals(createItem(""), OptionalItem.Invalid)
  }

}

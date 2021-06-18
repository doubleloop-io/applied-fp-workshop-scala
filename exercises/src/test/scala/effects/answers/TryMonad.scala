package effects.answers

class TryMonad extends munit.FunSuite {

  import scala.util._

  case class ItemId(value: Int)
  case class Item(id: ItemId, qty: Int)

  def load(id: ItemId): Try[Item] =
    Try(Item(id, 100))

  def save(item: Item): Try[Item] =
    Try(item)

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  test("scenario") {
    val program = load(ItemId(1))
      .map(checkIn(10, _))
      .flatMap(save)

    program.fold("err " + _.getMessage, "value " + _)
  }

}

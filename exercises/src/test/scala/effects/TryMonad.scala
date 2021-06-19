package effects

class TryMonad extends munit.FunSuite {

  import scala.util._

  case class ItemId(value: Int)
  case class Item(id: ItemId, qty: Int)

  def load(id: ItemId): Try[Item] =
    Try(Item(id, 100))

  def save(item: Item): Try[Unit] =
    Try(())

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  test("scenario".ignore) {
    // TODO: implement follow steps
    // load an item
    // checkIn 10
    // save item
    val program: Try[Unit] = ???

    // run the computation
    program.fold("err " + _.getMessage, "value " + _)
  }

}

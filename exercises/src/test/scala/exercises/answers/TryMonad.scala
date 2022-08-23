package exercises.answers

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

  test("scenario") {
    val program =
      load(ItemId(1))
        .map(checkIn(10, _))
        .flatMap(save)
  }

  test("scenario for-comprehension") {
    val program =
      for {
        item0 <- load(ItemId(1))
        item1 = checkIn(10, item0)
        _ <- save(item1)
      } yield ()
  }

}

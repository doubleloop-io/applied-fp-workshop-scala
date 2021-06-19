package effects.answers

class IOMonad extends munit.FunSuite {

  import cats.effect._

  case class ItemId(value: Int)
  case class Item(id: ItemId, qty: Int)

  def load(id: ItemId): IO[Item] =
    IO.pure(Item(id, 100))

  def save(item: Item): IO[Unit] =
    IO.unit

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  test("scenario") {
    val program = load(ItemId(1))
      .map(checkIn(10, _))
      .flatMap(save)

    program.unsafeRunSync()
  }

}

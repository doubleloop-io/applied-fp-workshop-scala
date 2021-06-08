package effects.answers

class IOMonadTests extends munit.FunSuite {

  import cats.implicits._
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
    // load an item
    val program = load(ItemId(1))
      // checkIn 10
      .map(checkIn(10, _))
      // save item
      .flatMap(save)

    // run the computation
    program.unsafeRunSync()

    () // keep for the test
  }

}

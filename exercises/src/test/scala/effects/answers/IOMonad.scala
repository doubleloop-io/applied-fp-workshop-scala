package exercises.answers

import minitest._

object IOMonadTests extends SimpleTestSuite {

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
      // materializes any exceptions into value
      .attempt

    // run the computation
    program.unsafeRunSync()

    // keep for the test
    ()
  }

}

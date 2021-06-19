package effects

class IOMonad extends munit.FunSuite {

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

  test("scenario".ignore) {
    // TODO: implement follow steps
    // load an item
    // checkIn 10
    // save item
    val program: IO[Unit] = ???

    // run the computation
    program.unsafeRunSync()
  }

}

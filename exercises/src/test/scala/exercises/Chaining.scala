package exercises

class Chaining extends munit.FunSuite {

  case class ItemId(value: Int)
  case class Item(id: ItemId, qty: Int)

  def checkIn(qty: Int, item: Item): Item = item.copy(qty = item.qty + qty)

  // TODO: remove ignores

  // TODO: for each test implement follow program
  // load an item
  // checkIn 10
  // save item

  test("chaining w/ Try Monad".ignore) {
    import scala.util._

    // fake implementations
    def load(id: ItemId): Try[Item] = Try(Item(id, 100))
    def save(item: Item): Try[Unit] = Try(())

    val program: Try[Unit] = ???
  }

  test("chaining w/ Either Monad".ignore) {
    // fake implementations
    def load(id: ItemId): Either[String, Item] = Right(Item(id, 100))
    def save(item: Item): Either[String, Unit] = Right(())

    val program: Either[String, Unit] = ???
  }

  test("chaining with IO Monad".ignore) {
    import cats.effect.IO

    // fake implementations
    def load(id: ItemId): IO[Item] = IO(Item(id, 100))
    def save(item: Item): IO[Unit] = IO(())

    val program: IO[Unit] = ???
  }

}

package exercises.answers

@munit.IgnoreSuite
class Chaining extends munit.FunSuite {

  case class ItemId(value: Int)
  case class Item(id: ItemId, qty: Int)

  val id = ItemId(1)

  def checkIn(qty: Int, item: Item): Item = item.copy(qty = item.qty + qty)

  test("chaining w/ Try Monad") {
    import scala.util._

    // fake implementations
    def load(id: ItemId): Try[Item] = Try(Item(id, 100))
    def save(item: Item): Try[Item] = Try(item)

    val program: Try[Item] =
      load(id)
        .map(checkIn(10, _))
        .flatMap(save)
  }

  test("chaining w/ Either Monad") {
    // fake implementations
    def load(id: ItemId): Either[String, Item] = Right(Item(id, 100))
    def save(item: Item): Either[String, Item] = Right(item)

    val program: Either[String, Item] =
      load(id)
        .map(checkIn(10, _))
        .flatMap(save)
  }

  test("chaining with IO Monad") {
    import cats.effect.IO

    // fake implementations
    def load(id: ItemId): IO[Item] = IO(Item(id, 100))
    def save(item: Item): IO[Item] = IO(item)

    val program: IO[Item] =
      load(id)
        .map(checkIn(10, _))
        .flatMap(save)
  }

}

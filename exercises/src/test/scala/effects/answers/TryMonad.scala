package exercises.answers

class TryMonadTests extends munit.FunSuite {

  import scala.util._
  import cats.implicits._
  import cats.effect._

  case class ItemId(value: Int)
  case class Item(id: ItemId, qty: Int)

  def load(id: ItemId): Try[Item] =
    Try(Item(id, 100))

  def save(item: Item): Try[Unit] =
    Try(())

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
    program.fold("err " + _.getMessage, "value " + _)

    () // keep for the test
  }

}

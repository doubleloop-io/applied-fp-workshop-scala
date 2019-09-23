package exercises

import minitest._

object EitherMonadTests extends SimpleTestSuite {

  import scala.util._
  import cats.implicits._
  import cats.effect._

  case class ItemId(value: Int)
  case class Item(id: ItemId, qty: Int)

  type Error = String

  def load(id: ItemId): Either[String, Item] =
    Right(Item(id, 100))

  def save(item: Item): Either[String, Unit] =
    Right(())

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  test("scenario") {
    ignore("implement follow steps")
    // load an item
    // checkIn 10
    // save item
    val program: Either[Error, Unit] = ???

    // run the computation
    program.fold("err " + _, "value " + _)

    () // keep for the test
  }

}
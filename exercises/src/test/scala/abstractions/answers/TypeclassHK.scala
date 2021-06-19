package abstractions.answers

class TypeclassHK extends munit.FunSuite {

  trait Stack[F[_]] {
    def push[A](fa: F[A])(value: A): F[A]
    def pop[A](fa: F[A]): (Option[A], F[A])
  }

  implicit val listStack: Stack[List] = new Stack[List] {
    def push[A](fa: List[A])(value: A): List[A]   = value :: fa
    def pop[A](fa: List[A]): (Option[A], List[A]) = (fa.headOption, fa.tail)
  }

  object MRUList {
    def add[F[_]: Stack, A](value: A, items: F[A])(implicit S: Stack[F]): F[A] =
      S.push(items)(value)
  }

  test("add an element to the MRU list") {
    import MRUList._

    val empty   = List.empty[String]
    val updated = add("first", empty)
    val result  = add("second", updated)

    assertEquals(result, List("second", "first"))
  }

  import scala.util._
  import cats.Monad
  import cats.implicits._

  case class ItemId(value: Int)
  case class Item(id: ItemId, qty: Int)

  def load[F[_]](id: ItemId)(implicit M: Monad[F]): F[Item] =
    M.pure(Item(id, 100))

  def save[F[_]](item: Item)(implicit M: Monad[F]): F[Item] =
    M.pure(item)

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  test("scenario w/ Option") {
    val program = load[Option](ItemId(1))
      .map(checkIn(10, _))
      .flatMap(x => save[Option](x))

    assertEquals(Option(Item(ItemId(1), 110)), program)
  }

  test("scenario w/ Try") {
    val program = load[Try](ItemId(1))
      .map(checkIn(10, _))
      .flatMap(x => save[Try](x))

    assertEquals(Try(Item(ItemId(1), 110)), program)
  }

}

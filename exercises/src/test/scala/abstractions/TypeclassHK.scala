package abstractions

/*
 * In a type system we deal with different kind of types.
 *
 * Type like Int, String, Date, Person, etc. are called
 * 'proper type'. In polymorphic function it is represented
 * by symbols like A, B, C, etc.
 * def sum[A](x: A, y: A): A = ???
 * These types have kind: *
 *
 * Instead types like List[A], Option[A], Box[A], etc.
 * are called 'type constructor', because they accepts
 * a type and return a new type like:
 * List[Int], Option[String], Box[Person], etc.
 * These types have kind: * -> *
 * aka accepts a type and returns a type.
 * In the polymorphic function definition nothing changes.
 * def concat[A](x: List[A], y: List[A]): List[A] = ???
 *
 * In Scala and few others languages we can express
 * a function polymorphic over the type constructor.
 * def concat[F[_], A](x: F[A], y: F[A]): F[A] = ???
 * In these cases we talk about Higher-Order Kinds.
 * These types have kind: (* -> *) -> *
 * aka accepts a type constructor and returns
 * a type constructor.
 *
 * It's similar to the idea of High-Order Functions
 * aka function that accepts/returns other functions.
 *
 * https://en.wikipedia.org/wiki/Kind_(type_theory)
 */

class TypeclassHK extends munit.FunSuite {

  // TODO: Define push and pop operations on Stack.
  trait Stack[F[_]] {}

  // TODO: Implements Stack class instance for List.
  implicit val listStack: Stack[List] = new Stack[List] {}

  object MRUList {
    // TODO: Complete the MRUList.add function implementation.
    def add[F[_]: Stack, A](value: A, items: F[A])(implicit S: Stack[F]): F[A] =
      ???
  }

  // TODO: remove ignore
  test("add an element to the MRU list".ignore) {
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
    // TODO: implement with Monad's pure function
    ???

  def save[F[_]](item: Item)(implicit M: Monad[F]): F[Item] =
    // TODO: implement with Monad's pure function
    ???

  def checkIn(qty: Int, item: Item): Item =
    item.copy(qty = item.qty + qty)

  test("scenario w/ Option".ignore) {
    val program = load[Option](ItemId(1))
      .map(checkIn(10, _))
      .flatMap(x => save[Option](x))

    assertEquals(Option(Item(ItemId(1), 110)), program)
  }

  test("scenario w/ Try".ignore) {
    val program = load[Try](ItemId(1))
      .map(checkIn(10, _))
      .flatMap(x => save[Try](x))

    assertEquals(Try(Item(ItemId(1), 110)), program)
  }
}

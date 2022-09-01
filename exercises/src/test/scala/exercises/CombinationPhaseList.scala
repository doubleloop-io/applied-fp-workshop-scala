package exercises

// TODO: Remove IgnoreSuite annotation

@munit.IgnoreSuite
class CombinationPhaseList extends munit.FunSuite {

  import cats.syntax.traverse._

  case class Item(qty: Int)

  def createItem(qty: String): Option[Item] =
    if (qty.matches("^[0-9]+$")) Some(Item(qty.toInt))
    else None

  // NOTE: List.map function documentation
  // https://www.scala-lang.org/api/3.1.3/scala/collection/immutable/List.html#map-fffff812

  test("all valid - individual validation results") {
    val values = List("1", "10", "100")
    // TODO: Use map over values list to create items
    val items: List[Option[Item]] = ???
    assertEquals(items, List(Some(Item(1)), Some(Item(10)), Some(Item(100))))
  }

  test("some invalid - individual validation results") {
    val values = List("1", "asf", "100")
    // TODO: Use map over values list to create items
    val items: List[Option[Item]] = ???
    assertEquals(items, List(Some(Item(1)), None, Some(Item(100))))
  }

  // NOTE: List.traverse function documentation
  // https://typelevel.org/cats/api/cats/Traverse.html#traverse[G[_],A,B](fa:F[A])(f:A=%3EG[B])(implicitevidence$1:cats.Applicative[G]):G[F[B]]

  test("all valid - one omni validation result") {
    val values = List("1", "10", "100")
    // TODO: Use traverse over values list to create items
    val items: Option[List[Item]] = ???
    assertEquals(items, Some(List(Item(1), Item(10), Item(100))))
  }

  test("some invalid - one omni validation result") {
    val values = List("1", "asd", "100")
    // TODO: Use traverse over values list to create items
    val items: Option[List[Item]] = ???
    assertEquals(items, None)
  }

}

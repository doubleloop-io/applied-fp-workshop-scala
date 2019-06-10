package exercises.answers

import minitest._

object TypeclassHKTests extends SimpleTestSuite {

  trait Stack[F[_]] {
    def push[A](fa: F[A])(value: A): F[A]
    def pop[A](fa: F[A]): (Option[A], F[A])
  }

  implicit val listStack = new Stack[List] {
    def push[A](fa: List[A])(value: A): List[A]   = value :: fa
    def pop[A](fa: List[A]): (Option[A], List[A]) = (fa.headOption, fa.tail)
  }

  object MRUList {
    def add[F[_]: Stack, A](value: A, items: F[A])(implicit S: Stack[F]): F[A] =
      S.push(items)(value)
  }

  test("add an element to the MRU list") {
    import MRUList._

    val items  = List("first")
    val result = add("second", items)
    assertEquals(result, List("second", "first"))
  }
}

package exercises.answers

import minitest._

object TypeclassHKTests extends SimpleTestSuite {

  trait Stack[F[_]] {
    def push[A](fa: F[A])(value: A): F[A]
    def pop[A](fa: F[A]): (A, F[A])
  }

  implicit val listStack = new Stack[List] {
    def push[A](fa: List[A])(value: A): List[A] = value :: fa
    def pop[A](fa: List[A]): (A, List[A])       = (fa.head, fa.tail)
  }

  object Stack {
    def apply[F[_]](implicit x: Stack[F]) = x
  }

  object MRUList {
    def add[F[_]: Stack, A](value: A, items: F[A]): F[A] =
      Stack[F].push(items)(value)
  }

  test("add an element to the MRU list") {
    import MRUList._

    val items  = List("first")
    val result = add("second", items)
    assertEquals(result, List("second", "first"))
  }
}

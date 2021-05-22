package exercises

/*
 * Let's see a real example of programming with type classes.
 *
 * The idea is to implement a MapReduce function.
 * A MapReduce function is simply a combination of two parts:
 * - a map function: which performs data projection
 * - a reduce function: which performs a summary operation
 */

class MapReduceTests extends munit.FunSuite {

  /*
   * TODO: Implements mapReduce function.
   *       Use typeclasses and instances from cats library.
   *
   * NOTE: the following type classes _can be_ useful:
   *
   *       - Semigroup: https://typelevel.org/cats/api/cats/kernel/Semigroup.html
   *       - Monoid: https://typelevel.org/cats/api/cats/kernel/Monoid.html
   *       - Functor: https://typelevel.org/cats/api/cats/Functor.html
   *       - Foldable: https://typelevel.org/cats/api/cats/Foldable.html
   *       - Traverse: https://typelevel.org/cats/api/cats/Traverse.html
   *
   * ADD YOUR CODE HERE INSIDE THE TEST OBJECT
   */

  def mapReduce[F[_], A, B](fa: F[A])(f: A => B): B =
    ???

  test("order total price".ignore) {
    case class Order(lines: List[OrderLine]) {
      def total: Int = mapReduce(lines)(l => l.price)
    }
    case class OrderLine(item: String, price: Int)

    val order = Order(List(OrderLine("foo", 50), OrderLine("bar", 20), OrderLine("bar", 15)))

    // TODO: ingore(implements mapReduce")
    val result = order.total
    assertEquals(result, 50 + 20 + 15)
  }

  test("major students".ignore) {
    case class Classroom(students: List[Student]) {
      def adultCount: Int = ???
    }
    case class Student(name: String, age: Int)

    // TODO: ingore(implements mapReduce")
    val classroom = Classroom(List(Student("foo", 16), Student("bar", 20), Student("bar", 19)))
    val result    = classroom.adultCount
    assertEquals(result, 2)
  }
}

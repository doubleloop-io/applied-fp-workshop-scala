package abstractions.answers

class MapReduce extends munit.FunSuite {

  import cats.{ Monoid, Traverse }

  def mapReduce[F[_]: Traverse, A, B: Monoid](fa: F[A])(f: A => B): B =
    Traverse[F].foldMap(fa)(f)

  test("order total price") {
    import cats.instances.list._
    import cats.instances.int._

    case class Order(lines: List[OrderLine]) {
      def total: Int = mapReduce(lines)(l => l.price)
    }
    case class OrderLine(item: String, price: Int)

    val order  = Order(List(OrderLine("foo", 50), OrderLine("bar", 20), OrderLine("bar", 15)))
    val result = order.total
    assertEquals(result, 50 + 20 + 15)
  }

  test("major students") {
    import cats.instances.list._
    import cats.instances.int._
    import cats.instances.option._

    case class Classroom(students: List[Student]) {
      def majorsCount: Int =
        mapReduce(students)(s => if (s.age >= 18) Option(1) else None)
          .getOrElse(0)
    }
    case class Student(name: String, age: Int)

    val classroom = Classroom(List(Student("foo", 16), Student("bar", 20), Student("bar", 19)))
    val result    = classroom.majorsCount
    assertEquals(result, 2)
  }
}

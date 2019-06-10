package exercises.answers

import minitest._

import scala.util.{Failure, Success, Try}

import cats._
import cats.effect._
import cats.implicits._

object MonadicFindTests extends SimpleTestSuite {

  case class Person(name: String, age: Int)

  val mario  = Person("Mario Rossi", 35)
  val john   = Person("John Doe", 47)
  val foo    = Person("Foo", 15)
  val people = List(mario, john, foo)

  object PurePredicate {
    def ageOver(age: Int): Person => Boolean =
      p => p.age > age
  }

  // def find(fa: F[A])(f: A ⇒ Boolean): Option[A]

  test("normal - result found") {
    import PurePredicate._
    val result = people.find(ageOver(40))
    assertEquals(result, Some(john))
  }

  test("normal - result not found") {
    import PurePredicate._
    val result = people.find(ageOver(50))
    assertEquals(result, None)
  }

  object EffectfulPredicate {

    def ageOver(age: Int): Person => Try[Boolean] =
      p => Try { p.age > age }

    def ageOverKaboom(age: Int): Person => Try[Boolean] =
      p => Try { throw UnreachableServer("server unreachable") }

    case class UnreachableServer(error: String) extends RuntimeException
  }

  // def findM(fa: F[A])(p: A ⇒ G[Boolean]): G[Option[A]]

  test("monadic - result found") {
    import EffectfulPredicate._
    val result = people.findM(ageOver(40))
    assertEquals(result, Success(Some(john)))
  }

  test("monadic - result not found") {
    import EffectfulPredicate._
    val result = people.findM(ageOver(50))
    assertEquals(result, Success(None))
  }

  test("monadic - fail") {
    import EffectfulPredicate._
    val result = people.findM(ageOverKaboom(40))
    assertEquals(result, Failure(UnreachableServer("server unreachable")))
  }

  object MoreMonads {
    import cats.data._
    import cats.implicits._

    def ageOverLogged(age: Int): Person => Writer[List[String], Boolean] =
      p => Writer(List(s"check: $p"), p.age > age)

    def ageOverCount(age: Int): Person => State[Int, Boolean] =
      p => State(s => (s + 1, p.age > age))
  }

  test("monadic - log find steps") {
    import MoreMonads._
    import cats.data._

    val monadicResult = people.findM(ageOverLogged(40))
    val (log, result) = monadicResult.run

    assertEquals(result, Some(john))
    assertEquals(log, List("check: Person(Mario Rossi,35)", "check: Person(John Doe,47)"))
  }

  test("monadic - count find steps") {
    import MoreMonads._
    import cats.data._

    val monadicResult   = people.findM(ageOverCount(40))
    val (count, result) = monadicResult.run(0).value

    assertEquals(result, Some(john))
    assertEquals(count, 2)
  }

}

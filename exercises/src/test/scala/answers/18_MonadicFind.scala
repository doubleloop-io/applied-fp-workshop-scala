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

  def ageOver(age: Int): Person => Boolean =
    p => p.age > age

  test("normal - result found") {
    val result = people.find(ageOver(40))
    assertEquals(result, Some(john))
  }

  test("normal - result not found") {
    val result = people.find(ageOver(50))
    assertEquals(result, None)
  }

  object EffectfulPredicate {

    def ageOver(age: Int): Person => Try[Boolean] =
      p => Try { p.age > age }

    test("monadic - result found") {
      val result = people.findM(ageOver(40))
      assertEquals(result, Success(Some(john)))
    }

    test("monadic - result not found") {
      val result = people.findM(ageOver(50))
      assertEquals(result, Success(None))
    }

    case class UnreachableServer(error: String) extends RuntimeException
    def ageOverKaboom(age: Int): Person => Try[Boolean] =
      p => Try { throw UnreachableServer("server unreachable") }

    test("monadic - fail") {
      val result = people.findM(ageOverKaboom(40))
      assertEquals(result, Failure(UnreachableServer("server unreachable")))
    }
  }

}

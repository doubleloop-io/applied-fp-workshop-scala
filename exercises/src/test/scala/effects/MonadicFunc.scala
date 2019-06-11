package exercises

import minitest._

object MonadicFuncTests extends SimpleTestSuite {

  import cats.data._
  import cats.implicits._
  import cats.effect._

  // def find (fa: F[A])(f: A ⇒   Boolean ):   Option[A]
  // def findM(fa: F[A])(p: A ⇒ G[Boolean]): G[Option[A]]

  case class Person(name: String, age: Int)

  val mario  = Person("Mario Rossi", 35)
  val john   = Person("John Doe", 47)
  val foo    = Person("Foo", 15)
  val people = List(mario, john, foo)

  object SimplePredicate {
    def ageOver(age: Int): Person => Boolean =
      p => p.age > age
  }

  test("normal - result found") {
    import SimplePredicate._

    val result = people.find(ageOver(40))

    assertEquals(result, Some(john))
  }

  test("normal - result not found") {
    import SimplePredicate._

    val result = people.find(ageOver(50))

    assertEquals(result, None)
  }

  object CountHowManyIteration {
    var iterationCount = 0

    def ageOver(age: Int): Person => Boolean =
      p => {
        iterationCount = iterationCount + 1
        p.age > age
      }
  }

  test("count find iterations") {
    import CountHowManyIteration._

    val result = people.find(ageOver(40))
    var count  = iterationCount

    assertEquals(result, Some(john))
    assertEquals(count, 2)
  }

  object WhatWereInspected {
    var inspected = List.empty[String]

    def ageOver(age: Int): Person => Boolean =
      p => {
        inspected = inspected :+ s"check: $p"
        p.age > age
      }
  }

  test("log find iterations") {
    import WhatWereInspected._

    val result = people.find(ageOver(40))
    val log    = inspected

    assertEquals(result, Some(john))
    assertEquals(log, List("check: Person(Mario Rossi,35)", "check: Person(John Doe,47)"))
  }

  object OutOfProc {
    // can be anythig:
    //    - a query to the database
    //    - a cloud-native FaaS microservice deployed somewhere
    // particularly it can fial for many reasons

    def ageOver(age: Int): Person => Boolean =
      p => p.age > age

    def ageOverKaboom(age: Int): Person => Boolean =
      p => throw UnreachableServer("server unreachable")

    case class UnreachableServer(error: String) extends RuntimeException
  }

  test("io - found") {
    import OutOfProc._

    val result = people.find(ageOver(40))

    assertEquals(result, Some(john))
  }

  test("io - not found") {
    import OutOfProc._

    val result = people.find(ageOver(50))

    assertEquals(result, None)
  }

  test("io - fail") {
    import OutOfProc._

    intercept[UnreachableServer] {

      val result = people.find(ageOverKaboom(40))
    }
  }
}
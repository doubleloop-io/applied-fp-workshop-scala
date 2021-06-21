package effects.answers

class MonadicFunc extends munit.FunSuite {

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
    def ageOver(age: Int): Person => State[Int, Boolean] =
      p => State(s => (s + 1, p.age > age))
  }

  test("count find iterations") {
    import CountHowManyIteration._

    val find            = people.findM(ageOver(40))
    val (count, result) = find.run(0).value

    assertEquals(result, Some(john))
    assertEquals(count, 2)
  }

  object WhatWereInspected {
    def ageOver(age: Int): Person => Writer[List[String], Boolean] =
      p => Writer(List(s"check: $p"), p.age > age)
  }

  test("log find iterations") {
    import WhatWereInspected._

    val find          = people.findM(ageOver(40))
    val (log, result) = find.run

    assertEquals(result, Some(john))
    assertEquals(log, List("check: Person(Mario Rossi,35)", "check: Person(John Doe,47)"))
  }

  object OutOfProc {
    // can be anythig:
    //    - a query to the database
    //    - a cloud-native FaaS microservice deployed somewhere
    // particularly it can fial for many reasons

    def ageOver(age: Int): Person => IO[Boolean] =
      p => IO(p.age > age)

    def ageOverKaboom(age: Int): Person => IO[Boolean] =
      p => IO(throw UnreachableServer("server unreachable"))

    case class UnreachableServer(error: String) extends RuntimeException
  }

  test("io - found") {
    import OutOfProc._

    val find   = people.findM(ageOver(40))
    val result = find.attempt.unsafeRunSync()

    assertEquals(result, Right(Some(john)))
  }

  test("io - not found") {
    import OutOfProc._

    val find   = people.findM(ageOver(50))
    val result = find.attempt.unsafeRunSync()

    assertEquals(result, Right(None))
  }

  test("io - fail") {
    import OutOfProc._

    val find   = people.findM(ageOverKaboom(40))
    val result = find.attempt.unsafeRunSync()

    assertEquals(result, Left(UnreachableServer("server unreachable")))
  }

}

package demos

@munit.IgnoreSuite
class MonadicFuncDemo extends munit.FunSuite {

  // Standard library function could be extended with a monadic effect.
  // For example:

  // def List[Int].find (f: A ⇒     Boolean ):     Option[Int]
  // def List[Int].findM(p: A ⇒ Try[Boolean]): Try[Option[Int]]

  // def List[Int].foldLeft (z:    String )(op: (String, Int) =>    String ): String
  // def List[Int].foldLeftM(z: IO[String])(op: (String, Int) => IO[String]): IO[String]

  case class Person(name: String, age: Int)

  val mario = Person("Mario Rossi", 35)
  val john = Person("John Doe", 47)
  val foo = Person("Foo", 15)
  val people = List(mario, john, foo)

  test("normal") {

    def ageOver(age: Int): Person => Boolean =
      p => p.age > age

    val found = people.find(ageOver(40))
    assertEquals(found, Some(john))

    val notFound = people.find(ageOver(50))
    assertEquals(notFound, None)
  }

  test("count iterations") {
    import cats.data.State
    import cats.syntax.foldable._

    def ageOver(age: Int): Person => State[Int, Boolean] =
      p => State(s => (s + 1, p.age > age))

    val find = people.findM(ageOver(40))
    val (count, result) = find.run(0).value

    assertEquals(result, Some(john))
    assertEquals(count, 2)
  }

  test("log iterations") {
    import cats.data.Writer
    import cats.syntax.foldable._

    def ageOver(age: Int): Person => Writer[List[String], Boolean] =
      p => Writer(List(s"check: $p"), p.age > age)

    val find = people.findM(ageOver(40))
    val (log, result) = find.run

    assertEquals(result, Some(john))
    assertEquals(log, List("check: Person(Mario Rossi,35)", "check: Person(John Doe,47)"))
  }

  test("iterate over I/O") {
    import cats.effect.IO
    import cats.syntax.foldable._
    import cats.effect.unsafe.implicits._

    def ageOver(age: Int): Person => IO[Boolean] =
      p => IO(p.age > age)

    val found = people
      .findM(ageOver(40))
      .attempt
      .unsafeRunSync()

    assertEquals(found, Right(Some(john)))

    val notFound = people
      .findM(ageOver(50))
      .attempt
      .unsafeRunSync()

    assertEquals(notFound, Right(None))
  }

  test("iterate over I/O - fail") {
    import cats.effect.IO
    import cats.syntax.foldable._
    import cats.effect.unsafe.implicits._

    def ageOver(age: Int): Person => IO[Boolean] =
      p => IO(throw UnreachableServer())

    case class UnreachableServer() extends RuntimeException

    val result = people
      .findM(ageOver(40))
      .attempt
      .unsafeRunSync()

    assertEquals(result, Left(UnreachableServer()))
  }
}

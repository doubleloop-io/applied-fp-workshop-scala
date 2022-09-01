package demos

@munit.IgnoreSuite
class IODemo extends munit.FunSuite {

  import cats.effect.IO
  import cats.effect.unsafe.implicits._
  import cats.syntax.applicativeError._

  test("creation") {
    // the IO constructor (apply) is lazy-evaluated so,
    // in this case nothing is printed on the console
    val io1 = IO(System.out.println("hi!"))

    // The laziness stop the side-effect and produces a value
    // that describing our program.
    // This throws has no effect.
    val io2 = IO(throw new RuntimeException("boom!"))

    // In order evaluate our program we explicitly call unsafeRunSync
    IO(5).unsafeRunSync()

    // Pay attention to the pure function, it's eager-evaluated

    // Eval 5 and then create IO
    val io3 = IO.pure(5)

    // Eval println and then create IO
    // val io4 = IO.pure(System.out.println("hi!"))

    // Eval throw and then create IO
    // val io5 = I.pureO(throw new RuntimeException("boom!"))
  }

  test("error handling") {
    case class UnreachableServer() extends RuntimeException

    // Like for Either there are recover and recoverWith functions
    val io1: IO[String] = IO(throw UnreachableServer())

    val io2: IO[String] =
      io1.recover(e =>
        e match {
          case UnreachableServer() => "localhost"
        }
      )

    val io3: IO[String] =
      io1.recoverWith(e =>
        e match {
          case UnreachableServer() => IO("localhost")
        }
      )

    // Plus, IO provides attempt function that trap any error and
    // materialize them as Either[Throwable, A]
    val io4: IO[Either[Throwable, String]] = io1.attempt

    // Remember, nothing happens until unsafeRunSync function is called
    io4.unsafeRunSync()
  }

}

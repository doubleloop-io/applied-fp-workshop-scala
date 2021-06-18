//package marsroverkata.answers
//
//import scala.util._
//import cats.implicits._
//import cats.effect._
//import cats.effect.concurrent._
//
//import marsroverkata.answers.Version5._
//
//class Version5Tests extends munit.FunSuite {
//
//  test("simulate app throws RuntimeException") {
//    implicit val silent: Logger[IO] = new Logger[IO] {
//      def logInfo(message: String): IO[Unit]  = IO.unit
//      def logError(message: String): IO[Unit] = IO.unit
//    }
//
//    val planet                      = IO.pure(("5x4", "2,0 0,3 3,2"))
//    val rover: IO[(String, String)] = IO(throw new RuntimeException("boom!"))
//    val commands                    = IO.pure("RFF")
//    val app                         = (planet, rover, commands).mapN(run)
//
//    val result = handleApp(app).unsafeRunSync()
//
//    assertEquals(result, "Ooops :-(")
//  }
//
//  test("simulate app throws RuntimeException") {
//
//    def scenario(ref: Ref[IO, Int]) = {
//      implicit val silent: Logger[IO] = new Logger[IO] {
//
//        private val increment = ref.modify(x => (x + 1, x))
//
//        def logInfo(message: String): IO[Unit] =
//          IO.unit
//
//        def logError(message: String): IO[Unit] =
//          increment *> IO.unit
//      }
//
//      val planet                      = IO.pure(("5x4", "2,0 0,3 3,2"))
//      val rover: IO[(String, String)] = IO(throw new RuntimeException("boom!"))
//      val commands                    = IO.pure("RFF")
//      val app                         = (planet, rover, commands).mapN(run)
//      val result                      = handleApp(app)
//      (result, ref.get).tupled
//    }
//
//    val (result, errorCount) = Ref
//      .of[IO, Int](0)
//      .flatMap(scenario)
//      .unsafeRunSync()
//
//    assertEquals(result, "Ooops :-(")
//    assertEquals(errorCount, 1)
//  }
//}

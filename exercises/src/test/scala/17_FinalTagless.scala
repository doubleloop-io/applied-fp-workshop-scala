package exercises

import minitest._

/*
 * Program with F-Algebras.
 *
 * Express the operation (algebra) of your domain
 * in terms of an abstract effect F[_], then
 * materialize the effect in the main.
 * Sometimes one effect isn't enough so instead of one
 * Monad we use a Monad stack built with the concept of
 * Monad Transformer.
 */

object FinalTaglessTests extends SimpleTestSuite {
  /*
   * TODO: add num, plus and times operations to
   *       Exp (F-algebra). Implements IOEvaluator
   *       and then uncomment and complete the test
   */

  import cats.implicits._
  import cats.effect._

  trait Exp[F[_]] {}

  object IOEvaluator extends Exp[IO] {}

//   test("execute program w/ IO monad") {
//     def programF[F[_]](exp: Exp[F]): F[Int] = {
//       import exp._
//       times(plus(num(1), num(1)), num(2))
//     }

//     def programIO: IO[Int] = ???

//     val result = programIO.unsafeRunSync()
//     assertEquals(result, 4)
//   }
}

import cats._

package object inventory {

  type Throwing[F[_]] = MonadError[F, Throwable]

  object Throwing {
    def apply[F[_]](implicit T: Throwing[F]): Throwing[F] = T
  }
}

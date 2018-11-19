package inventory.answers

import java.util.UUID

trait RandomId[F[_]] {
  def nextId(): F[UUID]
}

object RandomId {

  def apply[F[_]]()(implicit R: RandomId[F]): RandomId[F] = R

  def nextId[F[_]]()(implicit R: RandomId[F]): F[UUID] =
    R.nextId()
}

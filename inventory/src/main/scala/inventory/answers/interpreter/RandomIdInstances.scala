package inventory.answers.interpreter

import java.util.UUID

import cats.effect._

import inventory.answers.RandomId

trait RandomInstances {

  implicit def randomId[F[_]: Sync]: RandomId[F] = new RandomId[F] {

    private val S = Sync[F]
    import S._

    def nextId(): F[UUID] = delay(UUID.randomUUID())
  }

}

object randomid extends RandomInstances

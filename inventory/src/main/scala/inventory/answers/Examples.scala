package inventory.answers

import cats._
import cats.implicits._

import ItemService._
import RandomId._
import Models._

object Examples {

  def demoOk[F[_]: Monad: ItemService]: F[Unit] =
    for {
      item0 <- create("books", 5)
      uuid  = item0.id.value
      _     <- checkin(uuid, 10)
      _     <- rename(uuid, "pens")
      _     <- checkout(uuid, 3)
      _     <- deactivate(uuid)
    } yield ()

  def demoBad[F[_]: Monad: ItemService]: F[Unit] =
    for {
      item0 <- create("@books!", -5)
      uuid  = item0.id.value
      _     <- checkin(uuid, 10)
    } yield ()

  def demoNotFound[F[_]: Monad: RandomId: ItemService]: F[Unit] =
    for {
      uuid <- nextId()
      _    <- checkin(uuid, 10)
    } yield ()
}

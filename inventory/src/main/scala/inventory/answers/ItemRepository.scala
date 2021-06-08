package inventory.answers

import Models._

trait ItemRepository[F[_]] {
  def load(id: ItemId): F[Item]
  def save(iitem: Item): F[Item]
}

object ItemRepository {

  final case class ItemNotFoundException(id: ItemId) extends Exception(s"Can't load item: $id")

  def apply[F[_]](implicit R: ItemRepository[F]): ItemRepository[F] = R

  def load[F[_]](id: ItemId)(implicit R: ItemRepository[F]): F[Item] =
    R.load(id)

  def save[F[_]](item: Item)(implicit R: ItemRepository[F]): F[Item] =
    R.save(item)
}

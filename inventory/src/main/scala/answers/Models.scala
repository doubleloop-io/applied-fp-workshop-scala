package inventory.answers

import java.util.UUID

import cats.implicits._

import Validation._

object Models {

  case class ItemId(value: UUID)
  case class Item(id: ItemId, name: String, count: Int, activated: Boolean)

  object Item {

    def create(id: UUID, name: String, count: Int): ValidationResult[Item] =
      (
        valid(ItemId(id)),
        validateName(name),
        validateCount(count),
        valid(true)
      ).mapN(Item.apply)

    def checkin(count: Int): Item => ValidationResult[Item] =
      i => validateCount(count).map(v => i.copy(count = i.count + v))

    def rename(newName: String): Item => ValidationResult[Item] =
      i => validateName(newName).map(v => i.copy(name = v))

    def deactivate: Item => ValidationResult[Item] =
      i => validateActivated(i).map(_ => i.copy(activated = false))

    def validateName(name: String): ValidationResult[String] =
      checkNotEmpty(name, "name")
        .andThen(checkAlphanumeric(_, "name"))

    def validateCount(count: Int): ValidationResult[Int] =
      checkPositive(count, "count")

    def validateActivated(item: Item): ValidationResult[Boolean] =
      checkTrue(item.activated, "activated")
  }
}

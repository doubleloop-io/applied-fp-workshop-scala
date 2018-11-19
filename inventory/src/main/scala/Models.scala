package inventory

import java.util.UUID

object Models {

  case class ItemId(value: UUID)
  case class Item(id: ItemId, name: String, count: Int, activated: Boolean)

  object Item {}
}

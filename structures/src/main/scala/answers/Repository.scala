package structures.answers

import structures.answers.Models._

object Repository {

  def loadCart(id: CartId): Option[Cart] =
    if (id.value.contains("gold")) Some(Cart(id, CustomerId("gold-customer"), Amount(100)))
    else if (id.value.contains("normal")) Some(Cart(id, CustomerId("normal-customer"), Amount(100)))
    else None

  def lookupDiscountRule(id: CustomerId): Option[DiscountRule] =
    if (id.value.contains("gold")) Some(DiscountRule(half))
    else None

  private def half(cart: Cart): Amount =
    Amount(cart.amount.value / 2)

  def updateAmount(cart: Cart, discount: Amount): Cart =
    cart.copy(id = cart.id, customerId = cart.customerId, amount = Amount(cart.amount.value - discount.value))

  def save(cart: Cart, storage: Storage[Cart]): Unit =
    storage.flush(cart)
}

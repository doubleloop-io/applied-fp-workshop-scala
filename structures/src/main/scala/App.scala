package structures

import structures.Models._
import structures.Repository._

object App {

  def applyDiscount(cartId: CartId, storage: Storage[Cart]): Unit = {
    val cart = loadCart(cartId)
    if (cart != Cart.missingCart) {
      val rule = lookupDiscountRule(cart.customerId)
      if (rule != DiscountRule.noDiscount) {
        val discount    = rule(cart)
        val updatedCart = updateAmount(cart, discount)
        save(updatedCart, storage)
      }
    }
  }
}

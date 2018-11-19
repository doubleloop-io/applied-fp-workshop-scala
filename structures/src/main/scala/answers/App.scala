package structures.answers

import structures.answers.Models._
import structures.answers.Repository._

object App {

  def applyDiscount(cartId: CartId, storage: Storage[Cart]): Unit =
    (for {
      cart        <- loadCart(cartId)
      rule        <- lookupDiscountRule(cart.customerId)
      discount    = rule(cart)
      updatedCart = updateAmount(cart, discount)
      _           = save(updatedCart, storage)
    } yield ()).getOrElse(())
}

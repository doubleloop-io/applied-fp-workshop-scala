package exercises.answers

import minitest._

/*
 * Functions can't always return a value.
 * In this scenario they are called: partial functions.
 * We can convert them into total functions
 * with the introduction of effects.
 *
 *  f:  InType => Effect[OutType]
 */

object MaybeTests extends SimpleTestSuite {

  sealed trait MaybeQty
  case class ValidQty(value: Qty) extends MaybeQty
  case class InvalidQty()         extends MaybeQty

  case class Qty(value: Int)

  def toQty(value: String): MaybeQty =
    if (value.matches("^[0-9]+$")) ValidQty(Qty(value.toInt))
    else InvalidQty()

  test("valid qty") {
    assertEquals(toQty("100"), ValidQty(Qty(100)))
  }

  test("invalid qty") {
    assertEquals(toQty("asd"), InvalidQty())
    assertEquals(toQty("1 0 0"), InvalidQty())
    assertEquals(toQty(""), InvalidQty())
  }

  sealed trait Maybe[A]
  case class Yeah[A](value: A) extends Maybe[A]
  case class Nope[A]()         extends Maybe[A]

  def toQty_II(value: String): Maybe[Qty] =
    if (value.matches("^[0-9]+$")) Yeah(Qty(value.toInt))
    else Nope()

  test("valid qty") {
    assertEquals(toQty_II("100"), Yeah(Qty(100)))
  }

  test("invalid qty") {
    assertEquals(toQty_II("asd"), Nope())
    assertEquals(toQty_II("1 0 0"), Nope())
    assertEquals(toQty_II(""), Nope())
    assertEquals(toQty_II("-10"), Nope())
  }

  def toQty_III(value: String): Option[Qty] =
    if (value.matches("^[0-9]+$")) Some(Qty(value.toInt))
    else None

  test("valid qty") {
    assertEquals(toQty_III("100"), Some(Qty(100)))
  }

  test("invalid qty") {
    assertEquals(toQty_III("asd"), None)
    assertEquals(toQty_III("1 0 0"), None)
    assertEquals(toQty_III(""), None)
    assertEquals(toQty_III("-10"), None)
  }
}

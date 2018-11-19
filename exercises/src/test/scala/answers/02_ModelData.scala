package exercises.answers

import minitest._

/*
 * In OOP model object that incapsulate data and expose behaviours.
 * This two concepts are brigs togheter thanks to class definitions.
 *
 * In FP data and behaviours are modelled with two different tools:
 * - Algebraic Data Type (ADT) to model data
 * - Function to model behaviours
 *
 * An ADT is an immutable data structure that compose other types.
 * There are two common kinds of composition strategy:
 * - Product type: put many types togheter. e.g. struct in C, POJO in JAVA.
 *                 It's called product because all the possible values of a Tuple[String, Int] is
 *                 the *product* of all possible String with all possible Int.
 *                 Useful to model indipendent data in AND e.g. a Person is composed by a name *and* an age.
 *
 * - Sum type:     model exclusive types e.g. union in C, enum in JAVA.
 *                 Sum types correspond to disjoint unions of sets.
 *                 It's called sum because all the possible values of a Either[String, Int] is
 *                 the *sum* of all possible String with all possible Int.
 *                 Useful to model dipendant data in OR e.g. the Light is on *or* off.
 *
 * We can mix ADT as we want, like a product type that compose a type with a sum type.
 */

object ModelData extends SimpleTestSuite {

  // Typical product type in Scala
  case class Person(name: String, age: Int)

  // Typical sum type in Scala
  sealed trait LightState
  case class On()  extends LightState
  case class Off() extends LightState

  sealed trait Suit
  case object Cups   extends Suit
  case object Golds  extends Suit
  case object Swords extends Suit
  case object Clubs  extends Suit

  sealed trait Value
  case object Ace    extends Value
  case object Two    extends Value
  case object Three  extends Value
  case object Four   extends Value
  case object Five   extends Value
  case object Six    extends Value
  case object Seven  extends Value
  case object Knight extends Value
  case object Queent extends Value
  case object King   extends Value

  case class Card(value: Value, suit: Suit)
  case class Player(name: String, hand: List[Card], pile: List[Card])
  case class Table(cards: List[Card])
  case class Deck(cards: List[Card])
  case class Game(deck: Deck, table: Table, player1: Player, player2: Player)

  test("represent initial match state") {
    val p1 = Player("foo", List(Card(Two, Golds), Card(Five, Swords), Card(Seven, Clubs)), List())
    val p2 = Player("bar", List(Card(Ace, Cups), Card(Two, Clubs), Card(Queent, Golds)), List())
    val t  = Table(List(Card(Four, Clubs), Card(King, Swords), Card(Knight, Golds), Card(Ace, Swords)))
    val d  = Deck(List(Card(Ace, Swords), Card(King, Clubs))) // and many more
    Game(d, t, p1, p2)

    () // don't delete
  }

}

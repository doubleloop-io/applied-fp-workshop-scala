package firststeps.answers

class ModelData extends munit.FunSuite {

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
  case object Queen extends Value
  case object King   extends Value

  case class Card(value: Value, suit: Suit)
  case class Player(name: String, hand: List[Card], pile: List[Card])
  case class Table(cards: List[Card])
  case class Deck(cards: List[Card])
  case class Game(deck: Deck, table: Table, player1: Player, player2: Player)

  test("represent initial match state") {
    val p1 = Player("foo", List(Card(Two, Golds), Card(Five, Swords), Card(Seven, Clubs)), List())
    val p2 = Player("bar", List(Card(Ace, Cups), Card(Two, Clubs), Card(Queen, Golds)), List())
    val t  = Table(List(Card(Four, Clubs), Card(King, Swords), Card(Knight, Golds), Card(Ace, Swords)))
    val d  = Deck(List(Card(Ace, Swords), Card(King, Clubs))) // and many more
    Game(d, t, p1, p2)
  }

}

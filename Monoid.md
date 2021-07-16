# Semigroup & Monoid

I nomi come sempre derivano dalla matematica e rappresentano due strutture algebriche (https://it.wikipedia.org/wiki/Struttura_algebrica) ovvero, 
in parole povere, operazioni su insiemi con determinate caratteristiche/vincoli.
Per noi programmatori vuol dire _functions_ che operano su _types_ che devono rispettare certi comportamenti (_tests_).

## Semigroup: gettiamo le basi
Questa astrazione definisce una funzione binaria generica che permette di combinare due valori dello stesso tipo in uno:
```scala
trait Semigroup[A] {
  def combine(first: A, second: A): A
}
```
Come tutte le astrazioni non fa niente se non definire il contratto. 
Cosa fa nel concreto un semigruppo dipende dal tipo e operazione usati dall' implementazione del contratto.
Gli esempi classici sono:
- semigruppo somma (operazione) per gli interi (tipo)
```scala
def combine(first: Int, second: Int): Int = first + second
```
- semigruppo moltiplicazione per gli interi (stesso insieme, operazione differente)
```scala
def combine(first: Int, second: Int): Int = first * second
```
- semigruppo somma per double (differente insieme, stessa operazione)
```scala
def combine(first: Double, second: Double): Double = first * second
```
- semigruppo _and_ per i booleani
```scala
def combine(first: Boolean, second: Boolean): Boolean = first && second
```
- semigruppo _or_ per i booleani
```scala
def combine(first: Boolean, second: Boolean): Boolean = first || second
```
A questo punto potrebbe sorgere la seguente domanda: anche la sottrazione per gli interi forma un semigruppo?
No, perché uno dei vincoli del semigruppo è che l' operazione deve essere associativa. 
Somma e moltiplicazione lo sono, la sottrazione no.
Maggiori informazioni: https://it.wikipedia.org/wiki/Semigruppo
Come per altre astrazioni funzionali possiamo trovarla o meno dentro una standard library del linguaggio di turno.
I semigruppi non si limitano solo a numeri e booleani, ne esistono per strutture dati come Mappe e Set, come per altri oggetti.

## Monoid: estendiamo il semigroup
Il monoide è un' estensione del semigruppo, fa la stessa roba in termini di _combine_ e aggiunge in piu' un elemento neutro _empty_.
```scala
trait Monoid[A] {
  def combine(first: A, second: A): A
  def empty: A
}
```
Piu' precisamente
```scala
trait Monoid[A] extends Semigroup[A] {
  def empty: A
}
```
Gli esempi classici del valore neutro sono:
- zero per somma di interi
```scala
def empty: Int = 1
```
- uno per moltiplicazione degli interi
```scala
def empty: Int = 0
```
- true per i booleani in _and_
```scala
def empty: Boolean = true
```
- false per i booleani in _or_
```scala
def empty: Boolean = false
```

## Strutture algebriche
Le strutture algebriche sono molte, perché ne esistono tante?
Per dirla in termini di SOLID Principles (https://en.wikipedia.org/wiki/SOLID): Interface segregation.
Tante piccolo interfacce che aggiungono un solo comportamento responsabilità permettono un miglior: riuso, composizione, flessibilità e testing.
Quello che cambia passando da una struttura a un' altra è sempre un guadagno/perdita di operazioni e vincoli.
Vedi immagine: https://it.wikipedia.org/wiki/Struttura_algebrica#/media/File:Strutture_mono.png

## Fold & Reduce
Ormai le standard library dei vari linguaggi offrono funzioni per manipolare liste di valori (list comprehension):
map, flatten, filter, find, fold (left/right), reduce (left/right).
Definiamo una nostra reduce (left) per il tipo _List_:
```scala
def reduce[A](l: List[A],  op: (A, A) => A): A
```
La firma di _op_ vi ricorda niente? È esattamente la _combine_ dei semigruppi:
```scala
def combine(first: A, second: A): A
op: (A, A) => A
```
Quindi se vogliamo ottenere la somma di una lista degli interi ci basterà:
```scala
// immaginiamo di avere una standard library che offre le implementazioni delle strutture algebriche
import std.semigroup.implementations._

// passo l' operazione desiderata alla reduce
reduce(List(1, 2, 3), sommaInteri.combine)
```
### Esempi dal mondo reale
Bellissimo, ma la somma degli interi è facile, io voglio ottenere il totale del fatturato della mia azienda.
Ricordato in matematica e functional programming ci piace risolvere i problemi semplici e combinare le soluzioni per risolvere problemi piu' grandi.
```scala
// Invoice e' un product type di varie informazioni
case class Invoice(id: Id, billingAddress: BillingAddress, /* many more fields */ amount: Eur)
// semplifichiamo la gestione soldi con un wrapper di un double
case class Eur(value: Double)

// non so sommare gli Eur, ma so sommare i Double: deriviamo il semigruppo per Euro da double
val sommaEur: Semigroup[Eur] = createSemigroup[Eur]((a, b) => sommaDouble.combine(a.value, b.value))
// una forma piu' compatta potrebbe essere questa
//val sommaEur: Semigroup[Eur] = deriveSemigroup[Eur](sommaDouble, _.value, _.value)

def totalAmount(invoices: List[Invoice]):Eur =
  invoice
    // non so sommare invoices ma gli eur
    // uso la map per avvicinarmi ad un problema che so risolvere
    .map(_.amount)
    // ora posso usare il mio combinatore
    .reduce(sommaEur.combine)
```
Non ci combina nulla con i semigruppi ma divertiamoci un po'. :-)
Se la fonte delle mie fatture fosse multipla ed eterogenea? 
Scompongo il problema, trovo piccole soluzioni e le compongo:
```scala
def fetchFromDb(): IO[List[Invoice]]
def fetchFromHttp(): IO[List[Invoice]]
def fetchFromAS400(): IO[List[Invoice]]

val totalAmount: IO[Eur] =
  List(fetchFromDb(), fetchFromHttp(), fetchFromAS400())  // List[IO[List[Invoice]]]
    .sequence                                             // IO[List[List[Invoice]]]
    .map(_.flatten)                                       // IO[List[Invoice]]
    .map(totalAmount)                                     // IO[Eur]

val result = totalAmount.unsafeRunSync()
```
Come è implementata la flatten?
```scala
// risolviamo un piccolo problema, come ottengo una lista da due? le concateno!
val concatenaList: Semigroup[List[A]] = createSemigroup[List[A]]((a, b) => a.concat(b))

def flatten(nested: List[List[A]]): List[A] =
  nested.reduce(concatenaList.combine)
```
### Aggiungiamo un seed iniziale
Tornando ai combinatori delle liste tipicamente oltre a una funzione di combinazione troviamo anche un parametro per passare un valore iniziale
```scala
def reduce[A](l: List[A],  op: (A, A) => A): A
def reduce[A](l: List[A], initialValue: A,  op: (A, A) => A): A
```
In particolare in Scala questa funzione non è definita come un _overload_ di reduce, ma come una funzione con nome differente:
```scala
def reduce[A](l: List[A],  op: (A, A) => A): A
def fold  [A](l: List[A], initialValue: A,  op: (A, A) => A): A
```
Come facciamo a creare un valore iniziale che non vada a inficiare la combine?
Servirebbe un valore "vuoto" e "neutro". Entra in gioco il monoid:
```scala
// solita standard library immaginaria
import std.monoid.implementations._

fold(List(1, 2, 3), sommaInteri.empty, sommaInteri.combine)
```
Oppure:
```scala
// l' elemento neutro di Eur e' un wrapper dell' elemento neutro dei Double per la somma
val sommaEur: Monoid[Eur] = deriveSemigroup[Eur](sommaDouble, Eur.apply, _.value, _.value)

def totalAmount(invoices: List[Invoice]):Eur =
  invoice
    .map(_.amount)
    .fold(sommaEur.empty, sommaEur.combine)
```
### Evitiamo di passare i parametri
Grazie agli `implicit patameters` di scala possiamo evitare di farci passare in maniera esplicita il concreto semigruppo/monoide
```scala
def reduce[A](l: List[A])(implicit s: Semigroup[A]): A
def fold  [A](l: List[A])(implicit m: Monoid[A]): A

implicit val sommaEur: Monoid[Eur] = deriveMonoid[Eur](sommaDouble, Eur.apply, _.value, _.value)

def totalAmount(invoices: List[Invoice]):Eur =
  invoice
    .map(_.amount)
    .fold()
```
## Curiosità
Parlando di filtri su liste le standard library offrono:
  - forall/all/every combinator: restituisce _true_ se tutti gli elementi della lista rispettano un certo predicato oppure la lista è vuota.
```scala
List("1", "2", "3").forall(_.isDigit) == true
List("1", "a", "3").forall(_.isDigit) == false
```
Questo combinatore altro non è che la composizione di _fold_ e il monoid in _and_ dei booleani dove l' elemento neutro è _true_.
  - exists/any/some combinator: restituisce _true_ se almeno un elemento della lista rispetta un certo predicato oppure _false_ se la lista è vuota.
```scala
List("a", "2", "b").exists(_.isDigit) == true
List("a", "b", "c").exists(_.isDigit) == false
```
Questo combinatore altro non è che la composizione di _fold_ e il monoid in _or_ dei booleani dove l' elemento neutro è _false_.
  - Ogni funzione che restituisce un monoide è essa stessa un monoide
```scala
def countDigits(s: String): Int = ...
def countSymbols(s: String): Int = ...
def countUppers(s: String): Int = ...

// Le due funzioni restituiscono Int il quale offre un monoide con l' operazione somma
// questo vuol dire a prescindere da cosa facciano internamente le due funzioni
// io potrò sempre derivare un monoid della funzione String => Int
implicit val monoidCountFunc: Monoid[String => Int] = 
  createMonoid[String => Int](
    _ => sommaInt.empty,
    (f1, f2) => str => sommaInt.combine(f1(str), f2(str))
  )
// tale per cui la combinazione delle funzioni produce
// lo stesso risultato della combinazione dei valori 
// restituiti dalle funzioni
monoidCountFunc.combine(countDigits, countSymbols)("123stella") == sommaInt.combine(countDigits("123stella"), countSymbols("123stella"))

val countAll = List(countDigits, countSymbols, countUppers).fold

val result = List("c1ao", "M0nd0!", "123stella").map(countAll)
// result: List(1, 4, 3)
```
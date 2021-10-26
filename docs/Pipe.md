# Pipe

Costruiamo un pezzo per volata il pipe operator (|>) in Scala. Il pipe operator permette di passare un valore come
parametro della successiva funzione. Vediamo un semplice esempio in F#:

```fsharp
let incr x = x + 1

5 |> incr |> printfn "%d"
```

Ed in Elixir:

```elixir
defmodule Demo do
 def incr(x), do: x + 1
end

5 |> Demo.incr() |> IO.puts()
```

Il risultato finale in Scala sara' il seguente:

```scala
val incr = (x: Int) => x + 1

5 |> incr |> println
```

## Implementazione

Il "trucco" sta nell' implementare un operatore binario di nome "|>". In Scala gli operatori binari non sono altro che
una funzione definita su un tipo che accetta un parametro. Per esempio sul tipo intero troverò definita una funzione "+"
simile alla seguente:

```scala
def +(other: Int): Int = ...
```

Maggiori informazioni sugli operatori: https://docs.scala-lang.org/tour/operators.html
La pipe sara' quindi una funzione definita su un tipo.

```scala
def |>(other: ...): ... = ...
```

Su quale tipo va definita? Int, String, Date, Customer? Su tutti! Questo vuol dire che la funzione deve essere
polimorfica.

```scala
class PipeOps[A](self: A) {
  def |>(other: ...): ... = ...
}
```

Quale deve essere il tipo di `other`? Visto che la pipe passa un valore (`self`) a una funzione...ci aspettiamo
che `other` sia una funzione. Di che tipo? Tutti! Ancora polimorfica.

```scala
class PipeOps[A](self: A) {
  def |>[B](f: A => B): B =
    f(self)
}

```

L' ultimo tassello è estendere tutti i tipi base di Scala con questo operatore. Dobbiamo fare quello che in altri
linguaggi si chiama _extension method_. Per farlo basta marcare la classe con la keyword `implicit`:

```scala
implicit class PipeOps[A](self: A) {
  def |>[B](f: A => B): B =
    f(self)
}

```

Una volta portata in scope via `import` ogni tipo sara' esteso tramite pipe operator Maggiori informazioni sulle
implicit class: https://docs.scala-lang.org/overviews/core/implicit-classes.html. Codice completo:

```scala
object Pipe {
  implicit class PipeOps[A](self: A) {
    def |>[B](f: A => B): B =
      f(self)
  }
}

object Main extends App {

  import Pipe._

  val incr = (x: Int) => x + 1

  5 |> incr |> println
}

```

## Funzioni a piu' parametri

La nostra pipe gestisce funzioni con un solo parametro (unary function). Come fare a gestire funzioni a piu' parametri (
N-arity function)? Piuttosto che cambiare la pipe si preferisce adattare la funzione che passiamo tramite partial
application. Vediamo un esempio in F#

```fsharp
let incrBy x y = x + y

5 
|> incrBy 3
|> printfn "%d"
```

Nel precedente esempio `incrBy` è una funzione binaria invocata con un solo parametro (la x viene fissata a 3) ovvero
viene applicata parzialmente producendo un nuova funzione a un solo parametro (y). Maggiori informazioni su:

- Partial application: https://github.com/hemanth/functional-programming-jargon#partial-application
- Currying: https://github.com/hemanth/functional-programming-jargon#currying
- Auto currying: https://github.com/hemanth/functional-programming-jargon#auto-currying

Non tutti i linguaggi supportano l' auto currying in maniera nativa, Scala è uno di questi. 
Abbiamo comunque diverse alternative sempre in termini di currying.
Parameter placeholder:

```scala
val incrBy = (x: Int, y: Int) => x + y

5 |> (incrBy(3, _)) |> println
```

Invocare il `curried` combinator (presente nella std lib) per trasformare una "normale" funzione in una curried:

```scala
val incrBy = (x: Int, y: Int) => x + y

5 |> incrBy.curried(3) |> println
```

Fare tutto a mano (manual currying):

```scala
val incrBy = (x: Int) => (y: Int) => x + y

5 |> incrBy(3) |> println
```
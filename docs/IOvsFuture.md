# Referential Transparency

E' una proprieta' delle nostre funzioni: 
>An expression can be replaced with its corresponding value without changing the program's behavior

### Future
Analizziamo il data type Future presente nella standard library di Scala dal punto di vista della referential transparency.
Prendiamo in esame questo piccolo programma che produce una tupla di interi leggendo i valori dalla console.
```scala
import scala.concurrent._
import scala.concurrent.duration._
implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

def askInt(): Future[Int] = 
  Future(println("Please, give me a number:"))
    .flatMap(_ => Future(io.StdIn.readLine().toInt))

def askTwoInt(): Future[(Int, Int)] =
  for {
    x <- askInt()
    y <- askInt()
  } yield (x , y)

def program(): Future[Unit] =
  askTwoInt()
    .flatMap(pair => Future(println(s"Result: $pair")))

Await.result(program(), 20.seconds)    
```
L'ultima riga avvia `program` e blocca l'esecuzione del thread chiamante in attesa che il Future completi o scada il timeout di venti secondi.
Eseguendo lo snippet accade quello che ci aspettiamo.
```text
$ Please, give me a number:
4
$ Please, give me a number:
7
$ Result(4,7)
```
Soffermiamoci sulla parte centrale, la creazione della tupla:
```scala
def askTwoInt(): Future[(Int, Int)] =
  for {
    x <- askInt()
    y <- askInt()
  } yield (x , y)
```
Vedendo questo codice potremmo sentire la necessita' di applicare il refactoring `extract variable` sulla funzione `askInt`.
```scala
def askTwoInt(): Future[(Int, Int)] = {
  val sameAsk = askInt()
  for {
    x <- sameAsk
    y <- sameAsk
  } yield (x , y)
}
```
Eseguendo lo snippet aggiornato otteniamo il seguente output:
```text
$ Please, give me a number:
4
$ Result(4,4)
```
Il Future che chiede l'intero viene completamente eseguito solo la prima volta, mentre le volte successive continuera' a fornire la stessa risposta senza essere rivalutato.
Se guardiamo a questo comportamento in termini di API Design e' solo una scelta fatta dal designer del Future, basta conoscere/studiare come funziona per non cadere in questo errore.
Proviamo un'altro refactoring, forse il problema sta nel valutare lo stesso `sameAsk` due volte.
```scala
def askTwoInt(): Future[(Int, Int)] = {
  val ask1 = askInt()
  val ask2 = askInt()
  for {
    x <- ask1
    y <- ask2
  } yield (x , y)
}
```
Invochiamo due vole, come nel caso originale, la askInt e dopo sequenzializziamo l'esecuzione tramite la `for`. Risultato:
```text
$ Please, give me a number:
$ Please, give me a number:
4
7
$ Result(4,7)
```
Ci viene subito chiesto due volte d'inserire il numero e in fine stampato il result.
Il comportamento viene dal fatto che Future e' eager-evaluated quindi avvia subito, alla creazione, l'operazione asincrona.
Abbiamo un altro dettaglio dell'API da conoscere per non incappare in un errore a run-time.
Abituati a un mondo imperativo fatto di statement e stati mutabili ci sembra tutto normale.
Diversamente se rivediamo il tutto in ottica FP e referential transparency l'implementazione del Future e' fallace.
Immaginiamo come sarebbe piu' facile e bello lavorare in un mondo dove i due refactoring strutturali non producono nessun cambiamento comportamentale alla logica di business.

### IO
Alternative? Per le operazioni di I/O ci viene in soccorso la IO Monad un data type che permette di fare eseguire operazioni sync e async senza compromettere la RT.
Per portare il codice di esempio basta poco:
```scala
import cats._
import cats.data._
import cats.implicits._
import cats.effect._

def askInt(): IO[Int] = 
  IO(println("Please, give me a number:"))
    .flatMap(_ => IO(io.StdIn.readLine().toInt))

def askTwoInt(): IO[(Int, Int)] =
  for {
    x <- askInt()
    y <- askInt()
  } yield (x , y)

def program(): IO[Unit] =
  askTwoInt()
    .flatMap(pair => IO(println(s"Result: ${pair}")))


program().unsafeRunSync()
```
Rimpiazziamo il data type, cambiamo gli import e la parte di esecuzione del programma. L'output e' sempre il solito:
```text
$ Please, give me a number:
4
$ Please, give me a number:
7
$ Result(4,7)
```
Applicando i soliti due refactoring alla `askTwoInt`:
```scala
// primo refactoring
def askTwoInt(): Future[(Int, Int)] = {
  val sameAsk = askInt()
  for {
    x <- sameAsk
    y <- sameAsk
  } yield (x , y)
}
// secondo refactoring
def askTwoInt(): Future[(Int, Int)] = {
  val ask1 = askInt()
  val ask2 = askInt()
  for {
    x <- ask1
    y <- ask2
  } yield (x , y)
}
```
Il risultato non cambia, in tutte e tre le implementazioni l'ordine delle operazioni e la valutazione delle stesse produrra' lo stesso risultato.
```text
$ Please, give me a number:
4
$ Please, give me a number:
7
$ Result(4,7)
```
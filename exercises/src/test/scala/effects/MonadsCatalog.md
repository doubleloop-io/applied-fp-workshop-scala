# Monads Catalog

```
Option[A]      // Computations which may not return a result
Maybe[A]

Try[A]        // Computations which can fail or throw exceptions 

Either[A, B]  // Computations which can complete with two possibile values

Validated[E, A] // Like Either but with an accumulating Applicative on E

List[A]       // Non-deterministic computations which can return multiple possible results

Lazy[A]       // Computations which lazily produces a value

Stream[A]     // Computations which lazily produces values (pull based)

Async[A]      // Computations wich may produce a valute at some point.
Task[A]
Future[A]
Promise[A]

Observable[A] // Computations which asynchronously produces values (push based)

IO[A]         // Computations which perform sequential I/O operations

Reader[E, A]  // Computations which read from a readonly shared environment

Writer[L, A]  // Computations which write data in addition to computing values

State[S, A]   // Computations which maintain state

Identity[A]   // Does not embody any computational strategy. It's like identity function but for types
```

# Higher-Kinded Type

Nel mondo dei type system un type e' a sua volta una "etichettato" da un tipo chiamato kind.
I tipi di base del runtime (`Ordinary Type`) hanno kind `*`.
```scala
// any non-generic base type
// kind: *
// just a type

type OrdinaryType
```
Quelli che comunemente chiamiamo generics rappresentano i type constructor con kind: `* -> *`
```scala
// type constructor
// kind: * -> *
// take a type (parameter) and return a new type

def List(typeParameter: OrdinaryType) : OrdinaryType

def Option(typeParameter: OrdinaryType) : OrdinaryType
```
I type constructor possono essere definiti con piu' di un type parameter
```scala
// kind: * -> * -> *
// take two type parameters and return a new type

def Either(firstTypeParameter: OrdinaryType, secondTypeParameter: OrdinaryType) : OrdinaryType

def Reader(firstTypeParameter: OrdinaryType, secondTypeParameter: OrdinaryType) : OrdinaryType
```
Alcuni linguaggi spingono il concetto di tipo polimorfico anche al type constructor
```scala
// higher-kinded type
// kind: (* -> *) -> * -> *
// take a type constructor, a type parameter and return a new type

def F(typeConstructor: OrdinaryType => OrdinaryType, typeParameter: OrdinaryType) : OrdinaryType = ???
```
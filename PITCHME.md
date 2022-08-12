## **why** embrace

## functional programming?

---

## **composition**

---

## How do we **solve**<br /> complex problems?

---

> We decompose bigger problems into smaller problems. <br />
> ...<br />
> Finally, we write code that solves all the small problems.<br />
> ...<br />
> we compose those pieces of code to create solutions to larger problems.

---

> Decomposition wouldn’t make sense if we weren’t able to put the pieces back together. <br />
>
> Bartosz Milewski (https://bit.ly/3zydlFO)

---

## Complex System

we dedicate a **significant** portion of code and **effort** to compose pieces togheter

---

<img src="assets/drboolean.png" >

---

## **who** is the number one **enemy** of composition?

---

### Side-effects

```scala
def foo(n: Int): String = {
  appendAll("log.txt", "some content")
  n.toString
}
```

---

## Side-effects are a **complexity source**

- hide inputs and outputs
- destroy testability
- destroy composability

---

> ...most of the issues that I was presenting techniques for in the Working Effectively with Legacy Code book were issues of mutable state.
>
> Michael Feathers (https://bit.ly/3cQacaz)

---

## Functional Programming

### is about **eliminating** or **controlling** side-effects<br /> through the use of **pure functions**

---

## Referential transparency

### An expression can be **replaced**

### with its corresponding value

### **without changing** the program's **behavior**

---

### These two programs are **equivalent**

```scala
val z = foo(x) + foo(x)
```

```scala
val y = foo(x)
val z = y + y
```

---

### with **referential transparency** functions are:

- easier to **reason**
- easier to **compose**
- easier to **refactor**
- easier to **test**

---

## **pssss...**

mathematicians **refactor** their "code"</br> since long before us

```js
// distributive law
x(y + z) = (xy) + (xz)
```

---

## before we jump to the code

---

## What should we **expect**

## from a fp workshop?

---

## It's a bit like

## **learning** to program **again**

---

## use this **place** and **time**

## to **experiment** and **fail**

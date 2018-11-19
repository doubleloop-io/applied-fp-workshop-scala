package exercises

import minitest._

/*
 * Our most used Scala features:
 * - Case class
 * - Companion Object
 * - Apply function
 * - Type parameter
 * - Trait as interface/mixin
 * - Implicit parameter
 * - Extension class
 * - Pattern match
 */

object ScalaRecap extends SimpleTestSuite {

  /*
   * TODO: one test at a time,
   *       read `ignore()` description
   *       uncomment the code,
   *       and add the code to get a green test
   *
   * ADD YOUR CODE HERE INSIDE THE OBJECT
   */

  test("define case class") {
    ignore("define a case class w/ two fields: name and age")
    // val result = Person("foo", 56)
    // assertEquals(result, Person("foo", 56))
  }

  test("define the case class's companion object") {
    ignore("define a companion object w/ a custom factory method")
    // val result = Person.create("foo, 56")
    // assertEquals(result, Person("foo", 56))
  }

  test("case class apply") {
    ignore("add an apply function on Person class")
    // val result = Person("foo", 56)("Ciao,")
    // assertEquals(result, "Ciao, mi chiamo foo!")
  }

  test("companion object apply") {
    ignore("add an apply function on Person object")
    // val result = Person("foo, 56")("Ciao,")
    // assertEquals(result, "Ciao, mi chiamo foo!")
  }

  test("case class update") {
    ignore("add 100 years to the person")
    // val p      = Person("foo", 56)
    // val result = p.makeOlder(100)
    // assertEquals(result.age, 156)
  }

  test("trait as interface") {
    ignore("add a Fruit trait w/ two subclass Apple and Banana")
    // assert(Apple().isInstanceOf[Fruit])
    // assert(Banana().isInstanceOf[Fruit])

    ignore("add empty stringify function to Fruit and implement it in Apple and Banana")
    // assertEquals(Apple().stringify, "an apple")
    // assertEquals(Banana().stringify, "a banana")
  }

  test("trait as mixin") {
    ignore("add function w/ default implementation to Fruit trait")
    // assertEquals(Apple().eatenBy("foo"), "foo ate an apple")
    // assertEquals(Banana().eatenBy("bar"), "bar ate a banana")
  }

  test("type parameter") {
    ignore("add generic function to Person class")
    // val p      = Person("foo", 56)
    // val result = p.eat(Apple())
    // assertEquals(result, "foo ate an apple")
  }

  test("implicit parameter") {
    ignore("add a function w/ an implicit parameter to the Person class")
    // implicit val years = 30
    // val p              = Person("foo", 56)
    // val result         = p.makeYounger
    // assertEquals(result.age, 26)
  }

  test("extension class") {
    ignore("add a function to Person via implicit class extension")
    // val p      = Person("foo", 56)
    // val result = p.toMap
    // assertEquals(result, Map("name" -> "foo", "age" -> "56"))
  }

  test("pattern match") {
    ignore("add a function to Person object that...")
    ignore("...return true when name is foo")
    ignore("...return true when name is bar")
    ignore("...return true when age is negative")
    ignore("...otherwise return false")
    // import Person._
    // assert(isFake(Person("foo", 10)))
    // assert(isFake(Person("bar", 10)))
    // assert(isFake(Person("matte", -10)))
    // assert(!isFake(Person("matte", 10)))
  }
}

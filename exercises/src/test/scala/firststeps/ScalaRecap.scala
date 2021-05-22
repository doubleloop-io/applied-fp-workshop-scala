package exercises

/*
 * Our most used Scala features:
 * - Case class
 * - Companion Object
 * - Apply function
 * - Trait as interface/mixin
 * - Implicit parameter
 * - Pattern match
 */

class ScalaRecap extends munit.FunSuite {

  /*
   * TODO: one test at a time,
   *       read description
   *       uncomment the code,
   *       and add the code to get a green test
   *
   * ADD YOUR CODE HERE INSIDE THE TEST OBJECT
   */

  test("define case class".ignore) {
    // TODO: ingore(define a case class w/ two fields: name and age")
    // val result = Person("foo", 56)
    // assertEquals(result, Person("foo", 56))
  }

  test("define the case class's companion object".ignore) {
    // TODO: ingore(define a companion object w/ a custom factory method")
    // val result = Person.create("foo, 56")
    // assertEquals(result, Person("foo", 56))
  }

  test("case class apply".ignore) {
    // TODO: ingore(add an apply function on Person case class")
    // val result = Person("foo", 56)("Ciao,")
    // assertEquals(result, "Ciao, mi chiamo foo!")
  }

  test("companion object apply".ignore) {
    // TODO: ingore(add an apply function on Person object")
    // val result = Person("foo, 56")("Ciao,")
    // assertEquals(result, "Ciao, mi chiamo foo!")
  }

  test("case class update".ignore) {
    // TODO: ingore(add 100 years to the person")
    // val p      = Person("foo", 56)
    // val result = p.makeOlder(100)
    // assertEquals(result.age, 156)
  }

  test("trait as interface (part 1)".ignore) {
    // TODO: ingore(add a Fruit trait w/ two subclass Apple and Banana")
    // assert(Apple().isInstanceOf[Fruit])
    // assert(Banana().isInstanceOf[Fruit])
  }

  test("trait as interface (part 2)".ignore) {
    // TODO: ingore(add empty stringify function to Fruit and implement it in Apple and Banana")
    // assertEquals(Apple().stringify, "an apple")
    // assertEquals(Banana().stringify, "a banana")
  }

  test("trait as mixin".ignore) {
    // TODO: ingore(add function w/ default implementation to Fruit trait")
    // assertEquals(Apple().eatenBy("foo"), "foo ate an apple")
    // assertEquals(Banana().eatenBy("bar"), "bar ate a banana")
  }

  test("implicit parameter".ignore) {
    // TODO: ingore(add a function w/ an implicit parameter to the Person class")
    // implicit val years = 30
    // val p              = Person("foo", 56)
    // val result         = p.makeYounger
    // assertEquals(result.age, 26)
  }

  test("pattern match".ignore) {
    // TODO: ingore(add a function to Person object that...")
    // TODO: ingore(...return true when name is foo")
    // TODO: ingore(...return true when name is bar")
    // TODO: ingore(...return true when age is negative")
    // TODO: ingore(...otherwise return false")
    // import Person._
    // assert(isFake(Person("foo", 10)))
    // assert(isFake(Person("bar", 10)))
    // assert(isFake(Person("matte", -10)))
    // assert(!isFake(Person("matte", 10)))
  }
}

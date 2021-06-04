package firststeps

/*
 * Our most used Scala features are:
 * - Case class
 * - Companion Object
 * - Apply function
 * - Implicit parameter
 * - Pattern match
 * - Trait as interface
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
    // TODO: define a case class w/ two fields: name and age
    // val result = Person("foo", 56)
    // assertEquals(result, Person("foo", 56))
  }

  test("define the case class's companion object".ignore) {
    // TODO: define a companion object w/ a creation method that takes one string
    // val result = Person.create("foo;56")
    // assertEquals(result, Person("foo", 56))
  }

  test("case class apply".ignore) {
    // TODO: define an apply function on Person case class
    // val result = Person("foo", 56)("Ciao,")
    // assertEquals(result, "Ciao, mi chiamo foo!")
  }

  test("companion object apply".ignore) {
    // TODO: define an apply function on Person companion object
    // val result = Person("foo;56")("Ciao,")
    // assertEquals(result, "Ciao, mi chiamo foo!")
  }

  test("update case class state".ignore) {
    // TODO: define makeOlder function to increase age
    // val p      = Person("foo", 56)
    // val result = p.makeOlder(100)
    // assertEquals(result.age, 156)
  }

  test("implicit parameter".ignore) {
    // TODO: define makeYounger function w/ an implicit parameter
    // implicit val years = 30
    // val p              = Person("foo", 56)
    // val result         = p.makeYounger
    // assertEquals(result.age, 26)
  }

  test("pattern match".ignore) {
    // TODO: define isFake function on Person object that...
    // import Person._
    // TODO: ...return true when name is foo
    // assert(isFake(Person("foo", 10)))
    // TODO: ...return true when name is bar
    // assert(isFake(Person("bar", 10)))
    // TODO: ...return true when age is negative
    // assert(isFake(Person("matte", -10)))
    // TODO: ...otherwise return false
    // assert(!isFake(Person("matte", 10)))
  }

  test("trait as interface (part 1)".ignore) {
    // TODO: define a Fruit trait w/ two subclass Apple and Banana
    // assert(Apple().isInstanceOf[Fruit])
    // assert(Banana().isInstanceOf[Fruit])
  }

  test("trait as interface (part 2)".ignore) {
    // TODO: define stringify function on Fruit and implement it in Apple and Banana
    // assertEquals(Apple().stringify, "an apple")
    // assertEquals(Banana().stringify, "a banana")
  }

  test("trait as mixin".ignore) {
    // TODO: define function w/ implementation on Fruit trait
    // assertEquals(Apple().eatenBy("foo"), "foo ate an apple")
    // assertEquals(Banana().eatenBy("bar"), "bar ate a banana")
  }
}

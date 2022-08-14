package scalarecap.answers

class ScalaRecap extends munit.FunSuite {

  case class Person(name: String, age: Int) {
    def apply(suffix: String): String =
      s"$suffix mi chiamo $name!"

    def makeOlder(value: Int): Person =
      copy(age = age + value)

    def makeYounger(implicit value: Int): Person =
      copy(age = age - value)
  }

  object Person {
    def apply(value: String): Person =
      create(value)

    def create(value: String): Person = {
      val tokens = value.split(";")
      Person(tokens(0).trim, tokens(1).trim.toInt)
    }

    def isFake(p: Person): Boolean =
      p match {
        case Person("foo", _)          => true
        case Person("bar", _)          => true
        case Person(_, age) if age < 0 => true
        case _                         => false
      }
  }

  trait Fruit {
    def eatenBy(name: String): String =
      s"$name ate $stringify"

    def stringify: String
  }
  case class Apple() extends Fruit {
    def stringify: String = "an apple"
  }
  case class Banana() extends Fruit {
    def stringify: String = "a banana"
  }

  test("define case class") {
    val result = Person("foo", 56)
    assertEquals(result, Person("foo", 56))
  }

  test("define the case class's companion object") {
    val result = Person.create("foo; 56")
    assertEquals(result, Person("foo", 56))
  }

  test("case class apply") {
    val result = Person("foo", 56)("Ciao,")
    assertEquals(result, "Ciao, mi chiamo foo!")
  }

  test("companion object apply") {
    val result = Person("foo; 56")("Ciao,")
    assertEquals(result, "Ciao, mi chiamo foo!")
  }

  test("update case class") {
    val p = Person("foo", 56)
    val result = p.makeOlder(100)
    assertEquals(result.age, 156)
  }

  test("implicit parameter") {
    implicit val years: Int = 30
    val p = Person("foo", 56)
    val result = p.makeYounger
    assertEquals(result.age, 26)
  }

  test("pattern match") {
    import Person._
    assert(isFake(Person("foo", 10)))
    assert(isFake(Person("bar", 10)))
    assert(isFake(Person("baz", -10)))
    assert(!isFake(Person("baz", 10)))
  }

  test("trait as interface (part 1)") {
    assert(Apple().isInstanceOf[Fruit])
    assert(Banana().isInstanceOf[Fruit])
  }

  test("trait as interface (part 2)") {
    assertEquals(Apple().stringify, "an apple")
    assertEquals(Banana().stringify, "a banana")
  }

  test("trait as mixin") {
    assertEquals(Apple().eatenBy("foo"), "foo ate an apple")
    assertEquals(Banana().eatenBy("bar"), "bar ate a banana")
  }
}

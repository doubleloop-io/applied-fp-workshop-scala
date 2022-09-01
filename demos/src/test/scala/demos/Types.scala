package demos

@munit.IgnoreSuite
class TypesDemo extends munit.FunSuite {

  /*
   * In FP types are used to model/describe data and behavior intent.
   * - Algebraic Data Type (ADT) to model data
   * - Functions to model behaviors
   */

  /*
   * MODEL DATA (ADT)
   */

  // Proper type
  // es: Int, String

  // Type alias - rename a type without change it
  type Name = String

  // Type wrapper - rename a type and change it
  case class Age(value: Int)

  // Product type - put many types together. e.g. struct in C, POJO in JAVA, POCO in C#.
  // Useful to model independent data in AND e.g. a Person is composed by a name *and* an age.
  case class Person(name: String, age: Int)

  // Sum type - model exclusive types e.g. union in C, enum in JAVA/C#.
  // Useful to model dependant data in OR e.g. the Light is on *or* off.
  enum LightState {
    case On(intensity: Double)
    case Off
  }

  // Polymorphic Type
  // Type with generics are called type constructor
  // While the generics are called type parameters
  case class Triple[A, B, C](a: A, b: B, c: C)

  /*
   * MODEL BEHAVIOR
   */

  // A function is something that accepts and produces values.
  // Functions are described by it's type definition.
  // Type helps to reason about function implementation
  def asString(in: Int): String =
    in.toString

  def parseString(in: String): Int =
    in.toInt

  def andThen[A, B, C](first: A => B, second: B => C): A => C =
    a => second(first(a))

  def convertAndRollback(in: String): String =
    andThen(parseString, asString)(in)

  /*
   * MULTIPLE DISPATCH (DIFFERENT BEHAVIOR FOR DIFFERENT DATA)
   */

  // Multiple dispatch is a feature in which a function can be dispatched
  // based on the type of one of its arguments.

  // Define a Sum-Type with all possible values
  enum TrafficLight {
    case Red
    case Green
    case Yellow

    // Use pattern match to dispatch different behavior.
    // The "exhaustive check" verify that there is a branch for each type case.
    def next: TrafficLight = this match {
      case Red    => Green
      case Green  => Yellow
      case Yellow => Red
    }
  }

  /*
   * PROGRAM AS VALUES
   */

  // We can model behavior intent with data types.
  // Then, data can be stored, combined and finally interpreted.
  // Whit different interpreters we can execute different behaviors.

  // In order to do that, we must split a program in two parts:
  // - description: build a program description w/ values
  // - evaluation: execute logic based on the description

  // Define data types
  sealed trait Expr
  case class Num(x: Int) extends Expr
  case class Plus(x: Expr, y: Expr) extends Expr
  case class Times(x: Expr, y: Expr) extends Expr

  // Some function instead of execute something produces value
  def num(x: Int): Expr =
    Num(x)

  def plus(x: Expr, y: Expr): Expr =
    Plus(x, y)

  def times(x: Expr, y: Expr): Expr =
    Times(x, y)

  // In the end some other function execute the desired behavior
  def evalNormal(e: Expr): Int =
    e match {
      case Num(x)      => x
      case Plus(x, y)  => evalNormal(x) + evalNormal(y)
      case Times(x, y) => evalNormal(x) * evalNormal(y)
    }

  def evalPrint(e: Expr): String =
    e match {
      case Num(x)      => s"$x"
      case Plus(x, y)  => s"(${evalPrint(x)} + ${evalPrint(y)})"
      case Times(x, y) => s"(${evalPrint(x)} * ${evalPrint(y)})"
    }

  val program = times(plus(num(1), num(1)), num(2))

  test("execute program (normal)") {
    assertEquals(evalNormal(program), 4)
  }

  test("execute program (print)") {
    assertEquals(evalPrint(program), "((1 + 1) * 2)")
  }
}

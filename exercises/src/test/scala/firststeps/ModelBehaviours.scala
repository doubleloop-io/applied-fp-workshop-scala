package firststeps

/*
 * ADT models data while Function models behaviour.
 * A function is simply something that accepts an input value
 * and produces an output value.
 * In more academic terms it connects a Domain to a Codomain.
 * Functions are described/documented by it's type definition.
 *
 *  f:  InType => OutType
 */

class ModelBehaviours extends munit.FunSuite {

  /*
   * TODO: implements functions marked with `???`
   *
   * ADD YOUR CODE HERE INSIDE THE TEST OBJECT
   */

  val asString: Double => String = in => in.toString

  val parseString: String => Int = in => in.toInt

  val reciprocal: Int => Double = in => 1.0 / in

  val reciprocalString: String => String = in => {
    ???
  }

  test("from string to string throught reciprocal".ignore) {
    // TODO: use existing function to compute a reciprocal in string
    assertEquals(reciprocalString("42"), "0.023809523809523808")
  }
}

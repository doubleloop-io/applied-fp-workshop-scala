package random

object OOPStyle {

  trait Generator[+A] {
    def run(): A
  }

  val integers = new Generator[Int] {
    val rand  = new scala.util.Random()
    def run() = rand.nextInt()
  }

  val booleans = new Generator[Boolean] {
    def run() = integers.run() > 0
  }

  val intPairs = new Generator[(Int, Int)] {
    def run() = (integers.run(), integers.run())
  }

}

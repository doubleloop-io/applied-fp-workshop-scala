object OOPStyle {

  trait Generator[+A] {
    def run(): A
  }

  val integers = new Generator[Int] {
    val rand       = new scala.util.Random
    def run(): Int = rand.nextInt()
  }

  val booleans = new Generator[Boolean] {
    def run(): Boolean = integers.run() > 0
  }

  val intPairs = new Generator[(Int, Int)] {
    def run(): (Int, Int) = (integers.run(), integers.run())
  }

}

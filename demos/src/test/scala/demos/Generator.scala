package demos

@munit.IgnoreSuite
class GeneratorDemo extends munit.FunSuite {

  case class Generator[+A](run: () => A) {
    def map[B](f: A => B): Generator[B] =
      Generator(() => f(run()))

    def flatMap[B](f: A => Generator[B]): Generator[B] =
      Generator(() => f(run()).run())
  }

  def const[A](a: A) = Generator(() => a)

  val integers = Generator { () =>
    val rand = new scala.util.Random()
    rand.nextInt()
  }

  val booleans: Generator[Boolean] = integers.map(_ > 0)

  val intPairs: Generator[(Int, Int)] = integers.flatMap(x => integers.map(y => (x, y)))

  def pairs[A, B](ga: Generator[A], gb: Generator[B]): Generator[(A, B)] =
    ga.flatMap(a => gb.map(b => (a, b)))

  def choose(lo: Int, hi: Int): Generator[Int] =
    for (x <- integers) yield lo + math.abs(x) % (hi - lo)

  def oneOf[A](xs: A*): Generator[A] =
    for (index <- choose(0, xs.length)) yield xs(index)

  def lists[A](ga: Generator[A]): Generator[List[A]] = {

    def end = const[List[A]](Nil)

    def cons =
      for {
        head <- ga
        tail <- lists(ga)
      } yield head :: tail

    for {
      isEnd <- booleans
      list <- if (isEnd) end else cons
    } yield list
  }

  def listsN[A](ga: Generator[A], size: Int): Generator[List[A]] = {

    def end = const[List[A]](Nil)

    def cons =
      for {
        head <- ga
        tail <- listsN(ga, size - 1)
      } yield head :: tail

    for {
      list <- if (size == 0) end else cons
    } yield list
  }

  def alphanumerics(): Generator[Char] = {
    val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')
    oneOf(chars: _*)
  }

  def strings(length: Int): Generator[String] =
    listsN(alphanumerics(), length).map(_.mkString)

  case class Item(description: String, qty: Int, size: String)

  val items: Generator[Item] = for {
    desc <- const("T-shirt")
    qty <- choose(0, 1000)
    size <- oneOf("S", "M", "L", "XL")
  } yield Item(desc, qty, size)

  def check[A](ga: Generator[A], count: Int = 100)(
    predicate: A => Boolean
  ): Unit = {
    for (_ <- 1 until count) {
      val value: A = ga.run()
      val result: Boolean = predicate(value)
      assert(result, "test failed for " + value)
    }
    println("passed " + count + " tests")
  }

  test("example") {
    val pairsOfList: Generator[(List[Int], List[Int])] = pairs(lists(integers), lists(integers))

    check(pairsOfList) { pairs =>
      val (xs: List[Int], ys: List[Int]) = pairs

      // false should be >=
      (xs ++ ys).length > xs.length
    }
  }
}

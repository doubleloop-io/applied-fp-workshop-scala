package exercises.answers

@munit.IgnoreSuite
class CustomLazy extends munit.FunSuite {

  case class Lazy[A](func: () => A) {

    def map[B](f: A => B): Lazy[B] =
      Lazy(() => f(func()))

    def flatMap[B](f: A => Lazy[B]): Lazy[B] =
      Lazy(() => f(func()).func())

    def run(): A =
      func()
  }

  object Lazy {
    def pure[A](a: A): Lazy[A] = Lazy(() => a)
  }

  def increment(x: Int): Int = {
    log("increment")
    x + 1
  }

  def reverseString(x: Int): Lazy[String] = {
    log("reversed")
    Lazy.pure(x.toString.reverse)
  }

  test("creation phase") {
    val result = captureOutput {
      Lazy
        .pure(42)
    }

    assertEquals(result, List())
  }

  test("combination phase - normal") {
    val result = captureOutput {
      Lazy
        .pure(42)
        .map(increment)
    }

    assertEquals(result, List())
  }

  test("combination phase - effectful") {
    val result = captureOutput {
      Lazy
        .pure(42)
        .flatMap(reverseString)
    }

    assertEquals(result, List())
  }

  test("removal phase value") {
    val result = captureOutput {
      Lazy
        .pure(42)
        .map(increment)
        .flatMap(reverseString)
        .run()
    }

    assertEquals(result, List("increment", "reversed"))
  }

  def log(message: String): Unit =
    System.out.println(message)

  def captureOutput[A](action: => Unit): List[String] = {
    import java.io.{ ByteArrayInputStream, ByteArrayOutputStream, PrintStream }

    val originalOut = System.out
    try {
      val out = new ByteArrayOutputStream
      System.setOut(new PrintStream(out))

      action

      if out.toString.isEmpty
      then List()
      else out.toString.replace("\r", "").split('\n').toList
    } finally System.setOut(originalOut)
  }
}

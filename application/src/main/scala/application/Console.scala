package application

object Console {

  import scala.Console.{ GREEN, RED, RESET }
  import cats.effect.IO

  def ask(question: String): IO[String] =
    puts(question).flatMap(_ => reads())

  def logInfo(message: String): IO[Unit] =
    puts(green(s"[OK] $message"))

  def logError(message: String): IO[Unit] =
    puts(red(s"[ERROR] $message"))

  private def puts(message: String): IO[Unit] =
    IO.println(message)

  private def reads(): IO[String] =
    IO.readLine

  private def green(message: String): String =
    s"$GREEN$message$RESET"

  private def red(message: String): String =
    s"$RED$message$RESET"

}

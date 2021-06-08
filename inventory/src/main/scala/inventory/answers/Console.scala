package inventory.answers

trait Console[F[_]] {
  def getLine(): F[String]
  def putLine(line: String): F[Unit]
}

object Console {

  def apply[F[_]](implicit C: Console[F]): Console[F] = C

  def getLine[F[_]]()(implicit C: Console[F]): F[String] =
    C.getLine()

  def putLine[F[_]](line: String)(implicit C: Console[F]): F[Unit] =
    C.putLine(line)
}

package structures.answers

trait Storage[A] {
  def flush(a: A): Unit
}

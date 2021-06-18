package marsroverkata.answers
import cats.effect._

import scala.Console.println

object Version6 {

  object MyApp {
    sealed trait Events
    object Events {
      case class Loaded(value: Int)    extends Events
      case class Execute(cmd: Command) extends Events
    }

    sealed trait Command
    object Command {
      case object Inc  extends Command
      case object Dec  extends Command
      case object Quit extends Command
    }

    sealed trait Effects
    object Effects {
      case object LoadFromFile extends Effects
      case object Ask          extends Effects
    }

    def init(): (Int, Effects) = (0, Effects.LoadFromFile)

    def update(model: Int, event: Events): (Int, Option[Effects]) =
      event match {
        case Events.Loaded(value) => (value, Some(Effects.Ask))
        case Events.Execute(cmd) =>
          cmd match {
            case Command.Inc  => (model + 1, Some(Effects.Ask))
            case Command.Dec  => (model - 1, Some(Effects.Ask))
            case Command.Quit => (model, None)
          }
      }

    def view(model: Int, effect: Effects): IO[Events] =
      effect match {
        case Effects.LoadFromFile => IO.pure(Events.Loaded(42))
        case Effects.Ask          => askCommands(model).map(parse).map(Events.Execute)
      }

    def puts(message: String): IO[Unit] = IO(println(message))
    def reads(): IO[String]             = IO(scala.io.StdIn.readLine())
    def ask(question: String): IO[String] =
      puts(question) *> reads()

    def askCommands(value: Int): IO[String] =
      puts(s"now: $value") *> ask("Waiting commands...")

    def parse(value: String): Command =
      value match {
        case "i" => Command.Inc
        case "d" => Command.Dec
        case "q" => Command.Quit
      }

  }

  object Runtime {

    def create[M, EV, EF](
      initFn: () => (M, EF),
      updateFn: (M, EV) => (M, Option[EF]),
      viewFn: (M, EF) => IO[EV]
    ): IO[M] = {

      def loop(currentState: M, currentEffect: EF): IO[M] =
        viewFn(currentState, currentEffect)
          .flatMap { ev =>
            val (nextState, optContinue) = updateFn(currentState, ev)
            optContinue match {
              case Some(nextEffect) => loop(nextState, nextEffect)
              case None             => IO.pure(nextState)
            }
          }

      val (m0, eff) = initFn()
      loop(m0, eff)
    }
  }

  def main() =
    Runtime
      .create[Int, MyApp.Events, MyApp.Effects](MyApp.init _, MyApp.update, MyApp.view)
      .unsafeRunSync()
}

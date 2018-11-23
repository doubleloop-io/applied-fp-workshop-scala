package marsroverkata.answers.web

import cats._
import cats.data._
import cats.implicits._
import cats.effect._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.HttpService
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityCodec._

// purified :-)
import Parsing._
import RoverRepository._

// pure by design
import Models._
import Core._

object GameService {

  case class PlayGameRequest(idRover: String, commands: String)
  case class PlayGameResponse(message: String)

  val service: HttpService[IO] = HttpService[IO] {

    case req @ POST -> Root =>
      req
        .as[PlayGameRequest] // def fromJson(v : JObject) IO[PlayGameRequest]
        .flatMap(dto => (RoverRepository.load(dto.idRover), IO.pure(parseCommands(dto.commands))).mapN(execute))
        .flatMap(r => send(shouldSend(r), r) *> IO.pure(r))
        .flatMap(roverFinal => Ok(PlayGameResponse(display(roverFinal))))
    // def toJson(v:PlayGameResponse):IO[JObject]
  }

  def display(r: Rover): String = ???

  def send(s: ShouldSendEmail, r: Rover): IO[Unit] =
    s match {
      case YesSend  => EmailNotifier.send(r)
      case NopeSend => IO.unit
    }
}
object EmailNotifier {
  def send(r: Rover): IO[Unit] = ???
}

object RoverRepository {
  def load(id: String): IO[Rover] =
    queryPlanet(id)
      .map(parsePlanet)
      .flatMap(
        p => queryObstacles(id).map(parseObstacles).map(setObstacles(p, _))
      )
      .flatMap(p => queryPosition(id).map(parsePosition).map(Rover.apply(p, _)))

  private def setObstacles(p: Planet, os: List[Obstacle]): Planet =
    p.copy(obstacles = os)

  private def queryPlanet(id: String): IO[String]    = ???
  private def queryObstacles(id: String): IO[String] = ???
  private def queryPosition(id: String): IO[String]  = ???
}

object Parsing {

  def parseCommands(raw: String): List[Command] =
    raw.map(parseCommand).toList

  def parseCommand(raw: Char): Command =
    raw.toString match {
      case "f" => MoveForward
      case "b" => MoveBackward
      case "r" => TurnRight
      case "l" => TurnLeft
      case _   => Unknown
    }

  def parsePlanet(raw: String): Planet = {
    val tokens = raw.split('x')
    Planet(tokens(0).toInt, tokens(1).toInt)
  }

  def parsePosition(raw: String): Position = {
    val tokens = raw.split(',')
    Position(tokens(0).toInt, tokens(1).toInt)
  }

  def parseObstacles(raw: String): List[Obstacle] =
    raw.split('/').toList.map(parseObstacle)

  def parseObstacle(raw: String): Obstacle =
    Obstacle(parsePosition(raw))
}

object Models {

  sealed trait ShouldSendEmail
  case object YesSend  extends ShouldSendEmail
  case object NopeSend extends ShouldSendEmail

  case class Position(x: Int, y: Int)
  case class Planet(xSize: Int, ySize: Int, obstacles: List[Obstacle] = List())
  case class Obstacle(position: Position)
  case class Rover(planet: Planet, position: Position, direction: Direction = N)

  sealed trait Command
  case object Unknown extends Command

  sealed trait MoveCommand extends Command
  case object MoveForward  extends MoveCommand
  case object MoveBackward extends MoveCommand

  sealed trait TurnCommand extends Command
  case object TurnRight    extends TurnCommand
  case object TurnLeft     extends TurnCommand

  sealed trait Direction
  case object N extends Direction
  case object E extends Direction
  case object W extends Direction
  case object S extends Direction
}

object Core {

  def shouldSend(r: Rover): ShouldSendEmail =
    if (r.position == Position(5, 5)) YesSend
    else NopeSend

  def execute(rover: Rover, commands: List[Command]): Rover =
    commands.foldLeft(rover)(execute)

  def execute(rover: Rover, command: Command): Rover =
    command match {
      case c: MoveCommand => executeMove(rover, c)
      case c: TurnCommand => executeTurn(rover, c)
      case Unknown        => rover
    }

  def executeMove(rover: Rover, command: MoveCommand): Rover =
    command match {
      case MoveForward  => moveF(rover)
      case MoveBackward => moveB(rover)
    }

  def executeTurn(rover: Rover, command: TurnCommand): Rover =
    command match {
      case TurnRight => turnR(rover)
      case TurnLeft  => turnL(rover)
    }

  def moveF(rover: Rover): Rover = ???
  def moveB(rover: Rover): Rover = ???

  def turnR(rover: Rover): Rover = ???
  def turnL(rover: Rover): Rover = ???
}

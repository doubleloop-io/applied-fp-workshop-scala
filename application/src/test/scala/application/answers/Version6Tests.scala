package application.answers

import munit.CatsEffectSuite

@munit.IgnoreSuite
class Version6Tests extends CatsEffectSuite {

  import application.answers.Version6._
  import application.answers.Version6.Rotation._, Orientation._, Movement._, Command._, ParseError._
  import cats.effect.IO
  import scala.Console.{ GREEN, RED, RESET }

  test("load mission data on init") {
    val result = init("planet", "rover")
    val expected = (AppState.Loading, Effect.LoadMission("planet", "rover"))
    assertEquals(expected, result)
  }

  test("on load mission successful") {
    val planet = Planet(Size(5, 5), List())
    val rover = Rover(Position(0, 0), N)
    val result = update(AppState.Loading, Event.LoadMissionSuccessful(planet, rover))
    val expected = (AppState.Ready(planet, rover), Effect.AskCommands)
    assertEquals(expected, result)
  }

  test("on load mission failed") {
    val error = new RuntimeException("anything")
    val result = update(AppState.Loading, Event.LoadMissionFailed(error))
    val expected = (AppState.Failed, Effect.ReportKo(error))
    assertEquals(expected, result)
  }

  test("all commands executed") {
    val planet = Planet(Size(5, 5), List())
    val rover = Rover(Position(0, 0), N)
    val result = update(AppState.Ready(planet, rover), Event.CommandsReceived(List(Move(Forward), Move(Forward), Move(Forward))))
    val lastRover = Rover(Position(0, 3), N)
    val expected = (AppState.Ready(planet, lastRover), Effect.ReportCommandSequenceCompleted(lastRover))
    assertEquals(expected, result)
  }

  test("hit obstacle") {
    val planet = Planet(Size(5, 5), List(Obstacle(Position(0, 2))))
    val rover = Rover(Position(0, 0), N)
    val result = update(AppState.Ready(planet, rover), Event.CommandsReceived(List(Move(Forward), Move(Forward), Move(Forward))))
    val lastRover = Rover(Position(0, 1), N)
    val expected = (AppState.Ready(planet, lastRover), Effect.ReportObstacleDetected(ObstacleDetected(lastRover)))
    assertEquals(expected, result)
  }

  test("load planet (integration test)") {
    val result = loadPlanet("planet.txt")
    val expected = Planet(Size(5, 4), List(Obstacle(Position(2, 0)), Obstacle(Position(0, 3))))
    assertIO(result, expected)
  }

  test("go to opposite angle (integration test)") {
    val app = createApplication("planet.txt", "rover.txt")
    val result = runCaptureOutput("RBBLBRF", app)
    assertIO(result, s"$GREEN[OK] 4:3:E$RESET")
  }

  def runCaptureOutput[A](commands: String, program: IO[Unit]): IO[String] = IO {
    import java.io.{ ByteArrayInputStream, ByteArrayOutputStream, PrintStream }

    val originalIn = System.in
    val originalOut = System.out
    try {
      val in = new ByteArrayInputStream(s"$commands\n".getBytes())
      val out = new ByteArrayOutputStream

      System.setIn(in)
      System.setOut(new PrintStream(out))
      program.unsafeRunSync()
      out.toString.replace("\r", "").split('\n').last
    } finally {
      System.setIn(originalIn)
      System.setOut(originalOut)
    }
  }

}
